package com.vastu.enquiry.loan.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.enquiry.loan.callback.request.IGetLoanEnquiryRequest
import com.vastu.enquiry.loan.callback.response.IGetLoanEnquiryListResponseListener
import com.vastu.enquiry.loan.model.response.GetLoanEnquiryListMainResponse
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.utils.ErrorCode

object GetLoanEnquiryRepository :IGetLoanEnquiryRequest,IOnServiceResponseListener{

    private lateinit var iGetLoanEnquiryListResponseListener: IGetLoanEnquiryListResponseListener

    override fun callGetLoanEnquiryList(
        context: Context,
        urlEndPoint: String,
        iGetLoanEnquiryListResponseListener: IGetLoanEnquiryListResponseListener
    ) {
        this.iGetLoanEnquiryListResponseListener = iGetLoanEnquiryListResponseListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(false)
            .setIsRequestPut(false)
            .setRequest(HashMap<String, String>())
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
       val loanEnquiryRes = parseGetLoanEnquiryResponse(response)

        when(loanEnquiryRes.loanDataResponse.responseStatusHeader.statusCode){
            ErrorCode.success->
                iGetLoanEnquiryListResponseListener.getLoanEnquiryListSuccess(loanEnquiryRes)
            ErrorCode.error_0001->
                iGetLoanEnquiryListResponseListener.getLoanEnquiryListFailure(loanEnquiryRes)
            else->
                iGetLoanEnquiryListResponseListener.getLoanEnquiryListFailure(loanEnquiryRes)
        }
    }

    override fun onFailureResponse(response: String) {
      iGetLoanEnquiryListResponseListener.getLoanEnquiryListFailure(parseGetLoanEnquiryResponse(response))
    }

    override fun onUserNotConnected() {
        iGetLoanEnquiryListResponseListener.networkFailure()
    }

    private fun parseGetLoanEnquiryResponse(response: String): GetLoanEnquiryListMainResponse {
        return Gson().fromJson(
            response,
            GetLoanEnquiryListMainResponse::class.java
        )
    }
}