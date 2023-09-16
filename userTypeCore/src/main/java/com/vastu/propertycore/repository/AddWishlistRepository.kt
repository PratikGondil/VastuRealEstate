package com.vastu.propertycore.repository


import android.content.Context
import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.propertycore.callback.request.IGetPropertyDetailsRequest
import com.vastu.propertycore.callback.request.response.IGetPropertyDetailsResponseListener
import com.vastu.propertycore.callback.request.response.IGetWishlistResponseListener
import com.vastu.propertycore.model.request.GetPropertyRequest
import com.vastu.propertycore.model.response.AddWishlistResponse
import com.vastu.propertycore.model.response.PropertyDataResponseMain
import com.vastu.utils.ErrorCode

object AddWishlistRepository :IOnServiceResponseListener{
   lateinit var iGetWishlistResponseListener: IGetWishlistResponseListener
     fun callAddWishlistApi(
        context: Context,
        userId: String,
        propertyId: String,
        urlEndPoint: String,
        iGetWishlistResponseListener: IGetWishlistResponseListener
    ) {
        this.iGetWishlistResponseListener = iGetWishlistResponseListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(userId,propertyId))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }
    private fun buildRequest(userId: String,propertyId: String): ByteArray {
        val objPropertyReq = GetPropertyRequest(userId,propertyId)
        return Gson().toJson(objPropertyReq).toByteArray()
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
        val wishlistRes = parseResponse(response)
        when(wishlistRes.registerResponse.responseStatusHeader.statusCode){
            ErrorCode.success->
                iGetWishlistResponseListener.getWishlistSuccessResponse(wishlistRes)

            ErrorCode.error_0001->
                iGetWishlistResponseListener.getWishlistFailureResponse(wishlistRes)
            else->
                iGetWishlistResponseListener.getWishlistFailureResponse(wishlistRes)
        }
    }

    override fun onFailureResponse(response: String) {
        iGetWishlistResponseListener.getWishlistFailureResponse(parseResponse(response))
    }

    override fun onUserNotConnected() {
        iGetWishlistResponseListener.networkFailure()
    }

    private fun parseResponse(response: String): AddWishlistResponse {
        return Gson().fromJson(
            response,
            AddWishlistResponse::class.java
        )
    }
}