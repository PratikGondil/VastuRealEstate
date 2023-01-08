package com.vastu.realestatecore.callback.request

import android.content.Context
import com.vastu.realestatecore.callback.response.IFilterPropertyListResListener
import com.vastu.realestatecore.callback.response.IGetPropertyListResListener
import com.vastu.realestatecore.model.request.ObjFilterData

interface IGetFilteredPropertyListReq {
    fun getFilterPropertyList(context: Context, objFilterData: ObjFilterData, urlEndPoint:String, iFilterPropertyListResListener: IFilterPropertyListResListener)

}