package com.example.quizviewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*


private const val REQUEST_CODE_CHEAT = 0
class MainActivity : AppCompatActivity() {
    val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateQuestion()
        updateProgressBar()
        true_button.setOnClickListener {
            checkAnswer(true)
            quizViewModel.freezeButtons(true_button, false_button, prev_button)
        }
        false_button.setOnClickListener {
            checkAnswer(false)
            quizViewModel.freezeButtons(true_button, false_button, prev_button)
        }
        next_button.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            quizViewModel.activeButtons(true_button, false_button, prev_button)
            updateProgressBar()
            defaultButtonSettings(true_button, false_button)
            if (quizViewModel.currentIndex == 0) {
                quizViewModel.totalGrade = 0
                updateScore()
            }
        }
        prev_button.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
        }
        cheat_button.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatingActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }
    }


    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        question_text_view.setText(questionTextResId)
    }

    fun defaultButtonSettings(true_button: Button, false_button: Button) {
        true_button.setBackgroundResource(R.drawable.rounded_corners)
        false_button.setBackgroundResource(R.drawable.rounded_corners)
    }

    @SuppressLint("ResourceAsColor")
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val answerGrade = quizViewModel.currentQuestionGrade
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> {
                quizViewModel.updateGrade(answerGrade)
                if (userAnswer)
                    true_button.setBackgroundResource(R.color.Blue)
                else
                    false_button.setBackgroundResource(R.color.Blue)
                R.string.correct_toast
            }
            else -> {
                if (userAnswer)
                    true_button.setBackgroundResource(R.color.Red)
                else
                    false_button.setBackgroundResource(R.color.Red)
                R.string.incorrect_toast
            }

        }
        var toastA = Toast.makeText(this, messageResId, Toast.LENGTH_LONG)
        toastA.setGravity(Gravity.BOTTOM, 0, 0)
        toastA.show()
        updateScore()
    }
    fun updateScore(){
        gradeTV.text = quizViewModel.totalGrade.toString()
    }

    fun updateProgressBar() {
        progressBar.progress = quizViewModel.currentIndex + 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.isCheater =
                data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }
}