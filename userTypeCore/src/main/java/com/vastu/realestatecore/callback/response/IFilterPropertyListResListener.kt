package com.vastu.realestatecore.callback.response

import com.vastu.realestatecore.model.response.ObjFilterDataResponseMain
import com.vastu.realestatecore.model.response.ObjGetPropertyListResMain

interface IFilterPropertyListResListener {
    fun onSuccessFilterList(objFilterDataResponseMain: ObjFilterDataResponseMain)
    fun onFailureFilterList(objFilterDataResponseMain: ObjFilterDataResponseMain)
}