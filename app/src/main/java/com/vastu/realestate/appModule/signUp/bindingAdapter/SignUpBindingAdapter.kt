package com.vastu.realestate.appModule.signUp.bindingAdapter

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
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

                        setSelection(text!!.length)

                    }


                    R.id.edtMiddleName ->{
                        if(isValidName(input.toString())){
                            isValidMiddleName = true
                            signUpViewModel.middleName.set(input!!.toString().trim())
                        }
                        else
                            isValidMiddleName= false
                        setSelection(text!!.length)

                    }

                    R.id.edtLastName ->{
                        if(isValidName(input.toString())) {
                            isValidLastName = true
                            signUpViewModel.lastName.set(input!!.toString().trim())
                        }
                        else
                            isValidLastName = false
                        setSelection(text!!.length)

                    }
                    R.id.edtMobileNum ->{
                        if(isValidMobile(input.toString())) {
                            isValidMobileNo = true
                            signUpViewModel.mobileNumber.set(input!!.toString().trim())
                        }
                        else
                            isValidMobileNo =false
                        setSelection(text!!.length)

                    }

                    R.id.edtEmail ->{
                        if(isValidEmail(input.toString())){
                            isValidEmailId = true
                            signUpViewModel.mailId.set(input!!.toString().trim())
                        }
                        else
                            isValidEmailId = false
                        setSelection(text!!.length)

                    }

                }
               changeSubmitBtnState(signUpViewModel)
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
    fun occupationListClick(
        autoTextview: AutoCompleteTextView,
        viewModel: SignUpViewModel, context: Context
    ) {

        autoTextview.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                when (autoTextview.id) {
                    R.id.autoCompleteCity ->{
                        isValidCity = true
                        viewModel.city.set(autoTextview.adapter.getItem(i) as String?)}
                    R.id.autoCompleteAreaList ->{
                        isValidSubArea = true
                        viewModel.subArea.set(autoTextview.adapter.getItem(i) as String?)

                    }

                }
                changeSubmitBtnState(viewModel)
            }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @BindingAdapter("context","isEnable")
    @JvmStatic
    fun AppCompatButton.isButtonEnable(context: Context,isBtnEnable: Boolean){
        if(isBtnEnable){
            isEnabled =true
            setTextColor(context.getColor(R.color.white))
        }
        else{
            isEnabled =false
            setTextColor(context.getColor(R.color.gray))
        }
    }

    fun changeSubmitBtnState(signUpViewModel: SignUpViewModel) {
        if (isValidFirstName && isValidMiddleName && isValidLastName && isValidMobileNo && isValidEmailId && isValidCity && isValidSubArea) {
            signUpViewModel.isBtnEnable.set(true)
        } else {
            signUpViewModel.isBtnEnable.set(false)
        }
    }


}