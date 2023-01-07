package com.vastu.loanenquirycore.callbacks.request

import android.content.Context
import com.vastu.loanenquirycore.callbacks.response.IGetLoanInterestResponseListener

interface IGetLoanInterestRequest {
    fun callGetLoanInterest(context: Context, urlEndPoint:String, iGetLoanInterestResponseListener: IGetLoanInterestResponseListener)
}