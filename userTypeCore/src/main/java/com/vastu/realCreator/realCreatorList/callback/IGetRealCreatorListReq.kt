package com.vastu.realCreator.realCreatorList.callback

import android.content.Context
import com.vastu.realCreator.realCreatorSearch.callback.IGetCreatorListResListener

interface IGetRealCreatorListReq {
    fun callGetRealCreatorList(context: Context, profileId:String, language:String,taluka:String,subArea:String, urlEndPoint:String, iGetRealCreatorListResListener: IGetRealCreatorListResListener)

}