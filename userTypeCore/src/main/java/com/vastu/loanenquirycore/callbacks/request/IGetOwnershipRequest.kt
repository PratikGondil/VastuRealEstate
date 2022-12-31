package com.vastu.loanenquirycore.callbacks.request

import com.vastu.loanenquirycore.callbacks.response.IGetOwnershipResponseListener

interface IGetOwnershipRequest {
    fun callGetOwnership(urlEndPoint:String,iGetOwnershipResListener: IGetOwnershipResponseListener)
}