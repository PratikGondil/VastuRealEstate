package com.vastu.realestate.appModule.signUp.bindingAdapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.vastu.realestate.R
import com.vastu.realestate.appModule.signUp.viewModel.SignUpViewModel
import com.vastu.realestate.utils.BaseConstant.EMAIL_PATTERN
import com.vastu.realestate.utils.BaseConstant.MOBILE_REGEX
import com.vastu.realestate.utils.BaseConstant.NAME_REGEX
import java.util.regex.Pattern

object SignUpBindingAdapter {
    var isValidFirstName:Boolean=false
    var isValidMiddleName:Boolean=false
    var isValidLastName:Boolean=false
    var isValidMobileNo:Boolean=false
    var isValidEmailId:Boolean=false
    var isValidCity:Boolean=false
    var isValidSubArea:Boolean=false


    @BindingAdapter("validateField")
    @JvmStatic
    fun TextInputEditText.validateUserData(signUpViewModel: SignUpViewModel) {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when (id) {
                    R.id.edtFirstName ->{
                        if(isValidName(input.toString().trim())) {
                            isValidFirstName= true
                            signUpViewModel.firstName.set(input!!.toString().trim())
                        }
                        else
                            isValidFirstName= false

//                        setSelection(text!!.length)

                    }


                    R.id.edtMiddleName ->{
                        if(isValidName(input.toString())){
                            isValidMiddleName = true
                            signUpViewModel.middleName.set(input!!.toString().trim())
                        }
                        else
                            isValidMiddleName= false
//                        setSelection(text!!.length)

                    }

                    R.id.edtLastName ->{
                        if(isValidName(input.toString())) {
                            isValidLastName = true
                            signUpViewModel.lastName.set(input!!.toString().trim())
                        }
                        else
                            isValidLastName = false
//                        setSelection(text!!.length)

                    }
                    R.id.edtMobileNum ->{
                        if(isValidMobile(input.toString())) {
                            isValidMobileNo = true
                            signUpViewModel.mobileNumber.set(input!!.toString().trim())
                        }
                        else
                            isValidMobileNo =false
//                        setSelection(text!!.length)

                    }

                    R.id.edtEmail ->{
                        if(isValidEmail(input.toString())){
                            isValidEmailId = true
                            signUpViewModel.mailId.set(input!!.toString().trim())
                        }
                        else
                            isValidEmailId = false
//                        setSelection(text!!.length)

                    }

                }
               changeSubmitBtnState(signUpViewModel,context)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }
fun isValidEmail(email:String):Boolean{
   return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
    fun isValidName(name:String):Boolean{
       return NAME_REGEX.toRegex().containsMatchIn(name)
    }

    fun isValidMobile(mobileNo:String):Boolean{
        return MOBILE_REGEX.toRegex().containsMatchIn(mobileNo)
    }
    @BindingAdapter("android:onItemClick", "android:context")
    @JvmStatic
    fun AutoCompleteTextView.autoCompleteTextClick(
        viewModel: SignUpViewModel, context: Context
    ) {

     onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val imm =context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.hideSoftInputFromWindow(view.getWindowToken(), 0)
                when (id) {
                    R.id.autoCompleteCity ->{
                        isValidCity = true
                        viewModel.city.set(adapter.getItem(i) as String?)}
                    R.id.autoCompleteAreaList ->{
                        isValidSubArea = true
                        viewModel.subArea.set(adapter.getItem(i) as String?)

                    }

                }
                changeSubmitBtnState(viewModel,context)
            }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @BindingAdapter("context","isBtnEnable","btnBackground")
    @JvmStatic
    fun AppCompatButton.isButtonEnable(context: Context,isBtnEnable: Boolean,btnBackground: Drawable){
        if(isBtnEnable){
            isEnabled =true
//            setTextColor(context.getColor(R.color.white))
            background =btnBackground
        }
        else{
            isEnabled =false
//            setTextColor(context.getColor(R.color.gray))
            background =btnBackground

        }
    }

    fun changeSubmitBtnState(signUpViewModel: SignUpViewModel,context: Context) {
        if (isValidFirstName && isValidMiddleName && isValidLastName && isValidMobileNo && isValidEmailId && isValidCity && isValidSubArea) {
            signUpViewModel.isBtnEnable.set(true)
            signUpViewModel.btnBackground.set(ContextCompat.getDrawable(context,R.drawable.round_button_background))
        } else {
            signUpViewModel.isBtnEnable.set(false)
            signUpViewModel.btnBackground.set(ContextCompat.getDrawable(context,R.drawable.button_inactive_background))

        }
    }


}