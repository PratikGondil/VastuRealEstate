package com.vastu.loanenquirycore.repository

import com.google.gson.Gson
import com.vastu.loanenquirycore.callbacks.request.IGetPropertyInterestRequest
import com.vastu.loanenquirycore.callbacks.response.IGetPropertyInterestResponseListener
import com.vastu.loanenquirycore.model.response.interest.property.PropertyInterestMainResponse
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.realestate.commoncore.utils.ErrorCode

object PropertyInterestRepository:IGetPropertyInterestRequest,IOnServiceResponseListener {

    private lateinit var iGetPropertyInterestResponseListener: IGetPropertyInterestResponseListener

    override fun callGetPropertyInterest(
        urlEndPoint: String,
        iGetPropertyInterestResponseListener: IGetPropertyInterestResponseListener
    ) {
        this.iGetPropertyInterestResponseListener = iGetPropertyInterestResponseListener
        NetworkDaoBuilder.Builder
            .setIsContentTypeJSON(true)
            .setIsRequestPost(false)
            .setIsRequestPut(false)
            .setRequest(HashMap<String, String>())
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
       val propertyInterestResponse = parsePropertyInterestResponse(response)
        when(propertyInterestResponse.interestedInResponse.responseStatusHeader.statusCode){
            ErrorCode.success->
                iGetPropertyInterestResponseListener.getPropertyInterestSuccessResponse(propertyInterestResponse)
            ErrorCode.error_0001->
                iGetPropertyInterestResponseListener.getPropertyInterestFailureResponse(propertyInterestResponse)
            else->
                iGetPropertyInterestResponseListener.getPropertyInterestFailureResponse(propertyInterestResponse)
        }
    }

    override fun onFailureResponse(response: String) {
        iGetPropertyInterestResponseListener.getPropertyInterestFailureResponse(parsePropertyInterestResponse(response))
    }

    override fun onUserNotConnected() {
        iGetPropertyInterestResponseListener.networkFailure()
    }

    private fun parsePropertyInterestResponse(response: String): PropertyInterestMainResponse {
        return Gson().fromJson(
            response,
            PropertyInterestMainResponse::class.java
        )
    }

}