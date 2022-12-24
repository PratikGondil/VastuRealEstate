package com.vastu.realestatecore.callback.request

import com.vastu.realestatecore.callback.response.IGetPropertyListResListener

interface IGetPropertyListReq {
    fun callGetPropertyList(userId:String,urlEndPoint:String,iGetPropertyListResListener: IGetPropertyListResListener)
}
