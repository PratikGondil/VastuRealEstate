package com.vastu.realestate.logincore.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.realestate.commoncore.utils.ErrorCode
import com.vastu.realestate.logincore.callbacks.request.ILoginReq
import com.vastu.realestate.logincore.callbacks.response.ILoginResponseListener
import com.vastu.realestate.logincore.model.request.ObjLoginReq
import com.vastu.realestate.logincore.model.response.ObjLoginResponseMain

object LoginRepository : ILoginReq,IOnServiceResponseListener {
    lateinit var iLoginResponseListener :ILoginResponseListener
    override fun callLoginApi(context: Context,mobileNumber: String,language:String, urlEndPoint:String, iOnGetLoginResponse: ILoginResponseListener) {
        this.iLoginResponseListener = iOnGetLoginResponse
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(mobileNumber,language))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }
    fun buildRequest(request: String, language: String): ByteArray {
        var objLoginReq = ObjLoginReq(request,language)
        return Gson().toJson(objLoginReq).toByteArray()
    }

    override fun onSuccessResponse(response: String,isError:Boolean) {
        val loginResponse = parseResponse(response)
        when(loginResponse.objLoginResponse.objResponseStatusHdr.statusCode){
            ErrorCode.success->
                iLoginResponseListener.onGetSuccessResponse(loginResponse)
            ErrorCode.error_0002->
                iLoginResponseListener.onGetFailureResponse(loginResponse)
            else->
                iLoginResponseListener.onGetFailureResponse(loginResponse)
        }

    }

    override fun onFailureResponse(response: String) {
        iLoginResponseListener.onGetFailureResponse(parseResponse(response))
    }

    override fun onUserNotConnected() {
       iLoginResponseListener.networkFailure()
    }

    private fun parseResponse(response: String): ObjLoginResponseMain {
        return Gson().fromJson(
            response,
            ObjLoginResponseMain::class.java
        )

    }

}