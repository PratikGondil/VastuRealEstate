package com.vastu.realestate.appModule.ourServies.viewPlan

import android.content.Context
import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.realestate.appModule.ourServies.planForOwner.response.ObjPlanByTypeResponseMain
import com.vastu.realestate.commoncore.utils.ErrorCode

object PlanTypeRepository :IPlanTypeRequest, IOnServiceResponseListener {
    private lateinit var iPlanTypeResponseListener: IPlanTypeResponseListener

    override fun callPlansTypeApi(
        context: Context,
        language: String,
        planTypeId: String,
        profileId:String,
        urlEndPoint: String,
        iPlanTypeResponseListener: IPlanTypeResponseListener
    ) {
        this.iPlanTypeResponseListener = iPlanTypeResponseListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(planTypeId,language,profileId))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

    fun buildRequest(planTypeId: String, language: String, profileId: String): ByteArray {
        var objPlansTypeReq = GetPlansTypeRequest(planTypeId,language,profileId)
        return Gson().toJson(objPlansTypeReq).toByteArray()
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
        var plansTypeRes = parseResponse(response)
        when (plansTypeRes.planResponse.ResponseStatusHeader.statusCode) {
            ErrorCode.success ->
                iPlanTypeResponseListener.onGetSuccessResponse(plansTypeRes)
            ErrorCode.error_0002 ->
                iPlanTypeResponseListener.onGetFailureResponse(plansTypeRes)
            else ->
                iPlanTypeResponseListener.onGetFailureResponse(plansTypeRes)
        }
    }

    override fun onFailureResponse(response: String) {
        iPlanTypeResponseListener.onGetFailureResponse(parseResponse(response))
    }

    override fun onUserNotConnected() {
        iPlanTypeResponseListener.networkFailure()
    }

    private fun parseResponse(response: String): ObjPlanByTypeResponseMain {
        return Gson().fromJson(
            response,
            ObjPlanByTypeResponseMain::class.java
        )
    }
}