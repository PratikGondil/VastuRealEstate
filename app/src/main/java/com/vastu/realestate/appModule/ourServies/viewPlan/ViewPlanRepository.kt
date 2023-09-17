package com.vastu.realestate.appModule.ourServies.viewPlan

import android.content.Context
import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.realestate.commoncore.utils.ErrorCode

object ViewPlanRepository : IViewPlanRequest, IOnServiceResponseListener {
    private lateinit var iViewPlanResponseListener: IViewPlanResponseListener

    override fun callPlansApi(
        context: Context,
        planTypeId: String,
        urlEndPoint: String,
        iViewPlanResponseListener: IViewPlanResponseListener
    ) {
       this.iViewPlanResponseListener = iViewPlanResponseListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(false)
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

    fun buildRequest(planTypeId: String): ByteArray {
        var objPlansReq = GetViewPlanRequest(planTypeId)
        return Gson().toJson(objPlansReq).toByteArray()
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
        var plansRes = parseResponse(response)
        when (plansRes.objPlansResponse.objResponseStatusHdr.statusCode) {
            ErrorCode.success ->
                iViewPlanResponseListener.onGetSuccessResponse(plansRes)
            ErrorCode.error_0002 ->
                iViewPlanResponseListener.onGetFailureResponse(plansRes)
            else ->
                iViewPlanResponseListener.onGetFailureResponse(plansRes)
        }
    }

    override fun onFailureResponse(response: String) {
        iViewPlanResponseListener.onGetFailureResponse(parseResponse(response))
    }

    override fun onUserNotConnected() {
        iViewPlanResponseListener.networkFailure()
    }

    private fun parseResponse(response: String): ViewPlanDataResponseMain {
        return Gson().fromJson(
            response,
            ViewPlanDataResponseMain::class.java
        )
    }
}