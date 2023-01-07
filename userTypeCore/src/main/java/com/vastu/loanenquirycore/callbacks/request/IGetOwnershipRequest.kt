package com.vastu.loanenquirycore.callbacks.request

import android.content.Context
import com.vastu.loanenquirycore.callbacks.response.IGetOwnershipResponseListener

interface IGetOwnershipRequest {
    fun callGetOwnership(context: Context, urlEndPoint:String, iGetOwnershipResListener: IGetOwnershipResponseListener)
}