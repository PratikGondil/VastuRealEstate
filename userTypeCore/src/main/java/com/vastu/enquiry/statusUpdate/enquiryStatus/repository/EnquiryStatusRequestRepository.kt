package com.vastu.enquiry.statusUpdate.enquiryStatus.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.enquiry.statusUpdate.enquiryStatus.callbacks.IGetEnquiryStatusReq
import com.vastu.enquiry.statusUpdate.enquiryStatus.callbacks.IGetEnquiryStatusResponse
import com.vastu.enquiry.statusUpdate.enquiryStatus.model.response.ObjEnquiryStatusResponseMain
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.utils.ErrorCode

object EnquiryStatusRequestRepository:IGetEnquiryStatusReq,IOnServiceResponseListener {
    private lateinit var iGetEnquiryStatusResponse: IGetEnquiryStatusResponse
    override fun getEnquiryStatusList(
        context: Context,
        urlEndPoint: String,
        iGetEnquiryStatusResponse: IGetEnquiryStatusResponse
    ) {
        this.iGetEnquiryStatusResponse = iGetEnquiryStatusResponse
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


    override fun onSuccessResponse(response: String, isError: Boolean) {

        val enquiryStatusResponse = parseResponse(response)

        when(enquiryStatusResponse.objEnquiryStatusResponse.responseStatusHeader?.statusCode){
            ErrorCode.success->
                iGetEnquiryStatusResponse.onGetEnquirySuccessResponse(enquiryStatusResponse)
            ErrorCode.error_0001->
                iGetEnquiryStatusResponse.onGetEnquiryFailure(enquiryStatusResponse)
            else->
                iGetEnquiryStatusResponse.onGetEnquiryFailure(enquiryStatusResponse)
        }
    }

    override fun onFailureResponse(response: String) {
        iGetEnquiryStatusResponse.onGetEnquiryFailure(
            parseResponse(response)
        )
    }

    override fun onUserNotConnected() {
    }

    private fun parseResponse(response: String): ObjEnquiryStatusResponseMain {
        return Gson().fromJson(
            response,
            ObjEnquiryStatusResponseMain::class.java
        )
    }


}