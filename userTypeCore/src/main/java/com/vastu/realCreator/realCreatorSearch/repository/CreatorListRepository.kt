package com.vastu.realCreator.realCreatorSearch.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.realCreator.realCreatorSearch.callback.IGetCreatorListReq
import com.vastu.realCreator.realCreatorSearch.callback.IGetCreatorListResListener
import com.vastu.realCreator.realCreatorSearch.model.ObjCreatorListReq
import com.vastu.realCreator.realCreatorSearch.model.ObjCreatorListRes
import com.vastu.utils.ErrorCode

object CreatorListRepository :IGetCreatorListReq, IOnServiceResponseListener {
    lateinit var iGetCreatorListResListener: IGetCreatorListResListener

    private fun buildRequest(request: String,language: String): ByteArray {
        val objCreationReq = ObjCreatorListReq(request,language)
        return Gson().toJson(objCreationReq).toByteArray()
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
        val creatorListResponse = parseResponse(response)
        when(creatorListResponse.profileResponse.responseStatusHeader.statusCode){
            ErrorCode.success ->
                iGetCreatorListResListener.getCreatorListSuccessResponse(creatorListResponse)
            ErrorCode.error_0001->
                iGetCreatorListResListener.getCreatorListFailureResponse(creatorListResponse)
            else->
                iGetCreatorListResListener.getCreatorListFailureResponse(creatorListResponse)
        }
    }

    override fun onFailureResponse(response: String) {
        iGetCreatorListResListener.getCreatorListFailureResponse(parseResponse( response))
    }

    override fun onUserNotConnected() {
        iGetCreatorListResListener.networkFailure()
    }

    private fun parseResponse(response: String): ObjCreatorListRes {
        return Gson().fromJson(
            response,
            ObjCreatorListRes::class.java
        )
    }

    override fun callGetCreatorList(
        context: Context,
        userId: String,
        language: String,
        urlEndPoint: String,
        iGetCreatorListResListener: IGetCreatorListResListener
    ) {
        this.iGetCreatorListResListener=iGetCreatorListResListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(userId,language))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }
}
