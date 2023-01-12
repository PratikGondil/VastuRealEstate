package com.vastu.realestate.appModule.enquiry.bindingadapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.vastu.loanenquirycore.model.response.interest.property.InterestedInData
import com.vastu.loanenquirycore.model.response.occupation.OccupationData
import com.vastu.loanenquirycore.model.response.ownership.OwnershipData
import com.vastu.realestate.R
import com.vastu.realestate.appModule.enquiry.viewModel.AddPropertyEnquiryViewModel
import com.vastu.realestate.utils.BaseConstant

object AddPropertyBindingAdapter {
    var isValidFirstName:Boolean=false
    var isValidMiddleName:Boolean=false
    var isValidLastName:Boolean=false
    var isValidMobileNo:Boolean=false
    var isValidAddress:Boolean=false
    var isValidOccupation:Boolean=false
    var isValidInterestedIn:Boolean=false
    var isValidOwnership:Boolean=false
    var isValidArea:Boolean=false
    var isValidBudget:Boolean=false


    @BindingAdapter("valid")
    @JvmStatic
    fun TextInputEditText.validateUserData(viewModel: AddPropertyEnquiryViewModel) {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when (id) {
                    R.id.edtFirstName ->{
                        if(isValidName(input.toString().trim())) {
                            isValidFirstName= true
                            viewModel.firstName.set(input!!.toString().trim())
                            viewModel.fName.set("")
                        }
                        else {
                            isValidFirstName = false
                            viewModel.fName.set(context.getString(R.string.first_name_error))
                        }
                    }
                    R.id.edtMiddleName ->{
                        if(isValidName(input.toString())){
                            isValidMiddleName = true
                            viewModel.middleName.set(input!!.toString().trim())
                            viewModel.mName.set("")
                        }
                        else {
                            isValidMiddleName = false
                            viewModel.mName.set(context.getString(R.string.middle_name_error))
                        }
                    }
                    R.id.edtLastName ->{
                        if(isValidName(input.toString())) {
                            isValidLastName = true
                            viewModel.lastName.set(input!!.toString().trim())
                            viewModel.lName.set("")
                        }
                        else {
                            isValidLastName = false
                            viewModel.lName.set(context.getString(R.string.last_name_error))
                        }
                    }
                    R.id.edtMobileNum ->{
                        if(isValidMobile(input.toString())) {
                            isValidMobileNo = true
                            viewModel.mobileNumber.set(input!!.toString().trim())
                            viewModel.mobile.set("")
                        }
                        else {
                            isValidMobileNo = false
                            viewModel.mobile.set(context.getString(R.string.mobile_number_error))
                        }
                        setSelection(text!!.length)
                    }

                    R.id.edtAddress ->{
                        if(isValidAddress(input.toString())){
                            isValidAddress = true
                            viewModel.address.set(input!!.toString().trim())
                            viewModel.add.set("")
                        }
                        else {
                            isValidAddress = false
                            viewModel.add.set(context.getString(R.string.address_error))
                        }
                    }
                    R.id.edtArea ->{
                        if(isValidArea(input.toString())){
                            isValidArea = true
                            viewModel.area.set(input!!.toString().trim())
                            viewModel.era.set("")
                        }
                        else {
                            isValidArea = false
                            viewModel.era.set(context.getString(R.string.area_error))
                        }
                    }

                    R.id.edtBudget ->{
                        if(isValidBudget(input.toString())){
                            isValidBudget = true
                            viewModel.budget.set(input!!.toString().trim())
                            viewModel.bud.set("")
                        }
                        else{
                            isValidBudget = false
                            viewModel.bud.set(context.getString(R.string.budget_error))
                        }
                    }
                }
                changeSubmitBtnState(viewModel,context)
        }
            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }
    fun isValidArea(area:String):Boolean{
        return area.length in 4..8
    }
    fun isValidBudget(budget:String):Boolean{
        return budget.length in 6..10
    }
    fun isValidAddress(address:String):Boolean{
        return BaseConstant.ADDRESS_REGEX.toRegex().containsMatchIn(address)
    }
    fun isValidName(name:String):Boolean{
        return BaseConstant.NAME_REGEX.toRegex().containsMatchIn(name)
    }

    fun isValidMobile(mobileNo:String):Boolean{
        return BaseConstant.MOBILE_REGEX.toRegex().containsMatchIn(mobileNo)
    }
    @BindingAdapter("android:onItemClick", "android:context")
    @JvmStatic
    fun AutoCompleteTextView.autoCompleteTextClick(
        viewModel: AddPropertyEnquiryViewModel, context: Context
    ) {
        onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val imm =context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.hideSoftInputFromWindow(view.getWindowToken(), 0)
                when (id) {
                    R.id.autoCompleteOccupationList ->{
                        isValidOccupation = true
                        viewModel.occupationName.value = adapter.getItem(i) as OccupationData
                        viewModel.occ.set("")
                    }
                    R.id.autoCompletePropertyList ->{
                        isValidInterestedIn  = true
                        viewModel.propertyName.value = adapter.getItem(i) as InterestedInData
                        viewModel.property.set("")
                    }
                    R.id.autoCompleteOwnershipList->{
                        isValidOwnership  = true
                        viewModel.ownershipName.value = adapter.getItem(i) as OwnershipData
                        viewModel.ownership.set("")
                    }
                }
                changeSubmitBtnState(viewModel,context)
            }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @BindingAdapter("context","isBtnEnable","btnBackground")
    @JvmStatic
    fun AppCompatButton.isButtonEnable(context: Context, isBtnEnable: Boolean, btnBackground: Drawable){
        if(isBtnEnable){
            isEnabled =true
            background =btnBackground
        }
        else{
            isEnabled =false
            background =btnBackground

        }
    }

    fun changeSubmitBtnState(viewModel: AddPropertyEnquiryViewModel, context: Context) {
        if (isValidFirstName && isValidMiddleName && isValidLastName && isValidMobileNo && isValidAddress
            && isValidOccupation && isValidInterestedIn && isValidOwnership && isValidArea && isValidBudget) {
            viewModel.isBtnEnable.set(true)
            viewModel.btnBackground.set(ContextCompat.getDrawable(context, R.drawable.round_button_background))
        } else {
            viewModel.isBtnEnable.set(false)
            viewModel.btnBackground.set(ContextCompat.getDrawable(context, R.drawable.button_inactive_background))
        }
    }
}