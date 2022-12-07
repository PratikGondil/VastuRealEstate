package com.vastu.realestate.appModule.login.bindingAdapter

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.BindingAdapter
import com.chaos.view.PinView
import com.google.android.material.textfield.TextInputEditText
import com.vastu.realestate.R
import com.vastu.realestate.appModule.login.viewModel.LoginViewModel
import com.vastu.realestate.appModule.signUp.viewModel.SignUpViewModel


object LoginBindingAdapter {
    @BindingAdapter ("validateMobileNo")
    @JvmStatic
    fun TextInputEditText.checkMobileNumber(viewModel: LoginViewModel){
        addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!TextUtils.isEmpty(s.toString().trim()) && s.toString().length==10 ) {
                    viewModel.mobileNumber.set(text.toString())
                    viewModel.isValidMobileNumber.set(true)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }
    @RequiresApi(Build.VERSION_CODES.M)
    @BindingAdapter("context","isEnable")
    @JvmStatic
    fun AppCompatButton.changeSubmitBtnState(context: Context,isMobileValid: Boolean) {
        if ( isMobileValid) {
            isEnabled = true
            setTextColor(context.getColor(R.color.white))
        } else {
            isEnabled = false
            setTextColor(context.getColor(R.color.gray))
        }
    }
}