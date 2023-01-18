package com.vastu.enquiry.loan.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.enquiry.loan.callback.assignEnquiry.request.IAssignLoanLeadReq
import com.vastu.enquiry.loan.callback.assignEnquiry.response.IAssignLeadResponse
import com.vastu.enquiry.loan.model.request.assignEnquiry.ObjAssignLoanEnquiryReq
import com.vastu.enquiry.loan.model.response.assignEnquiry.ObjAssignEnquiryReponse
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.utils.ErrorCode

object AssignLoanEnquiryRepository: IAssignLoanLeadReq, IOnServiceResponseListener {
    lateinit var iAssignLoanLeadResponse:IAssignLeadResponse
    override fun assignLoanLead(
        context: Context,
        urlEndPoint: String,
        objAssignLoanEnquiryReq: ObjAssignLoanEnquiryReq,
        iAssignLoanLeadResponse: IAssignLeadResponse
    ) {
        this.iAssignLoanLeadResponse = iAssignLoanLeadResponse
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setIsRequestPut(false)
            .setRequest(buildRequest(objAssignLoanEnquiryReq))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

    private fun buildRequest(objAssignLoanEnquiryReq: ObjAssignLoanEnquiryReq): ByteArray {
        return Gson().toJson(objAssignLoanEnquiryReq).toByteArray()
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
       var objAssignEnquiryReponse = parseResponse(response)
        if (objAssignEnquiryReponse.objPropertyEmpAssignResponse!!.objResponseStatusHeader!!.statusCode.equals(ErrorCode.success)){
            iAssignLoanLeadResponse.onSuccessAssignLoanLead(objAssignEnquiryReponse)
        }
        else{
            iAssignLoanLeadResponse.onFailureAssignLoanLead(objAssignEnquiryReponse)
        }
    }

    override fun onFailureResponse(response: String) {
        var objAssignEnquiryReponse = parseResponse(response)
        iAssignLoanLeadResponse.onFailureAssignLoanLead(objAssignEnquiryReponse)
    }

    override fun onUserNotConnected() {

    }
    private fun parseResponse(response: String): ObjAssignEnquiryReponse {
        return Gson().fromJson(
            response,
            ObjAssignEnquiryReponse::class.java
        )
    }

}