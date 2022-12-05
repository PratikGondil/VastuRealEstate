package com.vastu.realestate.appModule.otp.uiListener

import com.vastu.realestate.commoncore.model.otp.request.ObjVerifyOtpReq

interface IVerifyOtpViewListener {
    fun verifyOtp()
    fun launchDashboard()
}