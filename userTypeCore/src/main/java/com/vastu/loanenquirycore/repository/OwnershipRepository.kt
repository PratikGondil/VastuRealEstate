package com.vastu.loanenquirycore.repository

import com.google.gson.Gson
import com.vastu.loanenquirycore.callbacks.request.IGetOwnershipRequest
import com.vastu.loanenquirycore.callbacks.response.IGetOwnershipResponseListener
import com.vastu.loanenquirycore.model.response.ownership.OwnershipMainResponse
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.realestate.commoncore.utils.ErrorCode

object OwnershipRepository:IGetOwnershipRequest,IOnServiceResponseListener {
    private lateinit var iGetOwnershipResListener: IGetOwnershipResponseListener

    override fun callGetOwnership(
        urlEndPoint: String,
        iGetOwnershipResListener: IGetOwnershipResponseListener
    ) {
       this.iGetOwnershipResListener = iGetOwnershipResListener
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
       val ownershipMainRes = parseOwnershipResponse(response)
        when(ownershipMainRes.ownershipResponse.responseStatusHeader.statusCode){
            ErrorCode.success ->
                iGetOwnershipResListener.getOwnershipSuccessResponse(ownershipMainRes)
            ErrorCode.error_0001->
                iGetOwnershipResListener.getOwnershipFailureResponse(ownershipMainRes)
            else ->
                iGetOwnershipResListener.getOwnershipFailureResponse(ownershipMainRes)
        }
    }

    override fun onFailureResponse(response: String) {
      iGetOwnershipResListener.getOwnershipFailureResponse(parseOwnershipResponse(response))
    }

    override fun onUserNotConnected() {
       iGetOwnershipResListener.networkFailure()
    }

    private fun parseOwnershipResponse(response: String): OwnershipMainResponse {
        return Gson().fromJson(
            response,
            OwnershipMainResponse::class.java
        )
    }


}