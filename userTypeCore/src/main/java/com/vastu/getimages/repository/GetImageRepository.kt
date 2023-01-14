package com.vastu.getimages.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.getimages.callbacks.request.IGetImagesRequest
import com.vastu.getimages.callbacks.response.IGetImagesResponseListener
import com.vastu.getimages.model.request.GetImageRequest
import com.vastu.getimages.model.response.GetImageMainResponse
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.utils.ErrorCode

object GetImageRepository :IOnServiceResponseListener,IGetImagesRequest{

    private lateinit var iGetImagesResponseListener: IGetImagesResponseListener

    override fun callGetImagesApi(
        context: Context,
        getImageRequest: GetImageRequest,
        urlEndPoint: String,
        iGetImagesResponseListener: IGetImagesResponseListener
    ) {
       this.iGetImagesResponseListener = iGetImagesResponseListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(getImageRequest))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

    private fun buildRequest(getImageRequest: GetImageRequest): ByteArray {
        return Gson().toJson(getImageRequest).toByteArray()
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
       val imageResponse = parseResponse(response)
        when(imageResponse.imageResponse.responseStatusHeader.statusCode){
            ErrorCode.success->
                iGetImagesResponseListener.onGetImagesSuccess(imageResponse)
            ErrorCode.error_0001->
                iGetImagesResponseListener.onGetImageFailure(imageResponse)
            else->
                iGetImagesResponseListener.onGetImageFailure(imageResponse)
        }
    }

    override fun onFailureResponse(response: String) {
       iGetImagesResponseListener.onGetImageFailure(parseResponse(response))
    }

    override fun onUserNotConnected() {
       iGetImagesResponseListener.networkFailure()
    }
    private fun parseResponse(response: String): GetImageMainResponse {
        return Gson().fromJson(
            response,
            GetImageMainResponse::class.java
        )
    }
}