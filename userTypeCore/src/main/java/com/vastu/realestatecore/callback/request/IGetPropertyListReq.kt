package com.vastu.realestatecore.callback.request

import android.content.Context
import com.vastu.realestatecore.callback.response.IGetPropertyListResListener

interface IGetPropertyListReq {
    fun callGetPropertyList(context: Context, userId:String, urlEndPoint:String, iGetPropertyListResListener: IGetPropertyListResListener)
}
