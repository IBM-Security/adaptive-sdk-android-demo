package com.ibm.security.a2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.ibm.security.adaptivesdk.AllowAssessmentResult
import com.ibm.security.adaptivesdk.DenyAssessmentResult
import com.ibm.security.adaptivesdk.PasswordEvaluation
import com.ibm.security.adaptivesdk.RequiresAssessmentResult

class PasswordInputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_input)

        val transactionId = intent.getStringExtra("transaction_id")!!

        val loginButton = findViewById<Button>(R.id.login_button)
        val usernameInput = findViewById<EditText>(R.id.username_edit_text)
        val passwordInput = findViewById<EditText>(R.id.password_edit_text)

        loginButton.setOnClickListener {
            val passwordEvaluation = PasswordEvaluation(transactionId = transactionId, username = usernameInput.text.toString(), password = passwordInput.text.toString())
            Adaptive.evaluate(evaluation = passwordEvaluation) { result ->
                result.onSuccess { evaluationResult ->
                    when (evaluationResult) {
                        is RequiresAssessmentResult -> {
                            val intent = Intent(this, FactorSelectionActivity::class.java)
                            intent.putExtra("adaptive_result", evaluationResult)
                            startActivity(intent)
                        }
                        is AllowAssessmentResult -> {
                            handleAllowResponse(this, evaluationResult)
                        }
                        is DenyAssessmentResult -> {
                            handleDenyResponse(this, evaluationResult)
                        }
                    }
                }
                result.onFailure { error ->
                    presentErrorDialog(this, "Failed to evaluate password", error)
                }
            }
        }
    }
}