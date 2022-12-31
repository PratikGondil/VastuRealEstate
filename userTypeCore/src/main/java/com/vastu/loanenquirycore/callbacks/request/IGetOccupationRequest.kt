package com.vastu.loanenquirycore.callbacks.request

import com.vastu.loanenquirycore.callbacks.response.IGetOccupationResponseListener

interface IGetOccupationRequest {
    fun callGetOccupation(urlEndPoint:String,iGetOccupationResListener: IGetOccupationResponseListener)
}