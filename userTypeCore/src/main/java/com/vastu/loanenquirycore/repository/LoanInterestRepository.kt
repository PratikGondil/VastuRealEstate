package com.vastu.loanenquirycore.repository

import com.google.gson.Gson
import com.vastu.loanenquirycore.callbacks.request.IGetLoanInterestRequest
import com.vastu.loanenquirycore.callbacks.response.IGetLoanInterestResponseListener
import com.vastu.loanenquirycore.model.response.interest.loan.LoanInterestMainResponse
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.realestate.commoncore.utils.ErrorCode

object LoanInterestRepository:IGetLoanInterestRequest,IOnServiceResponseListener {

    private lateinit var iGetLoanInterestResponseListener: IGetLoanInterestResponseListener

    override fun callGetLoanInterest(
        urlEndPoint: String,
        iGetLoanInterestResponseListener: IGetLoanInterestResponseListener
    ) {
       this.iGetLoanInterestResponseListener = iGetLoanInterestResponseListener
        NetworkDaoBuilder.Builder
            .setIsContentTypeJSON(true)
            .setIsRequestPost(false)
            .setIsRequestPut(false)
            .setRequest(HashMap<String, String>())
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
        val loanInterestRes = parseLoanInterestResponse(response)

        when(loanInterestRes.loanInterstedResponse.responseStatusHeader.statusCode){
            ErrorCode.success->
                iGetLoanInterestResponseListener.getLoanInterestSuccessResponse(loanInterestRes)
            ErrorCode.error_0001->
                iGetLoanInterestResponseListener.getLoanInterestFailureResponse(loanInterestRes)
            else->
                iGetLoanInterestResponseListener.getLoanInterestFailureResponse(loanInterestRes)
        }
    }

    override fun onFailureResponse(response: String) {
        iGetLoanInterestResponseListener.getLoanInterestFailureResponse(parseLoanInterestResponse(response))
    }

    override fun onUserNotConnected() {
       iGetLoanInterestResponseListener.networkFailure()
    }

    private fun parseLoanInterestResponse(response: String): LoanInterestMainResponse {
        return Gson().fromJson(
            response,
            LoanInterestMainResponse::class.java
        )
    }
}