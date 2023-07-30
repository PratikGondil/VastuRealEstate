package com.vastu.realestate.appModule.dashboard.view

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Build
import android.widget.Button
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.vastu.realestate.R
import com.vastu.realestate.appModule.enquiry.view.AddLoanEnquiryFragment
import com.vastu.realestate.appModule.enquiry.view.AddPropertyEnquiryFragment
import com.vastu.realestate.appModule.login.view.fragment.LoginFragment
import com.vastu.realestate.appModule.signUp.view.SignUpFragment
import java.util.Locale

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
            }else if(fragment is SignUpFragment)
            {
                fragment.redirectAftertheTermsAccept()

            }
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    fun setAppLocale(localeCode: String) {
        val resources = resources
        val dm = resources.displayMetrics
        val config: Configuration = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(Locale(localeCode.lowercase(Locale.getDefault())))
        } else {
            config.locale = Locale(localeCode.lowercase(Locale.getDefault()))
        }
        resources.updateConfiguration(config, dm)
    }

}