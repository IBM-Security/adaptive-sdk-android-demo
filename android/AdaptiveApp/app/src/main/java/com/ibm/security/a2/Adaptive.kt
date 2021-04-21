package com.ibm.security.a2

import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import com.ibm.security.adaptivesdk.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


object Adaptive : AdaptiveDelegate {
    private val address: String = "https://proxy-sdk.au-syd.mybluemix.net"
    val trusteerCollection = TrusteerCollectionService()

    private fun parseAdaptiveResult(response: JSONObject): AdaptiveResult {
        return when (response.get("status")) {
            // Allow response received.
            AssessmentStatusType.allow -> {
                val token = response.getString("token")
                return AllowAssessmentResult(token = token)
            }
            // Requires response received.
            AssessmentStatusType.requires -> {
                val transactionId = response.getString("transactionId")

                when {
                    // Received first-factor options.
                    response.has("allowedFactors") -> {
                        val factorsJson = response.getJSONArray("allowedFactors")
                        val allowedFactors = List(factorsJson.length()) { i ->
                            val factorType = Factor.from(
                                factorsJson.getJSONObject(i).getString("type")
                            )
                            AllowedFactor(type = factorType)
                        }
                        RequiresAssessmentResult(
                            transactionId = transactionId,
                            factors = allowedFactors
                        )
                    }
                    // Received second-factor options.
                    response.has("enrolledFactors") -> {
                        val factorsJson = response.getJSONArray("enrolledFactors")
                        val enrolledFactors = List(factorsJson.length()) { i ->
                            val enrollment = factorsJson.getJSONObject(i)

                            val factor = Factor.from(enrollment.getString("type"))
                            val id = enrollment.getString("id")
                            val validated = enrollment.optBoolean("validated")
                            val enabled = enrollment.optBoolean("enabled")
                            val attributes = enrollment.getJSONObject("attributes")
                            val attributesMap =
                                attributes.keys().asSequence().associateWith { key ->
                                    attributes[key].toString()
                                }
                            EnrolledFactor(
                                type = factor,
                                id = id,
                                validated = validated,
                                enabled = enabled,
                                attributes = attributesMap
                            )
                        }
                        RequiresAssessmentResult(
                            transactionId = transactionId,
                            factors = enrolledFactors
                        )
                    }
                    // Neither "allowedFactors" or "enrolledFactors" found in requires response.
                    else -> {
                        throw JSONException("Neither \"allowedFactors\" or \"enrolledFactors\" found in requires response.")
                    }
                }
            }
            // Deny response received.
            else -> DenyAssessmentResult()
        }
    }

    private fun parseGenerationResult(response: JSONObject): GenerateResult {
        // Parse knowledge questions
        val questions = response.optJSONArray("questions")
        if (questions != null) {
            val questionsMap = HashMap<String, String>()
            for (i in 0 until questions.length()) {
                val question = questions.getJSONObject(i).getString("question")
                val questionKey = questions.getJSONObject(i).getString("questionKey")
                questionsMap[questionKey] = question
            }

            return KnowledgeQuestionsGenerateResult(questions = questionsMap)
        }

        // Parse FIDO
        val fido = response.optJSONObject("fido")
        if (fido != null) {
            val rpId = fido.getString("rpId")
            val challenge = fido.getString("challenge")
            val userVerification = fido.optString("userVerification")
            val timeout = fido.getInt("timeout")
            val allowCredentials = fido.optJSONArray("allowCredentials")
            return FIDOGenerateResult(rpId = rpId, challenge = challenge, userVerification = userVerification, timeout = timeout, allowCredentials = allowCredentials)
        }

        return OtpGenerateResult(correlation = response.optString("correlation"))
    }

    override fun assessment(sessionId: String, completion: (Result<AdaptiveResult>) -> Unit) {
        // Specify request body and headers.
        val requestBody = """
        {
            "sessionId": "$sessionId"
        }
        """
        val headers = mapOf(
            "Content-Type" to "application/json"
        )

        // Start an assessment.
        "$address/login".httpPost().timeoutRead(60_000).jsonBody(requestBody).header(headers)
            .response { _, response, result ->
                Thread { println("Assessment response data: ${String(response.data)}") }.start()
                result.success {
                    val responseBody = JSONObject(String(response.data))

                    completion(Result.success(parseAdaptiveResult(responseBody)))
                }
                result.failure { error ->
                    completion(Result.failure(error))
                }
        }
    }

    override fun generate(
        enrollmentId: String,
        transactionId: String,
        factor: Factor,
        completion: (Result<GenerateResult>) -> Unit
    ) {
        val requestBody: String

        // Specify request body and headers.
        if (factor == Factor.FIDO) {
            requestBody = """
                {
                    "type": "fido2",
                    "userId": "6020007JDE",
                    "transactionId": "$transactionId"
                }
                """
        } else {
            val factors = mapOf(
                Factor.EMAIL_OTP to "emailotp",
                Factor.SMS_OTP to "smsotp",
                Factor.QUESTIONS to "questions",
            )
            requestBody = """
                {
                    "type": "${factors[factor]}",
                    "sessionId": "${AdaptiveContext.sessionId}",
                    "transactionId": "$transactionId",
                    "enrollmentId": "$enrollmentId"
                }
                """
        }

        val headers = mapOf(
            "Content-Type" to "application/json"
        )

        // Start an assessment.
        "$address/mfa".httpPost().timeoutRead(60_000).jsonBody(requestBody)
            .header(
                headers
            )
            .response { _, response, result ->
                Thread { println("Generate response data: ${String(response.data)}") }.start()
                result.success {
                    val responseBody = JSONObject(String(response.data))

                    completion(Result.success(parseGenerationResult(responseBody)))
                }
                result.failure { error ->
                    completion(Result.failure(error))
                }
            }
    }

