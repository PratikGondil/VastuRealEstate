package com.vastu.realCreator.realCreatorList.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.realCreator.realCreatorList.callback.IGetRealCreatorListReq
import com.vastu.realCreator.realCreatorList.callback.IGetRealCreatorListResListener
import com.vastu.realCreator.realCreatorList.model.ObjRealCreatorListReq
import com.vastu.realCreator.realCreatorList.model.ObjRealCreatorListRes
import com.vastu.utils.ErrorCode

object RealCreatorListRepository : IGetRealCreatorListReq, IOnServiceResponseListener {
    lateinit var iGetRealCreatorListResListener: IGetRealCreatorListResListener

    fun buildRequest(
        request: String,
        language: String,
        taluka_id: String,
        subarea: String,
        userID: String
    ): ByteArray {
        val objRealCreationReq = ObjRealCreatorListReq(language,request,taluka_id,subarea,userID)
        return Gson().toJson(objRealCreationReq).toByteArray()
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
        val creatorListResponse = parseResponse(response)
        when(creatorListResponse.realCreatorResponse.responseStatusHeader.statusCode){
            ErrorCode.success ->
                iGetRealCreatorListResListener.getRealCreatorListSuccessResponse(creatorListResponse)
            ErrorCode.error_0001->
                iGetRealCreatorListResListener.getRealCreatorListFailureResponse(creatorListResponse)
            else->
                iGetRealCreatorListResListener.getRealCreatorListFailureResponse(creatorListResponse)
        }
    }

    override fun onFailureResponse(response: String) {
        iGetRealCreatorListResListener.getRealCreatorListFailureResponse(parseResponse( response))
    }

    override fun onUserNotConnected() {
        iGetRealCreatorListResListener.networkFailure()
    }

    private fun parseResponse(response: String): ObjRealCreatorListRes {
        return Gson().fromJson(
            response,
            ObjRealCreatorListRes::class.java
        )
    }

    override fun callGetRealCreatorList(
        context: Context,
        profileId: String,
        language: String,
        taluka: String,
        subarea: String,
        userID:String,
        urlEndPoint: String,
        iGetRealCreatorListResListener: IGetRealCreatorListResListener
    ) {
        this.iGetRealCreatorListResListener=iGetRealCreatorListResListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(profileId,language,taluka,subarea,userID))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

}
