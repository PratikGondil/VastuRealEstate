package com.vastu.loanenquirycore.callbacks.response

import com.vastu.loanenquirycore.model.response.occupation.OccupationMainResponse
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IGetOccupationResponseListener :NetworkFailedListener{
    fun getOccupationSuccessResponse(occupationMainResponse: OccupationMainResponse)
    fun getOccupationFailureResponse(occupationMainResponse: OccupationMainResponse)
}