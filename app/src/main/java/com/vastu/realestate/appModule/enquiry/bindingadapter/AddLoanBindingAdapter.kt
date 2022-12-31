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
import com.vastu.loanenquirycore.model.response.bank.BankData
import com.vastu.loanenquirycore.model.response.interest.loan.LoanInterstedData
import com.vastu.loanenquirycore.model.response.occupation.OccupationData
import com.vastu.realestate.R
import com.vastu.realestate.appModule.enquiry.viewModel.AddLoanEnquiryViewModel
import com.vastu.realestate.utils.BaseConstant

object AddLoanBindingAdapter {
    var isValidFirstName:Boolean=false
    var isValidMiddleName:Boolean=false
    var isValidLastName:Boolean=false
    var isValidMobileNo:Boolean=false
    var isValidAddress:Boolean=false
    var isValidOccupation:Boolean=false
    var isValidInterestedIn:Boolean=false
    var isValidPreferredBank:Boolean=false
    var isValidLoanAmount:Boolean=false
    var isValidLoanTermYear:Boolean=false


    @BindingAdapter("valid")
    @JvmStatic
    fun TextInputEditText.validateUserData(addLoanViewModel: AddLoanEnquiryViewModel) {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when (id) {
                    R.id.edtFirstName ->{
                        if(isValidName(input.toString().trim())) {
                            isValidFirstName= true
                            addLoanViewModel.firstName.set(input!!.toString().trim())
                            addLoanViewModel.fName.set("")
                        }
                        else{
                            isValidFirstName = false
                            addLoanViewModel.fName.set(context.getString(R.string.first_name_error))
                        }
                    }
                    R.id.edtMiddleName ->{
                        if(isValidName(input.toString())){
                            isValidMiddleName = true
                            addLoanViewModel.middleName.set(input!!.toString().trim())
                            addLoanViewModel.mName.set("")
                        }
                        else {
                            isValidMiddleName = false
                            addLoanViewModel.mName.set(context.getString(R.string.middle_name_error))
                        }
                    }
                    R.id.edtLastName ->{
                        if(isValidName(input.toString())) {
                            isValidLastName = true
                            addLoanViewModel.lastName.set(input!!.toString().trim())
                            addLoanViewModel.lName.set("")
                        }
                        else {
                            isValidLastName = false
                            addLoanViewModel.lName.set(context.getString(R.string.last_name_error))
                        }
                    }
                    R.id.edtMobileNum ->{
                        if(isValidMobile(input.toString())) {
                            isValidMobileNo = true
                            addLoanViewModel.mobileNumber.set(input!!.toString().trim())
                            addLoanViewModel.mobile.set("")
                        }
                        else {
                            isValidMobileNo = false
                            addLoanViewModel.mobile.set(context.getString(R.string.mobile_number_error))
                        }
                        setSelection(text!!.length)
                    }

                    R.id.edtAddress ->{
                        if(isValidAddress(input.toString())){
                            isValidAddress = true
                            addLoanViewModel.address.set(input!!.toString().trim())
                            addLoanViewModel.add.set("")
                        }
                        else {
                            isValidAddress = false
                            addLoanViewModel.add.set(context.getString(R.string.address_error))
                        }
                    }
                    R.id.edtLoanAmount ->{
                        if(isValidLoanAmount(input.toString())){
                            isValidLoanAmount = true
                            addLoanViewModel.loanAmount.set(input!!.toString().trim())
                            addLoanViewModel.amount.set("")
                        }
                        else {
                            isValidLoanAmount = false
                            addLoanViewModel.amount.set(context.getString(R.string.amount_error))
                        }
                    }
                    R.id.edtLoanTermYear ->{
                        if(isValidTermYear(input.toString())){
                            isValidLoanTermYear = true
                            addLoanViewModel.loanTermYear.set(input!!.toString().trim())
                            addLoanViewModel.year.set("")
                        }
                        else {
                            isValidLoanTermYear = false
                            addLoanViewModel.year.set(context.getString(R.string.loan_term_year_error))
                        }
                    }
                }
                changeSubmitBtnState(addLoanViewModel,context)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }
    fun isValidLoanAmount(loanAmount:String):Boolean{
        return loanAmount.length in 4..11
    }
    fun isValidTermYear(termYear:String):Boolean{
        return termYear.length in 1..3
    }
    fun isValidAddress(address:String):Boolean{
        return BaseConstant.NAME_REGEX.toRegex().containsMatchIn(address)
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
        viewModel: AddLoanEnquiryViewModel, context: Context
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
                    R.id.autoCompleteLoanInterestedIn ->{
                        isValidInterestedIn  = true
                        viewModel.loanName.value = adapter.getItem(i) as LoanInterstedData
                        viewModel.loan.set("")
                    }
                    R.id.autoCompletePreferredBank->{
                        isValidPreferredBank  = true
                        viewModel.bankName.value = adapter.getItem(i) as BankData
                        viewModel.bank.set("")
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

    fun changeSubmitBtnState(addLoanViewModel: AddLoanEnquiryViewModel, context: Context) {
        if (isValidFirstName && isValidMiddleName && isValidLastName && isValidMobileNo && isValidAddress
            && isValidOccupation && isValidInterestedIn && isValidPreferredBank && isValidLoanAmount && isValidLoanTermYear) {
            addLoanViewModel.isBtnEnable.set(true)
            addLoanViewModel.btnBackground.set(ContextCompat.getDrawable(context, R.drawable.round_button_background))
        } else {
            addLoanViewModel.isBtnEnable.set(false)
            addLoanViewModel.btnBackground.set(ContextCompat.getDrawable(context, R.drawable.button_inactive_background))
        }
    }
}