package com.vastu.loanenquirycore.callbacks.response

import com.vastu.loanenquirycore.model.response.interest.property.PropertyInterestMainResponse
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IGetPropertyInterestResponseListener:NetworkFailedListener {
    fun getPropertyInterestSuccessResponse(propertyInterestMainResponse: PropertyInterestMainResponse)
    fun getPropertyInterestFailureResponse(propertyInterestMainResponse: PropertyInterestMainResponse)
}