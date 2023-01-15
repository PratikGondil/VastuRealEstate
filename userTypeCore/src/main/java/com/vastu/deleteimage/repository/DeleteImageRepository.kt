package com.vastu.deleteimage.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.deleteimage.callbacks.request.IDeleteImageRequest
import com.vastu.deleteimage.callbacks.response.IDeleteImageResponseListener
import com.vastu.deleteimage.model.request.DeleteImageRequest
import com.vastu.deleteimage.model.response.DeleteImageMainResponse
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.utils.ErrorCode

object DeleteImageRepository:IOnServiceResponseListener,IDeleteImageRequest {

    private lateinit var iDeleteImageResponseListener: IDeleteImageResponseListener

    override fun callDeleteImage(
        context: Context,
        deleteImageRequest: DeleteImageRequest,
        urlEndPoint: String,
        iDeleteImageResponseListener: IDeleteImageResponseListener
    ) {
        this.iDeleteImageResponseListener = iDeleteImageResponseListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(deleteImageRequest))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

    private fun buildRequest(deleteImageRequest: DeleteImageRequest): ByteArray {
        return Gson().toJson(deleteImageRequest).toByteArray()
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
       val deleteImageRes = parseResponse(response)
        when(deleteImageRes.imageDeleteResponse.responseStatusHeader.statusCode){
            ErrorCode.success ->
                iDeleteImageResponseListener.onDeleteImageSuccess(deleteImageRes)
            ErrorCode.error_0001->
                iDeleteImageResponseListener.onDeleteImageFailure(deleteImageRes)
            else->
                iDeleteImageResponseListener.onDeleteImageFailure(deleteImageRes)
        }
    }

    override fun onFailureResponse(response: String) {
        iDeleteImageResponseListener.onDeleteImageFailure(parseResponse(response))
    }

    override fun onUserNotConnected() {
        iDeleteImageResponseListener.networkFailure()
    }
    private fun parseResponse(response: String): DeleteImageMainResponse {
        return Gson().fromJson(
            response,
            DeleteImageMainResponse::class.java
        )
    }

}