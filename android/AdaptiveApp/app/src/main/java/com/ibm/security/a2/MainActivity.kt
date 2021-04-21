package com.ibm.security.a2

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.ibm.security.adaptivesdk.AdaptiveContext
import com.ibm.security.adaptivesdk.AllowAssessmentResult
import com.ibm.security.adaptivesdk.DenyAssessmentResult
import com.ibm.security.adaptivesdk.RequiresAssessmentResult


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("AdaptiveContext.sessionId: ${AdaptiveContext.sessionId}")

        val token = getToken(this)

        // Set token label if applicable
        val tokenText = findViewById<TextView>(R.id.token_text_view)
        tokenText.movementMethod = ScrollingMovementMethod()
        tokenText.text = token

        // Set result label if applicable
        val resultText = findViewById<TextView>(R.id.result_text_view)
        resultText.text = intent.getStringExtra(HelperConstants.RESULT_STATUS)

        // Assign an instance of AdaptiveCollection, otherwise we'll get an error.
        AdaptiveContext.collectionService = Adaptive.trusteerCollection

        try {
            // Start collection process.
            AdaptiveContext.start(applicationContext)
        } catch (error: Throwable) {
            presentErrorDialog(this, "Collection Failed", error)
        }

        val assessmentButton = findViewById<Button>(R.id.assessment_button)

        // Set assessment button on-click listener
        assessmentButton.setOnClickListener {
            // Perform assessment.
            Adaptive.assessment(sessionId = AdaptiveContext.sessionId) { result ->
                result.onSuccess { assessmentResult ->
                    when (assessmentResult) {
                        is RequiresAssessmentResult -> {
                            val intent = Intent(this, FactorSelectionActivity::class.java)
                            intent.putExtra("adaptive_result", assessmentResult)
                            startActivity(intent)
                        }
                        is AllowAssessmentResult -> {
                            handleAllowResponse(this, assessmentResult)
                        }
                        is DenyAssessmentResult -> {
                            handleDenyResponse(this, assessmentResult)
                        }
                    }
                }
                result.onFailure { error ->
                    presentErrorDialog(this, "Assessment Failed", error)
                }
            }
        }

        val logoutButton = findViewById<Button>(R.id.logout_button)
        logoutButton.isVisible = token != null

        logoutButton.setOnClickListener {
            Adaptive.logout(token!!) { result ->
                result.onSuccess {
                    handleLogout(this)
                }
                result.onFailure { error ->
                    presentErrorDialog(this, "Logout Failed", error)
                }
            }
        }
    }
}
