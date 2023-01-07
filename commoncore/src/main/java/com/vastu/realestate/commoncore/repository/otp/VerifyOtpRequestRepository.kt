package com.vastu.realestate.commoncore.repository.otp

import android.content.Context
import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.realestate.commoncore.callbacks.otp.request.IVerifyOtpReq
import com.vastu.realestate.commoncore.callbacks.otp.response.IVerifyOtpResponseListener
import com.vastu.realestate.commoncore.model.otp.request.ObjVerifyOtpReq
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyDtls
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyOtpResponseMain
import com.vastu.realestate.commoncore.utils.ErrorCode

object VerifyOtpRequestRepository:IVerifyOtpReq ,IOnServiceResponseListener{
    lateinit var iVerifyOtpResponseListener:IVerifyOtpResponseListener
    override fun callVerifyOtpApi(context: Context,objVerifyOtpReq: ObjVerifyOtpReq,urlEndPoint:String,iVerifyOtpResponseListener:IVerifyOtpResponseListener) {
        this.iVerifyOtpResponseListener = iVerifyOtpResponseListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(builRequest(objVerifyOtpReq))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

    fun builRequest(objVerifyOtpReq: ObjVerifyOtpReq): ByteArray {

        return Gson().toJson(objVerifyOtpReq).toByteArray()
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
        var objVerifyOtpResponseMain = parseResponse(response)
        when(objVerifyOtpResponseMain.objVerifyResponse.objResponseStatusHdr.statusCode){
            ErrorCode.success->
                iVerifyOtpResponseListener.onGetVerifyOtpSuccess(objVerifyOtpResponseMain.objVerifyDtls)
            ErrorCode.error_0001->
                iVerifyOtpResponseListener.onGetVerifyOtpFailure(objVerifyOtpResponseMain)
            else->
                iVerifyOtpResponseListener.onGetVerifyOtpFailure(objVerifyOtpResponseMain)

        }
    }

    override fun onFailureResponse(response: String) {
       iVerifyOtpResponseListener.onGetVerifyOtpFailure(parseResponse(response))
    }

    override fun onUserNotConnected() {
        iVerifyOtpResponseListener.networkFailure()
    }

    fun parseResponse(response: String):ObjVerifyOtpResponseMain{
        return Gson().fromJson(
            response,
            ObjVerifyOtpResponseMain::class.java
        )

    }
}