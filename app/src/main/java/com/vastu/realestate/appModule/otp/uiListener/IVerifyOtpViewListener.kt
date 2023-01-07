package com.vastu.realestate.appModule.otp.uiListener

import com.vastu.realestate.appModule.enquirylist.uiinterfaces.INetworkFailListener
import com.vastu.realestate.commoncore.model.otp.request.ObjVerifyOtpReq
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyDtls
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyOtpResponseMain
import com.vastu.realestate.logincore.model.response.ObjLoginResponseMain
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IVerifyOtpViewListener : INetworkFailListener {
    fun verifyOtp()
    fun launchDashboard(objVerifyDtls: ObjVerifyDtls)
    fun onOtpVerifyFailure(objVerifyOtpResponseMain: ObjVerifyOtpResponseMain)
    fun onBackClick()
    fun resendOtpReq()
    fun initOtpTimer()
    fun onResendOtpFailure(objLoginResponseMain: ObjLoginResponseMain)
}