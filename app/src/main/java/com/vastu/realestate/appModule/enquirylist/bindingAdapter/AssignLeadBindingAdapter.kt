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
import com.vastu.enquiry.statusUpdate.enquiryStatus.model.response.ObjEnquiryStatusData
import com.vastu.networkService.service.NetworkDaoBuilder.Builder.context
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
                imm!!.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0)
                isEmpSelect = true
                if(viewModel.isAssignLead.get()!!) {
                    viewModel.empName.value = (adapter.getItem(i) as ObjEmployeeData?)
                    parentLayout.helperText = ""
                }

                else{
                    viewModel.status.value = (adapter.getItem(i) as ObjEnquiryStatusData?)
                    parentLayout.helperText = ""
                }



                changeSubmitBtnState(viewModel, context)
            }
    }

//    fun changeBtnVariation(viewModel: AssignLeadsViewModel){
//        if(isEmpSelect){
//            viewModel.isBtnEnable.set(true)
//            viewModel.btnBackground.set(ContextCompat.getDrawable(context,R.drawable.round_button_background))
//        }
//        else{
//            viewModel.isBtnEnable.set(false)
//            viewModel.btnBackground.set(ContextCompat.getDrawable(context,R.drawable.button_inactive_background))
//
//        }
//    }

    fun changeSubmitBtnState(assignLeadsViewModel: AssignLeadsViewModel,context: Context) {
        if(isEmpSelect){
            assignLeadsViewModel.isBtnEnable.set(true)
            assignLeadsViewModel.btnBackground.set(ContextCompat.getDrawable(context,R.drawable.round_button_background))
        } else {
            assignLeadsViewModel.isBtnEnable.set(false)
            assignLeadsViewModel.btnBackground.set(ContextCompat.getDrawable(context,R.drawable.button_inactive_background))

        }
    }

}