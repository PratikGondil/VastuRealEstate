package com.vastu.realCreator.rate_us.callback

import android.content.Context
import com.vastu.realCreator.realCreatorSearch.callback.IGetCreatorListResListener

interface IGetRateUsReq {
    fun callGetRateUs(context: Context, userId:String, rateUs:String,realCreatorId:String, urlEndPoint:String, iGetRatUsCreatorResListener:IGetRatUsCreatorResListener)
}