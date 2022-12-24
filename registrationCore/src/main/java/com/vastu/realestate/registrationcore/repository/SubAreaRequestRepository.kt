package com.vastu.realestate.registrationcore.repository

import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.realestate.commoncore.utils.ErrorCode
import com.vastu.realestate.registrationcore.callbacks.request.ISubAreaListReq
import com.vastu.realestate.registrationcore.callbacks.response.ISubAreaResponseListener
import com.vastu.realestate.registrationcore.model.request.ObjSubAreaReq
import com.vastu.realestate.registrationcore.model.response.subArea.ObjGetCityAreaDetailResponseMain

object SubAreaRequestRepository : ISubAreaListReq , IOnServiceResponseListener {
 lateinit var iSubAreaResponseListener: ISubAreaResponseListener

    override fun callSubAreaListApi(
        objSubAreaReq: ObjSubAreaReq,
        urlEndPoint: String,
        iSubAreaResponseListener: ISubAreaResponseListener
    ) {
        this.iSubAreaResponseListener = iSubAreaResponseListener
        NetworkDaoBuilder.Builder
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setIsRequestPut(false)
            .setRequest(builRequest(objSubAreaReq))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }
    fun builRequest(objSubAreaReq: ObjSubAreaReq): ByteArray {

        return Gson().toJson(objSubAreaReq).toByteArray()
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
        val response = parseResponse(response)
        when(response.objCityAreaResponse.objResponseStatusHdr.statusCode){
            ErrorCode.success ->
                iSubAreaResponseListener.onGetSubAreaResponseSuccess(response)
            ErrorCode.error_0001 ->
                iSubAreaResponseListener.onGetSubAreaResponseFailure(response)
            else->
                iSubAreaResponseListener.onGetSubAreaResponseFailure(response)
        }
    }

    override fun onFailureResponse(response: String) {
        iSubAreaResponseListener.onGetSubAreaResponseFailure(parseResponse(response))
    }

    fun parseResponse(response: String): ObjGetCityAreaDetailResponseMain {
        return Gson().fromJson(
            response,
            ObjGetCityAreaDetailResponseMain::class.java
        )
    }
}