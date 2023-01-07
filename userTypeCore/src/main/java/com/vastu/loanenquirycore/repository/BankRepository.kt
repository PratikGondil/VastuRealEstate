package com.vastu.loanenquirycore.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.loanenquirycore.callbacks.request.IGetBankRequest
import com.vastu.loanenquirycore.callbacks.response.IGetBanksResponseListener
import com.vastu.loanenquirycore.model.response.bank.BankResponseMain
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.utils.ErrorCode

object BankRepository:IGetBankRequest,IOnServiceResponseListener {
    private lateinit var iGetBanksResponseListener: IGetBanksResponseListener
    override fun callGetBanks(
        context: Context,
        urlEndPoint: String,
        iGetBanksResponseListener: IGetBanksResponseListener
    ) {
       this.iGetBanksResponseListener = iGetBanksResponseListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(false)
            .setIsRequestPut(false)
            .setRequest(HashMap<String, String>())
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
       val bankResponseMain = parseBanksResponse(response)
        when(bankResponseMain.bankResponse.responseStatusHeader.statusCode){
            ErrorCode.success->
                iGetBanksResponseListener.getBanksSuccessResponse(bankResponseMain)
            ErrorCode.error_0001->
                iGetBanksResponseListener.getBanksFailureResponse(bankResponseMain)
            else->
                iGetBanksResponseListener.getBanksFailureResponse(bankResponseMain)
        }
    }

    override fun onFailureResponse(response: String) {
       iGetBanksResponseListener.getBanksFailureResponse(parseBanksResponse(response))
    }

    override fun onUserNotConnected() {
        iGetBanksResponseListener.networkFailure()
    }

    private fun parseBanksResponse(response: String): BankResponseMain {
        return Gson().fromJson(
            response,
            BankResponseMain::class.java
        )
    }

}