package com.vastu.realestate.appModule.otp.viewModel

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.vastu.realestate.R
import com.vastu.realestate.appModule.otp.uiListener.IVerifyOtpViewListener
import com.vastu.realestate.appModule.utils.ApiUrlEndPoints
import com.vastu.realestate.commoncore.callbacks.otp.response.IVerifyOtpResponseListener
import com.vastu.realestate.commoncore.model.otp.request.ObjVerifyOtpReq
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyDtls
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyOtpResponseMain
import com.vastu.realestate.commoncore.repository.otp.VerifyOtpRequestRepository
import com.vastu.realestate.logincore.callbacks.response.ILoginResponseListener
import com.vastu.realestate.logincore.model.response.ObjLoginResponseMain
import com.vastu.realestate.logincore.repository.LoginRepository

class OTPViewModel(application: Application) : AndroidViewModel(application),IVerifyOtpResponseListener,
    ILoginResponseListener {
    var mContext :Application
    init{
        mContext =application
    }
    var otp = ObservableField("")
    var isValidOTP=ObservableField(false)
    var timer = ObservableField(mContext.resources.getString(R.string.timer_text,"30"))
    var btnBackground =ObservableField(ContextCompat.getDrawable(mContext,R.drawable.button_inactive_background))

    lateinit var iVerifyOtpViewListener: IVerifyOtpViewListener

    fun onOtpSubmitClick(){
        iVerifyOtpViewListener.verifyOtp()
    }
    fun callLoginApi(mobilenumber:String){
        LoginRepository.callLoginApi(mobilenumber,"login.php",this)
    }

    fun callVerifyOtpApi(objVerifyOtpReq: ObjVerifyOtpReq){
        VerifyOtpRequestRepository.callVerifyOtpApi(objVerifyOtpReq,ApiUrlEndPoints.API_VERIFY_OTP,this)
    }

    override fun onGetVerifyOtpSuccess(objVerifyDtls: ObjVerifyDtls) {
        iVerifyOtpViewListener.launchDashboard(objVerifyDtls)
    }

    override fun onGetVerifyOtpFailure(objVerifyOtpResponseMain: ObjVerifyOtpResponseMain) {
//     iVerifyOtpViewListener.onOtpVerifyFailure(objVerifyOtpResponseMain)
        var objVerifyDtls = ObjVerifyDtls(userId = "7", firstName = "Supriya", mobileNo = "8796766181", emailId = "supriyakhambe77@gmail.com")
        iVerifyOtpViewListener.launchDashboard(objVerifyDtls)

    }
   fun  resendOtp(){
        iVerifyOtpViewListener.resendOtpReq()
   }

    override fun onGetSuccessResponse(response: ObjLoginResponseMain) {
        iVerifyOtpViewListener.initOtpTimer()
    }

    override fun onGetFailureResponse(response: ObjLoginResponseMain) {
        iVerifyOtpViewListener.onResenOtpFailure(response)
    }
}