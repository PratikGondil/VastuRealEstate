package com.vastu.enquiry.statusUpdate.proEnquiryStatus.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.enquiry.statusUpdate.proEnquiryStatus.callbacks.IUpdatePropEnqStatusReq
import com.vastu.enquiry.statusUpdate.proEnquiryStatus.callbacks.IUpdatePropEnqStatusResponse
import com.vastu.enquiry.statusUpdate.proEnquiryStatus.model.request.ObjPropStatusUpdateReq
import com.vastu.enquiry.statusUpdate.proEnquiryStatus.model.response.ObjPropEnqStatusResponseMain
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.utils.ErrorCode

object UpdatePropertyEnquiryStatus:IUpdatePropEnqStatusReq,IOnServiceResponseListener {
    lateinit var iUpdatePropEnqStatusResponse: IUpdatePropEnqStatusResponse
    override fun updatePropEnqStatus(
        context: Context,
        objPropStatusUpdateReq: ObjPropStatusUpdateReq,
        urlEndPoint: String,
        iUpdatePropEnqStatusResponse: IUpdatePropEnqStatusResponse
    ){
        this.iUpdatePropEnqStatusResponse = iUpdatePropEnqStatusResponse
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setIsRequestPut(false)
            .setRequest(buildRequest(objPropStatusUpdateReq))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

    private fun buildRequest(objPropStatusUpdateReq: ObjPropStatusUpdateReq): ByteArray {
        return Gson().toJson(objPropStatusUpdateReq).toByteArray()
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
        var propEnqStatusResponse = parseResponse(response)
        if (propEnqStatusResponse.statusEmpAssignResponse!!.responseStatusHeader!!.equals(
                ErrorCode.success)){
            iUpdatePropEnqStatusResponse.onUpdatePropEnqStatusSuccess(propEnqStatusResponse)
        }
        else{
            iUpdatePropEnqStatusResponse.onUpdatePropEnqStatusFailure(propEnqStatusResponse)
        }
    }

    override fun onFailureResponse(response: String) {
        var propEnqStatusResponse = parseResponse(response)
        iUpdatePropEnqStatusResponse.onUpdatePropEnqStatusFailure(propEnqStatusResponse)
    }

    override fun onUserNotConnected() {

    }
    private fun parseResponse(response: String): ObjPropEnqStatusResponseMain {
        return Gson().fromJson(
            response,
            ObjPropEnqStatusResponseMain::class.java
        )
    }


}