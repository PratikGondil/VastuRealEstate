package com.vastu.propertycore.callback.request

import android.content.Context
import com.vastu.propertycore.callback.request.response.IGetPropertyDetailsResponseListener

interface IGetPropertyDetailsRequest {
    fun callGetPropertyDetails(context: Context, userId:String, propertyId:String, urlEndPoint:String, iGetPropertyDetailsResponseListener: IGetPropertyDetailsResponseListener)
}
