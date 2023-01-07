package com.vastu.realestate.registrationcore.callbacks.response

import com.vastu.realestate.registrationcore.model.response.subArea.ObjGetCityAreaDetailResponseMain
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface ISubAreaResponseListener : NetworkFailedListener {
    fun onGetSubAreaResponseSuccess(response: ObjGetCityAreaDetailResponseMain)
    fun onGetSubAreaResponseFailure(responseMain: ObjGetCityAreaDetailResponseMain)
}