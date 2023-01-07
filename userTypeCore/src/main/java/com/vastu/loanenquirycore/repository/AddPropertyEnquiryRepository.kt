package com.vastu.loanenquirycore.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.loanenquirycore.callbacks.request.IAddPropertyEnquiryRequest
import com.vastu.loanenquirycore.callbacks.response.IAddPropertyEnquiryResponseListener
import com.vastu.loanenquirycore.model.request.AddLoanEnquiryRequest
import com.vastu.loanenquirycore.model.request.AddPropertyEnquiryRequest
import com.vastu.loanenquirycore.model.response.enquiry.EnquiryMainResponse
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.utils.ErrorCode

object AddPropertyEnquiryRepository:IAddPropertyEnquiryRequest,IOnServiceResponseListener {

    private lateinit var iAddPropertyEnquiryResponseListener: IAddPropertyEnquiryResponseListener
    override fun callAddPropertyEnquiry(
        context: Context,
        urlEndPoint: String,
        addPropertyEnquiryRequest: AddPropertyEnquiryRequest,
        iAddPropertyEnquiryResponseListener: IAddPropertyEnquiryResponseListener
    ) {
       this.iAddPropertyEnquiryResponseListener = iAddPropertyEnquiryResponseListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(addPropertyEnquiryRequest))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }
    private fun buildRequest(addPropertyEnquiryRequest: AddPropertyEnquiryRequest): ByteArray {
        return Gson().toJson(addPropertyEnquiryRequest).toByteArray()
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
        val enquiryResponse = parseResponse(response)
        when(enquiryResponse.registerResponse.responseStatusHeader.statusCode){
            ErrorCode.success->
                iAddPropertyEnquiryResponseListener.onAddPropertyEnquirySuccessResponse(enquiryResponse)
            ErrorCode.error_0001->
                iAddPropertyEnquiryResponseListener.onAddPropertyEnquiryFailureResponse(enquiryResponse)
            else->
                iAddPropertyEnquiryResponseListener.onAddPropertyEnquiryFailureResponse(enquiryResponse)
        }
    }

    override fun onFailureResponse(response: String) {
        iAddPropertyEnquiryResponseListener.onAddPropertyEnquiryFailureResponse(parseResponse(response))
    }

    override fun onUserNotConnected() {
       iAddPropertyEnquiryResponseListener.networkFailure()
    }

    private fun parseResponse(response: String): EnquiryMainResponse {
        return Gson().fromJson(
            response,
            EnquiryMainResponse::class.java
        )
    }
}