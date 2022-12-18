package com.vastu.realestate.registrationcore.callbacks.response

import com.vastu.realestate.registrationcore.model.response.subArea.ObjGetCityAreaDetailResponseMain

interface ISubAreaResponseListener {
    fun onGetSubAreaResponseSuccess(response: ObjGetCityAreaDetailResponseMain)
    fun onGetSubAreaResponseFailure(responseMain: ObjGetCityAreaDetailResponseMain)
}