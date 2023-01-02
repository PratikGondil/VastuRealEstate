package com.vastu.loanenquirycore.callbacks.request

import android.content.Context
import com.vastu.loanenquirycore.callbacks.response.IGetPropertyInterestResponseListener

interface IGetPropertyInterestRequest {
    fun callGetPropertyInterest(context: Context, urlEndPoint:String, iGetPropertyInterestResponseListener: IGetPropertyInterestResponseListener)
}