package com.vastu.loanenquirycore.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.loanenquirycore.callbacks.request.IAddLoanEnquiryRequest
import com.vastu.loanenquirycore.callbacks.response.IAddLoanEnquiryResponseListener
import com.vastu.loanenquirycore.model.request.AddLoanEnquiryRequest
import com.vastu.loanenquirycore.model.response.enquiry.EnquiryMainResponse
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.utils.ErrorCode

object AddLoanEnquiryRepository:IAddLoanEnquiryRequest,IOnServiceResponseListener {

    private lateinit var iAddLoanEnquiryResponseListener: IAddLoanEnquiryResponseListener

    override fun callAddLoanEnquiry(
        context: Context,
        urlEndPoint: String,
        addLoanEnquiryRequest: AddLoanEnquiryRequest,
        iAddLoanEnquiryResponseListener: IAddLoanEnquiryResponseListener
    ) {
        this.iAddLoanEnquiryResponseListener = iAddLoanEnquiryResponseListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(addLoanEnquiryRequest))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)

    }

    private fun buildRequest(addLoanEnquiryRequest: AddLoanEnquiryRequest): ByteArray {
        return Gson().toJson(addLoanEnquiryRequest).toByteArray()
    }


    override fun onSuccessResponse(response: String, isError: Boolean) {
        val enquiryResponse = parseResponse(response)
        when(enquiryResponse.registerResponse.responseStatusHeader.statusCode){
            ErrorCode.success ->
                iAddLoanEnquiryResponseListener.onAddLoanEnquirySuccessResponse(enquiryResponse)
            ErrorCode.error_0001->
                iAddLoanEnquiryResponseListener.onAddLoanEnquiryFailureResponse(enquiryResponse)
            else->
                iAddLoanEnquiryResponseListener.onAddLoanEnquiryFailureResponse(enquiryResponse)
        }
    }

    override fun onFailureResponse(response: String) {
        iAddLoanEnquiryResponseListener.onAddLoanEnquiryFailureResponse(parseResponse(response))
    }

    override fun onUserNotConnected() {
        iAddLoanEnquiryResponseListener.networkFailure()
    }

    private fun parseResponse(response: String): EnquiryMainResponse {
        return Gson().fromJson(
            response,
           EnquiryMainResponse::class.java
        )
    }

}