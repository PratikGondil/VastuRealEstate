package com.vastu.realestate.appModule.otp.uiListener

import com.vastu.realestate.commoncore.model.otp.request.ObjVerifyOtpReq
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyDtls
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyOtpResponseMain
import com.vastu.realestate.logincore.model.response.ObjLoginResponseMain

interface IVerifyOtpViewListener {
    fun verifyOtp()
    fun launchDashboard(objVerifyDtls: ObjVerifyDtls)
    fun onOtpVerifyFailure(objVerifyOtpResponseMain: ObjVerifyOtpResponseMain)
    fun onBackClick()
    fun resendOtpReq()
    fun initOtpTimer()
    fun onResenOtpFailure(objLoginResponseMain: ObjLoginResponseMain)
}