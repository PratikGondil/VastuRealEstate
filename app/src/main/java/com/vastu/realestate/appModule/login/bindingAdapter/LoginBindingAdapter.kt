package com.vastu.realestate.appModule.login.bindingAdapter

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.BindingAdapter
import com.chaos.view.PinView
import com.google.android.material.textfield.TextInputEditText
import com.vastu.realestate.appModule.login.viewModel.LoginViewModel


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
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    @BindingAdapter("verifyFocus")
    @JvmStatic
    fun PinView.onFocusChange(viewModel: LoginViewModel){
        if (!hasFocus()){
            requestFocus()
            val imm =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)


        }
    }

    @BindingAdapter("verifyOtp")
    @JvmStatic
    fun PinView.onTextChange(viewModel: LoginViewModel){
        addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(charSequence.toString().length==4){
                    viewModel.otp.set(charSequence.toString())
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }
}