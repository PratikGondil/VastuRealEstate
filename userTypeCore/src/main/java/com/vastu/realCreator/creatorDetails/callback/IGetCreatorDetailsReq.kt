package com.vastu.realCreator.creatorDetails.callback

import android.content.Context

interface IGetCreatorDetailsReq {
    fun callGetCreatorDetailsList(context: Context, profileId:String, language:String, urlEndPoint:String, iGetCreatorDetailsResListener: IGetCreatorDetailsResListener)

}