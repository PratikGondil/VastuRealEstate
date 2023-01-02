package com.vastu.realestate.registrationcore.repository

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.realestate.commoncore.utils.ErrorCode
import com.vastu.realestate.registrationcore.callbacks.request.ICityListReq
import com.vastu.realestate.registrationcore.callbacks.response.ITalukaResponseListener
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaResponseMain

object CityListRequestRepository : ICityListReq,IOnServiceResponseListener {
    lateinit var iTalukaResponseListener: ITalukaResponseListener

     override fun callCityListApi(context: Context,urlEndPoint:String, iTalukaResponseListener: ITalukaResponseListener){
        this.iTalukaResponseListener = iTalukaResponseListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(false)
            .setIsRequestPut(false)
            .setRequest(HashMap<String, String>())
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onSuccessResponse(response: String, isError: Boolean) {
        val talukaResponseMain = parseTalukaResponse(response)
           when (talukaResponseMain.objTalukaResponse.objResponseStatusHdr.statusCode){
               ErrorCode.success ->
                   iTalukaResponseListener.onTalukaListSuccessResponse(talukaResponseMain)
               else->
                   iTalukaResponseListener.onTalukaListFailureResponse(talukaResponseMain)
           }
    }

    override fun onFailureResponse(response: String) {
        iTalukaResponseListener.onTalukaListFailureResponse(parseTalukaResponse(response))
    }

    override fun onUserNotConnected() {
       iTalukaResponseListener.networkFailure()
    }


    fun parseTalukaResponse(response: String): ObjTalukaResponseMain {
        return Gson().fromJson(
            response,
            ObjTalukaResponseMain::class.java
        )
    }
}