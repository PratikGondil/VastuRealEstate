package com.vastu.realestate.logincore.repository

import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyOtpResponseMain
import com.vastu.realestate.commoncore.utils.ErrorCode
import com.vastu.realestate.logincore.callbacks.request.ILoginReq
import com.vastu.realestate.logincore.callbacks.response.ILoginResponseListener
import com.vastu.realestate.logincore.model.request.ObjLoginReq
import com.vastu.realestate.logincore.model.response.ObjLoginResponse

object LoginRepository : ILoginReq,IOnServiceResponseListener {
    lateinit var iLoginResponseListener :ILoginResponseListener
    override fun callLoginApi(mobileNumber: String,urlEndPoint:String,iLoginResponseListener: ILoginResponseListener) {
        this.iLoginResponseListener = iLoginResponseListener
        NetworkDaoBuilder.Builder
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(builRequest(mobileNumber))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }
    fun builRequest(request: String): ByteArray {
        var objLoginReq = ObjLoginReq(request)
        return Gson().toJson(objLoginReq).toByteArray()
    }

    override fun onSuccessResponse(response: String,isError:Boolean) {
        val loginResponse = parseResponse(response)
        when(loginResponse.objResponseStatusHdr.statusCode){
            ErrorCode.success->
                iLoginResponseListener.onGetSuccessResponse(loginResponse)
            ErrorCode.error_0002->
                iLoginResponseListener.onGetFailureResponse(loginResponse)
        }

    }

    override fun onFailureResponse(response: String) {
        iLoginResponseListener.onGetFailureResponse(parseResponse(response))
    }
    fun parseResponse(response: String): ObjLoginResponse {
        return Gson().fromJson(
            response,
            ObjLoginResponse::class.java
        )

    }

}