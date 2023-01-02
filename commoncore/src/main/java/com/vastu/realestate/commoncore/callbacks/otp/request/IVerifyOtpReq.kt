package com.vastu.realestate.commoncore.callbacks.otp.request

import android.content.Context
import com.vastu.realestate.commoncore.callbacks.otp.response.IVerifyOtpResponseListener
import com.vastu.realestate.commoncore.model.otp.request.ObjVerifyOtpReq

interface IVerifyOtpReq {
    fun callVerifyOtpApi(context: Context,objVerifyOtpReq: ObjVerifyOtpReq, urlEndPoint:String,iVerifyOtpResponseListener: IVerifyOtpResponseListener)
}