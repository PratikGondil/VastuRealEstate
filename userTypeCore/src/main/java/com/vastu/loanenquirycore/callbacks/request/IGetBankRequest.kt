package com.vastu.loanenquirycore.callbacks.request

import com.vastu.loanenquirycore.callbacks.response.IGetBanksResponseListener

interface IGetBankRequest {
    fun callGetBanks(urlEndPoint:String,iGetBanksResponseListener: IGetBanksResponseListener)
}