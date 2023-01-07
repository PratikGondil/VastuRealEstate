package com.vastu.enquiry.property.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.enquiry.property.callbacks.request.IGetPropertyEnquiryRequest
import com.vastu.enquiry.property.callbacks.response.IGetPropertyEnquiryListResponseListener
import com.vastu.enquiry.property.model.response.GetPropertyEnquiryListMainResponse
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.utils.ErrorCode

object GetPropertyEnquiryRepository:IGetPropertyEnquiryRequest,IOnServiceResponseListener {

   private lateinit var iGetPropertyEnquiryListResponseListener: IGetPropertyEnquiryListResponseListener

   override fun callGetPropertyEnquiryList(
       context: Context,
       urlEndPoint: String,
       iGetPropertyEnquiryListResponseListener: IGetPropertyEnquiryListResponseListener
    ) {
        this.iGetPropertyEnquiryListResponseListener = iGetPropertyEnquiryListResponseListener
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

       val propertyListResponse = parseGetPropertyEnquiryResponse(response)

        when(propertyListResponse.enquiryDataResponse.responseStatusHeader.statusCode){
            ErrorCode.success->
                iGetPropertyEnquiryListResponseListener.getPropertyEnquiryListSuccess(propertyListResponse)
            ErrorCode.error_0001->
                iGetPropertyEnquiryListResponseListener.getPropertyEnquiryListFailure(propertyListResponse)
            else->
                iGetPropertyEnquiryListResponseListener.getPropertyEnquiryListFailure(propertyListResponse)
        }
    }

    override fun onFailureResponse(response: String) {
        iGetPropertyEnquiryListResponseListener.getPropertyEnquiryListFailure(
            parseGetPropertyEnquiryResponse(response)
        )
    }

    override fun onUserNotConnected() {
       iGetPropertyEnquiryListResponseListener.networkFailure()
    }

    private fun parseGetPropertyEnquiryResponse(response: String):GetPropertyEnquiryListMainResponse {
        return Gson().fromJson(
            response,
            GetPropertyEnquiryListMainResponse::class.java
        )
    }
}