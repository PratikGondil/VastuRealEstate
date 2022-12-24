package com.vastu.propertycore.callback.request

import com.vastu.propertycore.callback.request.response.IGetPropertyDetailsResponseListener

interface IGetPropertyDetailsRequest {
    fun callGetPropertyDetails(userId:String,propertyId:String,urlEndPoint:String,iGetPropertyDetailsResponseListener: IGetPropertyDetailsResponseListener)
}
