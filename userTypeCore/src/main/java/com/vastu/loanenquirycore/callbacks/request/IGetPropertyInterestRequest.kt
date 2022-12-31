package com.vastu.loanenquirycore.callbacks.request

import com.vastu.loanenquirycore.callbacks.response.IGetPropertyInterestResponseListener

interface IGetPropertyInterestRequest {
    fun callGetPropertyInterest(urlEndPoint:String,iGetPropertyInterestResponseListener: IGetPropertyInterestResponseListener)
}