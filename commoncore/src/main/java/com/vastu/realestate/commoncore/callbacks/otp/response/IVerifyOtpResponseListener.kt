package com.vastu.realestate.commoncore.callbacks.otp.response

import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyDtls
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyOtpResponseMain

interface IVerifyOtpResponseListener {
    fun onGetVerifyOtpSuccess(objVerifyDtls: ObjVerifyDtls)
    fun onGetVerifyOtpFailure(objVerifyOtpResponseMain: ObjVerifyOtpResponseMain)
}