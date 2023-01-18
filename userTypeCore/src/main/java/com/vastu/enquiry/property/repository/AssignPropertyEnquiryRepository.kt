package com.vastu.enquiry.property.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.enquiry.loan.callback.assignEnquiry.response.IAssignLeadResponse
import com.vastu.enquiry.loan.model.request.assignEnquiry.ObjAssignLoanEnquiryReq
import com.vastu.enquiry.loan.model.response.assignEnquiry.ObjAssignEnquiryReponse
import com.vastu.enquiry.property.callbacks.request.IAssignPropertyLeadReq
import com.vastu.enquiry.property.model.request.assignEnquiry.ObjAssignPropertyEnquiryReq
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.utils.ErrorCode

object AssignPropertyEnquiryRepository: IAssignPropertyLeadReq,IOnServiceResponseListener {
    lateinit var iAssignLeadResponse: IAssignLeadResponse
    override fun assignPropertyLead(
        context: Context,
        urlEndPoint: String,
        objAssignPropertyEnquiryReq: ObjAssignPropertyEnquiryReq,
        iAssignLeadResponse: IAssignLeadResponse
    ) {
        this.iAssignLeadResponse = iAssignLeadResponse
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setIsRequestPut(false)
            .setRequest(buildRequest(objAssignPropertyEnquiryReq))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }


    private fun buildRequest(objAssignPropertyEnquiryReq: ObjAssignPropertyEnquiryReq): ByteArray {
        return Gson().toJson(objAssignPropertyEnquiryReq).toByteArray()
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
        var objAssignEnquiryReponse = parseResponse(response)
        if (objAssignEnquiryReponse.objPropertyEmpAssignResponse!!.objResponseStatusHeader!!.statusCode.equals(
                ErrorCode.success)){
            iAssignLeadResponse.onSuccessAssignLoanLead(objAssignEnquiryReponse)
        }
        else{
            iAssignLeadResponse.onFailureAssignLoanLead(objAssignEnquiryReponse)
        }
    }

    override fun onFailureResponse(response: String) {
        var objAssignEnquiryReponse = parseResponse(response)
        iAssignLeadResponse.onFailureAssignLoanLead(objAssignEnquiryReponse)
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