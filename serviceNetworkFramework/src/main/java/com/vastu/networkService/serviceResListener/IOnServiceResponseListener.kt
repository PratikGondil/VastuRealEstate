package com.vastu.networkService.serviceResListener

interface IOnServiceResponseListener {
    fun onSuccessResponse(response: String,isError:Boolean)
    fun onFailureResponse(response :String)
    fun onUserNotConnected()
}