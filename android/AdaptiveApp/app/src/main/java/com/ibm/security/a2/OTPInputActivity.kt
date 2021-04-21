package com.ibm.security.a2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.ibm.security.adaptivesdk.*


class OTPInputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_input)

        val otpGenerateResult = intent.getSerializableExtra("otp_generate_result") as OtpGenerateResult
        val otpFactorType = intent.getSerializableExtra("otp_factor_type") as OneTimePasscodeFactor
        val transactionId = intent.getStringExtra("transaction_id")!!

        val submitButton = findViewById<Button>(R.id.otp_submit_button)
        val otpInput = findViewById<EditText>(R.id.otp_edit_text)

        otpInput.hint = "${otpGenerateResult.correlation}-"

        submitButton.setOnClickListener {
            val otpEvaluation = OneTimePasscodeEvaluation(
                transactionId = transactionId,
                code = otpInput.text.toString(),
                type = otpFactorType
            )
            Adaptive.evaluate(evaluation = otpEvaluation) { result ->
                result.onSuccess { evaluationResult ->
                    when (evaluationResult) {
                        is AllowAssessmentResult -> {
                            handleAllowResponse(this, evaluationResult)
                        }
                        is DenyAssessmentResult -> {
                            handleDenyResponse(this, evaluationResult)
                        }
                    }
                }
                result.onFailure { error ->
                    presentErrorDialog(this, "Failed to evaluate OTP", error)
                }
            }
        }
    }
}