package com.vastu.realestate.appModule.feedback

import android.content.Context
import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.realestate.commoncore.utils.ErrorCode

object FeedbackRepository : IFeedbackRequest, IOnServiceResponseListener {
    private lateinit var iFeedbackResponseListener: IFeedbackResponseListener

    override fun callFeedbackApi(
        context: Context,
        userId: String,
        feedbackSelect: String,
        feedbackText: String,
        urlEndPoint: String,
        iFeedbackResponseListener: IFeedbackResponseListener
    ) {
        this.iFeedbackResponseListener = iFeedbackResponseListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(userId, feedbackSelect, feedbackText))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

    fun buildRequest(userId: String, feedbackSelect: String, feedbackText: String): ByteArray {
        var objFeedbackReq = GetFeedbackRequest(userId, feedbackSelect, feedbackText)
        return Gson().toJson(objFeedbackReq).toByteArray()
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
        var feedbackRes = parseResponse(response)
        when (feedbackRes.objFeedbackResponse.objResponseStatusHdr.statusCode) {
            ErrorCode.success ->
                iFeedbackResponseListener.onGetSuccessResponse(feedbackRes)
            ErrorCode.error_0002 ->
                iFeedbackResponseListener.onGetFailureResponse(feedbackRes)
            else ->
                iFeedbackResponseListener.onGetFailureResponse(feedbackRes)
        }
    }

    override fun onFailureResponse(response: String) {
        iFeedbackResponseListener.onGetFailureResponse(parseResponse(response))
    }

    override fun onUserNotConnected() {
        iFeedbackResponseListener.networkFailure()
    }

    private fun parseResponse(response: String): FeedbackDataResponseMain {
        return Gson().fromJson(
            response,
            FeedbackDataResponseMain::class.java
        )
    }
}