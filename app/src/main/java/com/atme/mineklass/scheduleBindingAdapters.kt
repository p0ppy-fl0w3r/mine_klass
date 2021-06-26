package com.atme.mineklass

import android.graphics.Color
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
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

