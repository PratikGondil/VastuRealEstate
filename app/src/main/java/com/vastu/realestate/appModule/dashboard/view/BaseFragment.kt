package com.vastu.realestate.appModule.dashboard.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.provider.Settings
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import com.vastu.realestate.R
import com.vastu.realestate.appModule.enquiry.view.AddLoanEnquiryFragment
import com.vastu.realestate.appModule.enquiry.view.AddPropertyEnquiryFragment
import com.vastu.realestate.appModule.login.view.fragment.LoginFragment
import com.vastu.realestate.appModule.signUp.view.SignUpFragment
import com.vastu.realestate.utils.CommonUtils
import java.util.*

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
        val cross=view.findViewById<ImageView>(R.id.img_cross)
        builder.setView(view)
        checkbox.setOnClickListener {
            builder.dismiss()
            if(fragment is LoginFragment) {
                fragment.callLoginAPI()
            }else if(fragment is AddPropertyEnquiryFragment)
            {
                fragment.redirectedAfterAPITerms()
            }else if (fragment is AddLoanEnquiryFragment)
            {
                fragment.redirectedAfterTermsLoan()
            }else if(fragment is SignUpFragment)
            {
                fragment.callSignUpAPI()

            }
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
        cross.setOnClickListener{
            builder.dismiss()
        }
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

    @SuppressLint("MissingInflatedId")
    fun createImageDialog(imageURL: String) {
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.custom_image_dialog,null)
        val cross :ImageView = view.findViewById(R.id.img_cross)
        val imageView : ImageView =view.findViewById(R.id.touchImageView)

        CommonUtils.showImageFromURL(
            context,
            imageURL,
            imageView,
            R.drawable.load
        )
        builder.setView(view)
        builder.setCanceledOnTouchOutside(false)
        builder.show()
        cross.setOnClickListener{
            builder.dismiss()
        }
    }

    fun showErrorMessage(message: String) {
            var gpsbuilder = AlertDialog.Builder(requireContext())
            gpsbuilder.setMessage(message)
            gpsbuilder.setTitle("Sorry")
                .setCancelable(true)
                .setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, id ->dialog.dismiss()
                    }
                )
            var alert = gpsbuilder.create()
            alert.show()

    }

}