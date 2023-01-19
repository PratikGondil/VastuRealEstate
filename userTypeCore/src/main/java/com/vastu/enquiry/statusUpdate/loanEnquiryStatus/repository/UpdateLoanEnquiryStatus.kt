package com.vastu.enquiry.statusUpdate.loanEnquiryStatus.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.enquiry.loan.model.response.assignEnquiry.ObjAssignEnquiryReponse
import com.vastu.enquiry.property.model.request.assignEnquiry.ObjAssignPropertyEnquiryReq
import com.vastu.enquiry.statusUpdate.loanEnquiryStatus.callbacks.IUpdateLoanEnqStatusReq
import com.vastu.enquiry.statusUpdate.loanEnquiryStatus.callbacks.IUpdateLoanEnqStatusResposne
import com.vastu.enquiry.statusUpdate.loanEnquiryStatus.model.request.ObjLoanStatusUpdateReq
import com.vastu.enquiry.statusUpdate.loanEnquiryStatus.model.response.ObjLoanEnqStatusResponseMain
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.utils.ErrorCode

object UpdateLoanEnquiryStatus: IUpdateLoanEnqStatusReq ,IOnServiceResponseListener{
    lateinit var iUpdateLoanEnqStatusResposne: IUpdateLoanEnqStatusResposne
    override fun updateLoanEnqStatus(context:Context,
        objLoanStatusUpdateReq: ObjLoanStatusUpdateReq,
        urlEndPoint: String, iUpdateLoanEnqStatusResposne: IUpdateLoanEnqStatusResposne
    ) {
        this.iUpdateLoanEnqStatusResposne = iUpdateLoanEnqStatusResposne
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setIsRequestPut(false)
            .setRequest(buildRequest(objLoanStatusUpdateReq))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

    private fun buildRequest(objLoanStatusUpdateReq: ObjLoanStatusUpdateReq): ByteArray {
        return Gson().toJson(objLoanStatusUpdateReq).toByteArray()
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
       var loanEnqStatusResponse = parseResponse(response)
        if (loanEnqStatusResponse.statusLoanEmpAssignResponse!!.responseStatusHeader!!.statusCode.equals(ErrorCode.success)){
            iUpdateLoanEnqStatusResposne.onUpdateLoanEnqStatusSuccess(loanEnqStatusResponse)
        }
        else{
            iUpdateLoanEnqStatusResposne.onUpdateLoanEnqStatusFailure(loanEnqStatusResponse)
        }
    }

    override fun onFailureResponse(response: String) {
        var loanEnqStatusResponse = parseResponse(response)
        iUpdateLoanEnqStatusResposne.onUpdateLoanEnqStatusFailure(loanEnqStatusResponse)
    }

    override fun onUserNotConnected() {

    }
    private fun parseResponse(response: String): ObjLoanEnqStatusResponseMain {
        return Gson().fromJson(
            response,
            ObjLoanEnqStatusResponseMain::class.java
        )
    }
}