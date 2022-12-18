package com.vastu.realestate.registrationcore.callbacks.request

import com.vastu.realestate.registrationcore.callbacks.response.ISubAreaResponseListener
import com.vastu.realestate.registrationcore.model.request.ObjSubAreaReq
import retrofit2.http.Url

interface ISubAreaListReq {
    fun callSubAreaListApi(objSubAreaReq: ObjSubAreaReq, urlEndPoint:String, iSubAreaResponseListener: ISubAreaResponseListener)
}