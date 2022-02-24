package com.atme.mineklass.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
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


@BindingAdapter("isVisible")
fun isVisible(textView: TextView, value: String){
    if (value.isBlank()){
        textView.visibility = View.GONE
    }
    else{
        textView.visibility = View.VISIBLE
    }
}

@BindingAdapter("setClassImage")
fun setClassImage(imageView: ImageView, classType: String){
    val mImageRes = when(classType){
        "Lecture" -> R.drawable.lecture
        "Workshop" -> R.drawable.lab
        else -> R.drawable.tutor
    }

    imageView.setImageResource(mImageRes)
}

@BindingAdapter("setClassTextVisibility")
fun setClassTextVisibility(textView: TextView, value : String){
    val visibility = when(value){
        "" -> View.GONE
        else -> View.VISIBLE
    }
    textView.visibility = visibility
}

