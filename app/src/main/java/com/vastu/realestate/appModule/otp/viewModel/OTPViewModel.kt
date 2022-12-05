package com.vastu.realestate.appModule.otp.viewModel

import android.app.Application
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

class OTPViewModel(application: Application) : AndroidViewModel(application),IVerifyOtpResponseListener {
    var mContext :Application
    init{
        mContext =application
    }
    var otp = ObservableField("")
    var isValidOTP=ObservableField(false)
    var timer = ObservableField(mContext.resources.getString(R.string.timer_text,"30"))
    lateinit var iVerifyOtpViewListener: IVerifyOtpViewListener

    fun onOtpSubmitClick(){
        iVerifyOtpViewListener.verifyOtp()
    }

    fun callVerifyOtpApi(objVerifyOtpReq: ObjVerifyOtpReq){
        VerifyOtpRequestRepository.callVerifyOtpApi(objVerifyOtpReq,ApiUrlEndPoints.API_VERIFY_OTP,this)
    }

    override fun onGetVerifyOtpSuccess(objVerifyDtls: ObjVerifyDtls) {
        iVerifyOtpViewListener.launchDashboard()
    }

    override fun onGetVerifyOtpFailure(objVerifyOtpResponseMain: ObjVerifyOtpResponseMain) {
        TODO("Not yet implemented")
    }
}