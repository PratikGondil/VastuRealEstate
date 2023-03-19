package com.vastu.realestatecore.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.realestatecore.callback.request.IGetFilteredPropertyListReq
import com.vastu.realestatecore.callback.response.IFilterPropertyListResListener
import com.vastu.realestatecore.callback.response.IGetPropertyListResListener
import com.vastu.realestatecore.model.request.ObjFilterData
import com.vastu.realestatecore.model.response.ObjFilterDataResponseMain
import com.vastu.realestatecore.model.response.ObjGetPropertyListResMain
import com.vastu.usertypecore.model.request.ObjGetUserTypeReq
import com.vastu.utils.ErrorCode

object FilterPropertyListRepository: IGetFilteredPropertyListReq, IOnServiceResponseListener {
    lateinit var iFilterPropertyListResListener: IFilterPropertyListResListener

    override fun getFilterPropertyList(
        context: Context,
        objFilterData: ObjFilterData,
        urlEndPoint: String,
        iFilterPropertyListResListener: IFilterPropertyListResListener
    ) {
        this.iFilterPropertyListResListener = iFilterPropertyListResListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(objFilterData))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }
    private fun buildRequest(objFilterData: ObjFilterData): ByteArray {
        return Gson().toJson(objFilterData).toByteArray()
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
        val propertyListResponse = parseResponse(response)
        when(propertyListResponse.objfilterDataResponse.responseStatusHeader.statusCode){
            ErrorCode.success ->
            iFilterPropertyListResListener.onSuccessFilterList(propertyListResponse)
            ErrorCode.error_0001->
                iFilterPropertyListResListener.onFailureFilterList(propertyListResponse)
            else->
                iFilterPropertyListResListener.onFailureFilterList(propertyListResponse)
        }
    }

    override fun onFailureResponse(response: String) {
        val propertyListResponse = parseResponse(response)

        iFilterPropertyListResListener.onFailureFilterList(propertyListResponse)    }

    override fun onUserNotConnected() {
    }
    private fun parseResponse(response: String): ObjFilterDataResponseMain {
        return Gson().fromJson(
            response,
            ObjFilterDataResponseMain::class.java
        )
    }
}