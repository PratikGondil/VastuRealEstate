package com.vastu.realestate.commoncore.callbacks.otp.response

import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyDtls
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyOtpResponseMain
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IVerifyOtpResponseListener: NetworkFailedListener {
    fun onGetVerifyOtpSuccess(objVerifyDtls: ObjVerifyDtls)
    fun onGetVerifyOtpFailure(objVerifyOtpResponseMain: ObjVerifyOtpResponseMain)
}