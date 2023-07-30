package com.vastu.realestate.appModule.login.bindingAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.vastu.realestate.R
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
                    viewModel.isValidMobileNumber.set(true)
                    viewModel.btnBackground.set(ContextCompat.getDrawable(context,R.drawable.round_button_background))
                    viewModel.error.set("")
                }
                else {
                    viewModel.errorVisible.set(View.VISIBLE)
                    viewModel.error.set(context!!.resources.getString(R.string.valid_number))
                }

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }


    @RequiresApi(Build.VERSION_CODES.M)
    @BindingAdapter("context","isEnable")
    @JvmStatic
    fun AppCompatButton.changeSubmitBtnState(context: Context,isEnable: Boolean) {
        if (isEnable) {
            isEnabled = true
//          background = btnBackground
        } else {
            isEnabled = false
//            background = btnBackground

        }
    }


    @BindingAdapter("onTouch")
    @JvmStatic
    fun LinearLayout.screenOnTouch(loginViewModel: LoginViewModel){
        setOnTouchListener { v: View, m: MotionEvent ->
            val imm =context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(v.getWindowToken(), 0)
            v.requestFocus()
            true
        }
    }
}