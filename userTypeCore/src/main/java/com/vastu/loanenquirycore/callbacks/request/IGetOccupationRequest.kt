package com.vastu.loanenquirycore.callbacks.request

import android.content.Context
import com.vastu.loanenquirycore.callbacks.response.IGetOccupationResponseListener

interface IGetOccupationRequest {
    fun callGetOccupation(context: Context, urlEndPoint:String, iGetOccupationResListener: IGetOccupationResponseListener)
}