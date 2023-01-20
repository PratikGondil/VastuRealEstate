package com.vastu.realestate.appModule.employee.bindingAdapter

import android.widget.RatingBar
import androidx.databinding.BindingAdapter

object EmployeeDetailsBindingAdapter {
    @BindingAdapter("android:rating")
    @JvmStatic
    fun RatingBar.setRating(rating:String){
        setRating(rating.toFloat())
    }
}