package com.vastu.loanenquirycore.callbacks.request

import android.content.Context
import com.vastu.loanenquirycore.callbacks.response.IGetBanksResponseListener

interface IGetBankRequest {
    fun callGetBanks(context: Context, urlEndPoint:String, iGetBanksResponseListener: IGetBanksResponseListener)
}