package com.ibm.security.a2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.ibm.security.adaptivesdk.*

class QuestionsInputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_input)

        val questionsGenerateResult = intent.getSerializableExtra("questions_generate_result") as KnowledgeQuestionsGenerateResult
        val transactionId = intent.getStringExtra("transaction_id")!!

        val submitButton = findViewById<Button>(R.id.questions_submit_button)

        val layout = findViewById<LinearLayout>(R.id.questions_linear_layout)

        val answersEditText = HashMap<String, EditText>()
        questionsGenerateResult.questions.forEach { question ->
            val questionInput = EditText(this)
            questionInput.hint = question.value
            answersEditText[question.key] = questionInput
            layout.addView(questionInput, 0)
        }

        submitButton.setOnClickListener {
            val answers = answersEditText.mapValues { it.value.text.toString() }
            val questionsEvaluation = KnowledgeQuestionEvaluation(transactionId = transactionId, answers = answers)

            Adaptive.evaluate(evaluation = questionsEvaluation) { result ->
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
                    presentErrorDialog(this, "Failed to evaluate questions", error)
                }
            }
        }
    }
}
