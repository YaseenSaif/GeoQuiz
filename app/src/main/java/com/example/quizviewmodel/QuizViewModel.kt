package com.example.quizviewmodel

import android.widget.Button
import androidx.lifecycle.ViewModel
import kotlin.random.Random
class QuizViewModel : ViewModel() {
    private val questionBank1 = listOf(
        Question(R.string.question_L1_1, true, 2),
        Question(R.string.question_L1_2, false, 2),
        Question(R.string.question_L1_3, false, 2),
        Question(R.string.question_L1_4, true, 2),
        Question(R.string.question_L1_5, true, 2),
        Question(R.string.question_L1_6, false, 2)

    )
    private val questionBank2 = listOf(
        Question(R.string.question_L2_1, false, 4),
        Question(R.string.question_L2_2, true, 4),
        Question(R.string.question_L2_3, true, 4),
        Question(R.string.question_L2_4, false, 4),
        Question(R.string.question_L2_5, true, 4),
        Question(R.string.question_L2_6, true, 4)
    )
    private val questionBank3 = listOf(
        Question(R.string.question_L3_1, true, 6),
        Question(R.string.question_L3_2, false, 6),
        Question(R.string.question_L3_3, false, 6),
        Question(R.string.question_L3_4, true, 6),
        Question(R.string.question_L3_5, false, 6),
        Question(R.string.question_L3_6, false, 6)
    )
    private val questionBank = listOf(
        questionBank1[Random.nextInt(0, 2)],
        questionBank1[Random.nextInt(3, 5)],
        questionBank2[Random.nextInt(0, 2)],
        questionBank2[Random.nextInt(3, 5)],
        questionBank3[Random.nextInt(0, 2)],
        questionBank3[Random.nextInt(3, 5)]
    )
    var currentIndex = 0
    var totalGrade = 0
    var isCheater = false
    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId
    val currentQuestionGrade: Int
        get() = questionBank[currentIndex].grade
    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
        isCheater = false
    }

    fun moveToPrev() {
        currentIndex = if (currentIndex == 0) {
            questionBank.size - 1
        } else {
            currentIndex - 1
        }
        isCheater = false
    }

    fun updateGrade(grade: Int) {
        if (!isCheater)
            totalGrade += grade
    }

    fun freezeButtons(true_button: Button, false_button: Button, prev_button: Button) {
        true_button.isClickable = false
        false_button.isClickable = false
        prev_button.isClickable = false
    }

    fun activeButtons(true_button: Button, false_button: Button, prev_button: Button) {
        true_button.isClickable = true
        false_button.isClickable = true
        prev_button.isClickable = true
    }

}

