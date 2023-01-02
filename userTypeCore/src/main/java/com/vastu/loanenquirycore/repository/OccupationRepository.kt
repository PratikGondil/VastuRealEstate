package com.vastu.loanenquirycore.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.loanenquirycore.callbacks.request.IGetOccupationRequest
import com.vastu.loanenquirycore.callbacks.response.IGetOccupationResponseListener
import com.vastu.loanenquirycore.model.response.occupation.OccupationMainResponse
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.utils.ErrorCode


object OccupationRepository:IGetOccupationRequest,IOnServiceResponseListener {
    private lateinit var iGetOccupationResListener: IGetOccupationResponseListener

    override fun callGetOccupation(
        context: Context,
        urlEndPoint: String,
        iGetOccupationResListener: IGetOccupationResponseListener
    ) {
        OccupationRepository.iGetOccupationResListener = iGetOccupationResListener
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
        val occupationResMain = parseOccupationResponse(response)
        when(occupationResMain.occupationResponse.responseStatusHeader.statusCode){
            ErrorCode.success->
                iGetOccupationResListener.getOccupationSuccessResponse(occupationResMain)
            ErrorCode.error_0001->
                iGetOccupationResListener.getOccupationFailureResponse(occupationResMain)
            else->
                iGetOccupationResListener.getOccupationFailureResponse(occupationResMain)
        }
    }

    override fun onFailureResponse(response: String) {
        iGetOccupationResListener.getOccupationFailureResponse(parseOccupationResponse(response))
    }

    override fun onUserNotConnected() {
       iGetOccupationResListener.networkFailure()
    }

    private fun parseOccupationResponse(response: String): OccupationMainResponse {
        return Gson().fromJson(
            response,
            OccupationMainResponse::class.java
        )
    }
}