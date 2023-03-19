package com.vastu.termsandconditions.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.termsandconditions.callbacks.request.IGetTermsConditionRequest
import com.vastu.termsandconditions.callbacks.response.ITermsConditionResponseListener
import com.vastu.termsandconditions.model.respone.TermsConditionMainResponse
import com.vastu.utils.ErrorCode

object TermsConditionRepository :IOnServiceResponseListener,IGetTermsConditionRequest{
  private lateinit var iTermsConditionResponseListener: ITermsConditionResponseListener
    override fun callTermsCondition(
        context: Context,
        urlEndPoint: String,
        iTermsConditionResponseListener: ITermsConditionResponseListener
    ) {
        this.iTermsConditionResponseListener = iTermsConditionResponseListener
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
        val termsRes = parseResponse(response)
        when(termsRes.termsConditionResponse.responseStatusHeader.statusCode){
            ErrorCode.success->
                iTermsConditionResponseListener.onTermsConditionSuccess(termsRes)
            ErrorCode.error_0001->
                iTermsConditionResponseListener.onTermsConditionFailure(termsRes)
            else->
                iTermsConditionResponseListener.onTermsConditionFailure(termsRes)
        }
    }

    override fun onFailureResponse(response: String) {
        iTermsConditionResponseListener.onTermsConditionFailure(parseResponse(response))
    }

    override fun onUserNotConnected() {
        iTermsConditionResponseListener.networkFailure()
    }
    private fun parseResponse(response: String): TermsConditionMainResponse {
        return Gson().fromJson(
            response,
            TermsConditionMainResponse::class.java
        )
    }
}