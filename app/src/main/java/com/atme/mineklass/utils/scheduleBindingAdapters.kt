package com.atme.mineklass.utils

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.atme.mineklass.R
import com.atme.mineklass.classData.ClassData


@BindingAdapter("setDayString")
fun TextView.setDayString(classData: ClassData) {
    text = classData.day
}

@BindingAdapter("setTimeString")
fun TextView.setTimeString(classData: ClassData) {
    text = classData.time
}

@BindingAdapter("setSubjectString")
fun TextView.setSubjectString(classData: ClassData) {
    text = classData.module_title
}

@BindingAdapter("setRoomString")
fun TextView.setRoomString(classData: ClassData) {
    text = classData.room
}

@BindingAdapter("cardBackgroundColor")
fun setCardBackgroundColor(cardView: CardView, isToday: Boolean) {
    if (isToday) {
        cardView.setCardBackgroundColor(cardView.context.getColor(R.color.secondaryLightColor))
    } else {
        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFFFF"))
    }
}

@BindingAdapter("isVisible")
fun isVisible(textView: TextView, value: String){
    if (value.isBlank()){
        textView.visibility = View.GONE
    }
    else{
        textView.visibility = View.VISIBLE
    }
}

