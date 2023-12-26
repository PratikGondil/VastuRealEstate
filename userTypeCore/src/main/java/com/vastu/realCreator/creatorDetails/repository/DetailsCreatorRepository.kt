package com.vastu.realCreator.creatorDetails.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.realCreator.creatorDetails.callback.IGetCreatorDetailsReq
import com.vastu.realCreator.creatorDetails.callback.IGetCreatorDetailsResListener
import com.vastu.realCreator.creatorDetails.model.ObjDetailsCreatorReq
import com.vastu.realCreator.creatorDetails.model.ObjDetailsCreatorRes
import com.vastu.utils.ErrorCode

object DetailsCreatorRepository : IGetCreatorDetailsReq, IOnServiceResponseListener {

    private fun buildRequest(request: String, language: String): ByteArray {
        val objDetailsCreatorReq = ObjDetailsCreatorReq(language, request)
        return Gson().toJson(objDetailsCreatorReq).toByteArray()
    }

    lateinit var iGetCreatorDetailsResListener: IGetCreatorDetailsResListener
    override fun callGetCreatorDetailsList(
        context: Context,
        profileId: String,
        language: String,
        urlEndPoint: String,
        iGetCreatorDetailsResListener: IGetCreatorDetailsResListener
    ) {
        this.iGetCreatorDetailsResListener = iGetCreatorDetailsResListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(profileId, language))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
        var detailsResponse = parseResponse(response)
        when (detailsResponse.singalRealCreatorResponse.responseStatusHeader.statusCode){
            ErrorCode.success ->
                iGetCreatorDetailsResListener.getDetailsCreatorSuccessResponse(detailsResponse)
            ErrorCode.error_0001 ->
                iGetCreatorDetailsResListener.getDetailsCreatorFailureResponse(detailsResponse)
            else ->
                iGetCreatorDetailsResListener.getDetailsCreatorFailureResponse(detailsResponse)

        }

    }

    private fun parseResponse(response: String): ObjDetailsCreatorRes {
        return Gson().fromJson(
            response,
            ObjDetailsCreatorRes::class.java
        )
    }

    override fun onFailureResponse(response: String) {
        iGetCreatorDetailsResListener.getDetailsCreatorFailureResponse(parseResponse(response))
    }

    override fun onUserNotConnected() {
        iGetCreatorDetailsResListener.networkFailure()
    }


}