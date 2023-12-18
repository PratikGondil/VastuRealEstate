package com.vastu.realCreator.realCreatorSearch.callback

import android.content.Context

interface IGetCreatorListReq {
    fun callGetCreatorList(context: Context, userId:String,language:String, urlEndPoint:String, iGetCreatorListResListener: IGetCreatorListResListener)

}
