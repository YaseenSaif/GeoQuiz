package com.example.quizviewmodel

import androidx.annotation.StringRes

data class Question(@StringRes val textResId:Int , val answer:Boolean , val grade:Int)