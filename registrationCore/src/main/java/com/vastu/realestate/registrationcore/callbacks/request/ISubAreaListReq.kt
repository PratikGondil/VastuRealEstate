package com.vastu.realestate.registrationcore.callbacks.request

import android.content.Context
import com.vastu.realestate.registrationcore.callbacks.response.ISubAreaResponseListener
import com.vastu.realestate.registrationcore.model.request.ObjSubAreaReq
import retrofit2.http.Url

interface ISubAreaListReq {
    fun callSubAreaListApi(context: Context,objSubAreaReq: ObjSubAreaReq, urlEndPoint:String, iSubAreaResponseListener: ISubAreaResponseListener)
}