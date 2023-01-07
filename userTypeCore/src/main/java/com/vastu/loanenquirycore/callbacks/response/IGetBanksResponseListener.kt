package com.vastu.loanenquirycore.callbacks.response

import com.vastu.loanenquirycore.model.response.bank.BankResponseMain
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IGetBanksResponseListener:NetworkFailedListener {
    fun getBanksSuccessResponse(bankResponseMain: BankResponseMain)
    fun getBanksFailureResponse(bankResponseMain: BankResponseMain)
}