    override fun evaluate(
        evaluation: FactorEvaluation,
        completion: (Result<AdaptiveResult>) -> Unit
    ) {
        when (evaluation) {
            // Evaluate password factor.
            is PasswordEvaluation -> {
                // Specify request body and headers.
                val requestBody = """
                {
                    "sessionId": "${AdaptiveContext.sessionId}",
                    "transactionId": "${evaluation.transactionId}",
                    "username": "${evaluation.username}",
                    "password": "${evaluation.password}"
                }
                """

                val headers = mapOf(
                    "Content-Type" to "application/json"
                )

                // Start an assessment.
                "$address/login/password".httpPost().timeoutRead(60_000).jsonBody(requestBody)
                    .header(
                        headers
                    )
                    .response { _, response, result ->
                        Thread { println("Evaluate response data: ${String(response.data)}") }.start()
                        result.success {
                            val responseBody = JSONObject(String(response.data))

                            completion(Result.success(parseAdaptiveResult(responseBody)))
                        }
                        result.failure { error ->
                            completion(Result.failure(error))
                        }
                    }
            }
            // Evaluate OTP factor.
            is OneTimePasscodeEvaluation -> {
                // Specify request body and headers.
                val requestBody = """
                {
                    "sessionId": "${AdaptiveContext.sessionId}",
                    "transactionId": "${evaluation.transactionId}",
                    "otp": "${evaluation.code}"
                }
                """

                val headers = mapOf(
                    "Content-Type" to "application/json"
                )

                val otpFactors = mapOf(
                    OneTimePasscodeFactor.EMAIL_OTP to "emailotp",
                    OneTimePasscodeFactor.TOTP to "totp",
                    OneTimePasscodeFactor.SMS_OTP to "smsotp"
                )
                // Start an assessment.
                "$address/mfa/${otpFactors[evaluation.type]}".httpPost()
                    .timeoutRead(60_000).jsonBody(
                        requestBody
                    ).header(headers)
                    .response { _, response, result ->
                        Thread { println("Evaluation response data: ${String(response.data)}") }.start()
                        result.success {
                            val responseBody = JSONObject(String(response.data))

                            completion(Result.success(parseAdaptiveResult(responseBody)))
                        }
                        result.failure { error ->
                            completion(Result.failure(error))
                        }
                    }
            }
            is KnowledgeQuestionEvaluation -> {
                val answersJson = JSONArray(evaluation.answers.toList().map { answer ->
                    JSONObject("{\"questionKey\": \"${answer.first}\", \"answer\": \"${answer.second}\"}")
                })

                // Specify request body and headers.
                val requestBody = """
                {
                    "sessionId": "${AdaptiveContext.sessionId}",
                    "transactionId": "${evaluation.transactionId}",
                    "questions": $answersJson
                }
                """

                val headers = mapOf(
                    "Content-Type" to "application/json"
                )

                // Start an assessment.
                "$address/mfa/questions".httpPost().timeoutRead(60_000)
                    .jsonBody(
                        requestBody
                    ).header(headers)
                    .response { _, response, result ->
                        Thread { println("Evaluation response data: ${String(response.data)}") }.start()
                        result.success {
                            val responseBody = JSONObject(String(response.data))

                            completion(Result.success(parseAdaptiveResult(responseBody)))
                        }
                        result.failure { error ->
                            completion(Result.failure(error))
                        }
                    }
            }
            is FIDOEvaluation -> {
                // Specify request body and headers.
                val requestBody = """
                {
                    "sessionId": "${AdaptiveContext.sessionId}",
                    "transactionId": "${evaluation.transactionId}",
                    "response": {
                        "clientDataJSON": "${evaluation.clientDataJSON}",
                        "authenticatorData": "${evaluation.authenticatorData}",
                        "signature": "${evaluation.signature}",
                        "userHandle": "${evaluation.userHandle}"
                    }
                }
                """

                val headers = mapOf(
                    "Content-Type" to "application/json"
                )

                // Start an assessment.
                "$address/mfa/fido2".httpPost().timeoutRead(60_000)
                    .jsonBody(
                        requestBody
                    ).header(headers)
                    .response { _, response, result ->
                        Thread { println("Evaluation response data: ${String(response.data)}") }.start()
                        result.success {
                            val responseBody = JSONObject(String(response.data))

                            completion(Result.success(parseAdaptiveResult(responseBody)))
                        }
                        result.failure { error ->
                            completion(Result.failure(error))
                        }
                    }
            }
        }
    }

    fun logout(accessToken: String, completion: (Result<Nothing?>) -> Unit) {
        // Specify request body and headers.
        val headers = mapOf(
            "Content-Type" to "application/json"
        )

        // Start an assessment.
        "$address/logout".httpPost().timeoutRead(60_000).jsonBody(accessToken).header(headers)
            .response { _, response, result ->
                Thread { println("Logout response data: ${String(response.data)}") }.start()
                result.success {
                    completion(Result.success(null))
                }
                result.failure { error ->
                    completion(Result.failure(error))
                }
            }
    }
}
