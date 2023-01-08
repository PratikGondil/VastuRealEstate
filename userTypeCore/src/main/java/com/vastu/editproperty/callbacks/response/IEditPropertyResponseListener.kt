package com.vastu.editproperty.callbacks.response

import com.vastu.editproperty.model.response.EditPropertyMainResponse
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IEditPropertyResponseListener:NetworkFailedListener {
    fun onSuccessEditProperty(editPropertyMainResponse: EditPropertyMainResponse)
    fun onFailureEditProperty(editPropertyMainResponse: EditPropertyMainResponse)
}