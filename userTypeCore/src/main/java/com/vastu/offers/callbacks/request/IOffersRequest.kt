package com.vastu.offers.callbacks.request

import android.content.Context
import com.vastu.offers.callbacks.response.IGetOffersResponseListener

interface IOffersRequest {
    fun callGetOffers(context: Context, urlEndPoint:String,iGetOffersResponseListener: IGetOffersResponseListener)
}