package com.vastu.realestate.commoncore.callbacks.otp.request

import com.vastu.realestate.commoncore.callbacks.otp.response.IVerifyOtpResponseListener
import com.vastu.realestate.commoncore.model.otp.request.ObjVerifyOtpReq

interface IVerifyOtpReq {
    fun callVerifyOtpApi(objVerifyOtpReq: ObjVerifyOtpReq, urlEndPoint:String,iVerifyOtpResponseListener: IVerifyOtpResponseListener)
}