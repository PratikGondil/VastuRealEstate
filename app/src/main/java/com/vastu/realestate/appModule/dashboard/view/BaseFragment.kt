package com.vastu.realestate.appModule.dashboard.view

import android.app.AlertDialog
import android.widget.Button
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.vastu.realestate.R
import com.vastu.realestate.appModule.enquiry.view.AddLoanEnquiryFragment
import com.vastu.realestate.appModule.enquiry.view.AddPropertyEnquiryFragment
import com.vastu.realestate.appModule.login.view.fragment.LoginFragment

open class BaseFragment : Fragment() {

    private fun getVastuActivity(): BaseActivity? {
        return super.getActivity() as BaseActivity?
    }
    fun showProgressDialog(){
        getVastuActivity()?.showProgressDialog()
    }
     fun hideProgressDialog(){
        getVastuActivity()?.hideProgressDialog()
    }
    fun hideDialog(){
        getVastuActivity()?.hideDialog()
    }
    fun showDialog(message:String,isSuccess:Boolean,isNetworkFailure:Boolean){
        getVastuActivity()?.showDialog(message,isSuccess,isNetworkFailure)
    }
    fun createDialog(fragment: Fragment) {
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.custom_dialog,null)
        val  checkbox = view.findViewById<CheckBox>(R.id.termsCheck)
        builder.setView(view)
        checkbox.setOnClickListener {
            builder.dismiss()
            if(fragment is LoginFragment) {
                fragment.redirectedToAPIAfterTerms()
            }else if(fragment is AddPropertyEnquiryFragment)
            {
                fragment.redirectedAfterAPITerms()
            }else if (fragment is AddLoanEnquiryFragment)
            {
                fragment.redirectedAfterTermsLoan()
            }
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

}