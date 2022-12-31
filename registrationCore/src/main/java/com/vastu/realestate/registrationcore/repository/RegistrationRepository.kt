package com.vastu.realestate.registrationcore.repository

import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.realestate.commoncore.utils.ErrorCode
import com.vastu.realestate.registrationcore.callbacks.request.ISignUpReq
import com.vastu.realestate.registrationcore.callbacks.response.IResgisterResponseListener
import com.vastu.realestate.registrationcore.model.request.ObjUserInfo
import com.vastu.realestate.registrationcore.model.response.registration.ObjRegisterResponseMain

object RegistrationRepository : ISignUpReq,IOnServiceResponseListener {
    lateinit var iResgisterResponseListener : IResgisterResponseListener
    override fun callRegisterUserApi(objUserInfo: ObjUserInfo,urlEndPoint:String,iResgisterResponseListener:IResgisterResponseListener) {
       this.iResgisterResponseListener = iResgisterResponseListener
        NetworkDaoBuilder.Builder
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setIsRequestPut(false)
            .setRequest(builRequest(objUserInfo))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }


    fun builRequest(objUserInfo: ObjUserInfo): ByteArray {

        return Gson().toJson(objUserInfo).toByteArray()
    }

    override fun onSuccessResponse(response: String,isError:Boolean) {
           val registerResponse = parseResponse(response)
           when (registerResponse.objRegisterResponse.objResponseStatusHdr.statusCode) {
               ErrorCode.success ->
                   iResgisterResponseListener.onGetSuccessResponse(registerResponse)
               ErrorCode.error_0001 ->
                   iResgisterResponseListener.onAlreadyExistUser(registerResponse)
               ErrorCode.error_0003 ->
                   iResgisterResponseListener.onGetFailureResponse(parseResponse(response))
               else ->
                   iResgisterResponseListener.onGetFailureResponse(parseResponse(response))
           }

    }

    override fun onFailureResponse(response: String) {
        iResgisterResponseListener.onGetFailureResponse(parseResponse(response))
    }

    override fun onUserNotConnected() {

    }

    fun parseResponse(response: String): ObjRegisterResponseMain {
        return Gson().fromJson(
            response,
            ObjRegisterResponseMain::class.java
        )
    }


}