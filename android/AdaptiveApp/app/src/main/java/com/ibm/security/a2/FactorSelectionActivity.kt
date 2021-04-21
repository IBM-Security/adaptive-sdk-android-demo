package com.ibm.security.a2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.google.android.material.button.MaterialButton
import com.ibm.security.adaptivesdk.*
import org.json.JSONObject

class FactorSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factor_selection)

        val adaptiveResult = intent.getSerializableExtra("adaptive_result") as RequiresAssessmentResult

        val layout = findViewById<LinearLayout>(R.id.factors_linear_layout)

        adaptiveResult.factors.forEach { factor ->
            val factorButton = MaterialButton(this)
            factorButton.height = 160
            factorButton.text = factor.type.name
            factorButton.setOnClickListener {
                when (factor.type) {
                    Factor.PASSWORD -> {
                        val intent = Intent(this, PasswordInputActivity::class.java)
                        intent.putExtra("transaction_id", adaptiveResult.transactionId)
                        startActivity(intent)
                    }
                    Factor.EMAIL_OTP, Factor.SMS_OTP -> {
                        val enrolledFactor = factor as EnrolledFactor
                        Adaptive.generate(enrollmentId = enrolledFactor.id, transactionId = adaptiveResult.transactionId, factor = enrolledFactor.type) { result ->
                            result.onSuccess { generateResult ->
                                val intent = Intent(this, OTPInputActivity::class.java)
                                intent.putExtra("otp_generate_result", generateResult)
                                intent.putExtra("transaction_id", adaptiveResult.transactionId)
                                intent.putExtra("otp_factor_type", if (factor.type == Factor.EMAIL_OTP) OneTimePasscodeFactor.EMAIL_OTP else OneTimePasscodeFactor.SMS_OTP)
                                startActivity(intent)
                            }
                            result.onFailure { error ->
                                presentErrorDialog(this, "Failed to generate OTP", error)
                            }
                        }
                    }
                    Factor.QUESTIONS -> {
                        val enrolledFactor = factor as EnrolledFactor
                        Adaptive.generate(enrollmentId = enrolledFactor.id, transactionId = adaptiveResult.transactionId, factor = enrolledFactor.type) { result ->
                            result.onSuccess { generateResult ->
                                val intent = Intent(this, QuestionsInputActivity::class.java)
                                intent.putExtra("questions_generate_result", generateResult)
                                intent.putExtra("transaction_id", adaptiveResult.transactionId)
                                startActivity(intent)
                            }
                            result.onFailure { error ->
                                presentErrorDialog(this, "Failed to generate knowledge questions", error)
                            }
                        }
                    }
                    Factor.FIDO -> {
                        Adaptive.generate(enrollmentId = "not needed", transactionId = adaptiveResult.transactionId, factor = Factor.FIDO) { result ->
                            result.onSuccess { generateResult ->
                                val fidoGenerateResult = generateResult as FIDOGenerateResult
                                val fidoGenerateJSONObject = JSONObject()
                                fidoGenerateJSONObject.put("rpId", fidoGenerateResult.rpId)
                                fidoGenerateJSONObject.put("challenge", fidoGenerateResult.challenge)
                                fidoGenerateJSONObject.put("timeout", fidoGenerateResult.timeout)
                                fidoGenerateJSONObject.putOpt("userVerification", fidoGenerateResult.userVerification)
                                fidoGenerateJSONObject.putOpt("allowCredentials", fidoGenerateResult.allowCredentials)

                                val fidoEvaluation = FIDOEvaluation(transactionId = adaptiveResult.transactionId, authenticatorData = "authenticatorData", userHandle = "userHandle", signature = "signature", clientDataJSON = "clientDataJSON")
                                // TODO: Fix proxy implementation to handle FIDO evaluations
                                Adaptive.evaluate(evaluation = fidoEvaluation) { result ->
                                    result.onSuccess { evaluateResult ->
                                        println("FIDO Evaluation Result: $evaluateResult")
                                        when (evaluateResult) {
                                            is AllowAssessmentResult -> {
                                                handleAllowResponse(this, evaluateResult)
                                            }
                                            is DenyAssessmentResult -> {
                                                handleDenyResponse(this, evaluateResult)
                                            }
                                        }
                                    }
                                    result.onFailure { error ->
                                        presentErrorDialog(this, "Failed to evaluate FIDO", error)
                                    }
                                }
                            }
                            result.onFailure { error ->
                                presentErrorDialog(this, "Failed to generate FIDO", error)
                            }
                        }
                    }
                    else -> {
                        println("Selected factor (${factorButton.text}) has not been implemented.")
                    }
                }
            }
            layout.addView(factorButton)
        }
    }
}