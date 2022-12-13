package com.vastu.realestate.appModule.login.viewModel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.vastu.realestate.R
import com.vastu.realestate.appModule.login.uiInterfaces.ILoginViewListener
import com.vastu.realestate.logincore.callbacks.response.ILoginResponseListener
import com.vastu.realestate.logincore.model.response.ObjLoginResponse
import com.vastu.realestate.logincore.model.response.ObjLoginResponseMain
import com.vastu.realestate.logincore.repository.LoginRepository

class LoginViewModel(application: Application) : AndroidViewModel(application),
    ILoginResponseListener {
    var mobileNumber = ObservableField("")
    var isValidMobileNumber = ObservableField<Boolean>(false)
    var mContext :Application
    init {
        mContext = application
    }
    var errorVisible = ObservableField(View.GONE)
    var btnBackground = ObservableField(mContext.getDrawable(R.drawable.button_inactive_background))
    var error =ObservableField("Please enter your register number to proceed.")
    lateinit var iLoginViewListener : ILoginViewListener
    fun onSendOtpClick(){
        iLoginViewListener.onSendOtpClick()
    }

    fun callLoginApi(mobilenumber:String){
        LoginRepository.callLoginApi(mobilenumber,"login.php",this)
    }

    override fun onGetSuccessResponse(response: ObjLoginResponseMain) {
        iLoginViewListener.launchOtpScreen(response)

    }

    override fun onGetFailureResponse(response: ObjLoginResponseMain) {
       iLoginViewListener.onLoginFail(response.objLoginResponse)
    }
}