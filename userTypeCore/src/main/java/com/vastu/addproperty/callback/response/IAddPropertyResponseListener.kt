package com.vastu.addproperty.callback.response

import com.vastu.addproperty.model.response.AddPropertyMainResponse
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IAddPropertyResponseListener: NetworkFailedListener {
    fun onAddPropertySuccess(addPropertyMainResponse: AddPropertyMainResponse)
    fun onAddPropertyFailure(addPropertyMainResponse: AddPropertyMainResponse)
}