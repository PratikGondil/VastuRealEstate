package com.vastu.realestate.appModule.otp.bindingAdapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.chaos.view.PinView
import com.vastu.realestate.R
import com.vastu.realestate.appModule.otp.viewModel.OTPViewModel
import com.vastu.realestate.appModule.signUp.viewModel.SignUpViewModel


object OtpBindingAdapter {

    @BindingAdapter("verifyFocus")
    @JvmStatic
    fun PinView.onFocusChange(viewModel: OTPViewModel){
        if (!hasFocus()){
            requestFocus()
            val imm =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)


        }
    }

    @BindingAdapter("verifyOtp")
    @JvmStatic
    fun PinView.onTextChange(viewModel: OTPViewModel){

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if(charSequence.toString().length==4){

                    viewModel.otp.set(charSequence.toString())
                    viewModel.isValidOTP.set(true)
                    viewModel.btnBackground.set(ContextCompat.getDrawable(context,R.drawable.round_button_background))
                }
                else{
                    viewModel.btnBackground.set(ContextCompat.getDrawable(context,R.drawable.button_inactive_background))

                }

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }
    @RequiresApi(Build.VERSION_CODES.M)
    @BindingAdapter("context","isValidOTP","btnBackground")
    @JvmStatic
    fun AppCompatButton.changeSubmitBtnState(context: Context,isValidOTP: Boolean,btnBackground:Drawable) {
        if ( isValidOTP) {
            isEnabled = true
            background =btnBackground
        } else {
            isEnabled = false
            background =btnBackground

        }
    }
}