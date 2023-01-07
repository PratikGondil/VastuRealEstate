package com.vastu.loanenquirycore.callbacks.response

import com.vastu.loanenquirycore.model.response.ownership.OwnershipMainResponse
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IGetOwnershipResponseListener :NetworkFailedListener{
    fun getOwnershipSuccessResponse(ownershipMainResponse: OwnershipMainResponse)
    fun getOwnershipFailureResponse(ownershipMainResponse: OwnershipMainResponse)
}