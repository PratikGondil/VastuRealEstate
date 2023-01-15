package com.vastu.realestate.appModule.enquirylist.bindingAdapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.vastu.enquiry.employee.model.response.ObjEmployeeData
import com.vastu.realestate.R
import com.vastu.realestate.appModule.enquirylist.viewmodel.AssignLeadsViewModel
import com.vastu.realestate.appModule.signUp.bindingAdapter.SignUpBindingAdapter
import com.vastu.realestate.appModule.signUp.viewModel.SignUpViewModel
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaDataList
import com.vastu.realestate.registrationcore.model.response.subArea.ObjCityAreaData

object AssignLeadBindingAdapter {
    var isEmpSelect = false
    @BindingAdapter("android:onItemClick", "android:context","tilLayout")
    @JvmStatic
    fun AutoCompleteTextView.autoCompleteTextClick(
        viewModel: AssignLeadsViewModel, context: Context,parentLayout:TextInputLayout
    ) {

        onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val imm =context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.hideSoftInputFromWindow(view.getWindowToken(), 0)

                isEmpSelect = true
                        viewModel.empName.value = (adapter.getItem(i) as ObjEmployeeData?)
                        parentLayout.helperText = ""




                changeSubmitBtnState(viewModel, context)
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

    fun changeSubmitBtnState(assignLeadsViewModel: AssignLeadsViewModel,context: Context) {
        if (SignUpBindingAdapter.isValidFirstName && SignUpBindingAdapter.isValidMiddleName && SignUpBindingAdapter.isValidLastName && SignUpBindingAdapter.isValidMobileNo && SignUpBindingAdapter.isValidEmailId && SignUpBindingAdapter.isValidCity && SignUpBindingAdapter.isValidSubArea) {
            assignLeadsViewModel.isBtnEnable.set(true)
            assignLeadsViewModel.btnBackground.set(ContextCompat.getDrawable(context,R.drawable.round_button_background))
        } else {
            assignLeadsViewModel.isBtnEnable.set(false)
            assignLeadsViewModel.btnBackground.set(ContextCompat.getDrawable(context,R.drawable.button_inactive_background))

        }
    }

}