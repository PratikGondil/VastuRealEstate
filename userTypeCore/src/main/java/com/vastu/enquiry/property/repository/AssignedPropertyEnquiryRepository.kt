package com.vastu.enquiry.property.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.enquiry.getAssignedEnquiry.request.ObjGetAssignedEnquiryReq
import com.vastu.enquiry.loan.callback.getAssignEnquiry.request.IGetAssignedLoanEnqReq
import com.vastu.enquiry.loan.callback.getAssignEnquiry.response.IGetAssignedLoanLeadRes
import com.vastu.enquiry.property.callbacks.getAssignedEnquiry.request.IGetAssignedPropertyEnqReq
import com.vastu.enquiry.property.callbacks.getAssignedEnquiry.response.IGetAssignedPropertyLeadRes
import com.vastu.enquiry.property.model.response.getAssignedPropertyEnquiry.ObjEmpPropertyEnquiryDtlsResMain
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.utils.ErrorCode

object AssignedPropertyEnquiryRepository : IGetAssignedPropertyEnqReq, IOnServiceResponseListener {
    lateinit var iAssignedPropertyLeadResponse: IGetAssignedPropertyLeadRes

    override fun getAssignPropertyLead(
        context: Context,
        urlEndPoint: String,
        objGetAssignedEnquiryReq: ObjGetAssignedEnquiryReq,
        iAssignedPropertyLeadResponse: IGetAssignedPropertyLeadRes
    ) {
        this.iAssignedPropertyLeadResponse = iAssignedPropertyLeadResponse
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
        try {
            var objGetAssignedPropertyEnquiryRes = parseResponse(response)
            if (objGetAssignedPropertyEnquiryRes.objEmpPropertyEnquiryDtlsRes?.ResponseStatusHeader?.statusCode.equals(
                    ErrorCode.success
                )
            ) {
                iAssignedPropertyLeadResponse.onSuccessAssignedPropertyLead(
                    objGetAssignedPropertyEnquiryRes
                )
            } else {
                iAssignedPropertyLeadResponse.onFailureAssignedPropertyLead(
                    objGetAssignedPropertyEnquiryRes
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onFailureResponse(response: String) {
        try {
            var objGetAssignedPropertyEnquiryRes = parseResponse(response)

            iAssignedPropertyLeadResponse.onFailureAssignedPropertyLead(
                objGetAssignedPropertyEnquiryRes
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onUserNotConnected() {
    }

    private fun parseResponse(response: String): ObjEmpPropertyEnquiryDtlsResMain {
        return Gson().fromJson(
            response,
            ObjEmpPropertyEnquiryDtlsResMain::class.java
        )
    }
}