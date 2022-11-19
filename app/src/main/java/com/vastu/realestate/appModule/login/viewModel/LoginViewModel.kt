package com.vastu.realestate.appModule.login.viewModel

import android.app.Application
import android.database.Observable
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.vastu.realestate.appModule.login.uiInterfaces.ILoginViewListener

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    var mobileNumber = ObservableField("")
    var title = ObservableField("")
    var otp = ObservableField("")
    var isVisibleOtpLayout = ObservableField(View.GONE)
    var isVisibleSignupTextLayout = ObservableField(View.VISIBLE)
    var isInputLayoutVisible = ObservableField(View.VISIBLE)
    var otpSentNumber = ObservableField("")
    lateinit var iLoginViewListener : ILoginViewListener
    fun onNextBtnClick(){
        iLoginViewListener.onNextBtnClick()
    }
}