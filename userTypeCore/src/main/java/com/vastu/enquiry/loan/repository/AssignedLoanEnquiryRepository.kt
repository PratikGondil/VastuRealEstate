package com.vastu.enquiry.loan.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.enquiry.getAssignedEnquiry.request.ObjGetAssignedEnquiryReq
import com.vastu.enquiry.loan.callback.getAssignEnquiry.request.IGetAssignedLoanEnqReq
import com.vastu.enquiry.loan.callback.getAssignEnquiry.response.IGetAssignedLoanLeadRes
import com.vastu.enquiry.loan.model.response.getAssignedLoanEnquiry.ObjEmpEnquiryDetailsResMain
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.utils.ErrorCode

object AssignedLoanEnquiryRepository: IGetAssignedLoanEnqReq,IOnServiceResponseListener {
    lateinit var iAssignLoanLeadResponse: IGetAssignedLoanLeadRes
    override fun getAssignLoanLead(
        context: Context,
        urlEndPoint: String,
        objGetAssignedEnquiryReq: ObjGetAssignedEnquiryReq,
        iAssignedLoanLeadResponse: IGetAssignedLoanLeadRes
    ) {
        this.iAssignLoanLeadResponse = iAssignedLoanLeadResponse
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setIsRequestPut(false)
            .setRequest(buildRequest(objGetAssignedEnquiryReq))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

    private fun buildRequest(objGetAssignedEnquiryReq: ObjGetAssignedEnquiryReq): ByteArray {
        return Gson().toJson(objGetAssignedEnquiryReq).toByteArray()
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
        var objGetAssignedEnquiryRes = parseResponse(response)
        if (objGetAssignedEnquiryRes.objEmpEnquiryDetailsResponse?.objResponseStatusHeader?.statusCode.equals(
                ErrorCode.success)){
            iAssignLoanLeadResponse.onSuccessAssignedLoanLead(objGetAssignedEnquiryRes)
        }
        else{
            iAssignLoanLeadResponse.onFailureAssignedLoanLead(objGetAssignedEnquiryRes)
        }
    }

    override fun onFailureResponse(response: String) {
        var objAssignEnquiryReponse = parseResponse(response)
        iAssignLoanLeadResponse.onFailureAssignedLoanLead(objAssignEnquiryReponse)
    }

    override fun onUserNotConnected() {

    }
    private fun parseResponse(response: String): ObjEmpEnquiryDetailsResMain {
        return Gson().fromJson(
            response,
            ObjEmpEnquiryDetailsResMain::class.java
        )
    }


}