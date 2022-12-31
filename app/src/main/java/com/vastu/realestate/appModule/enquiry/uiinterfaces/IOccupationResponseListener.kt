package com.vastu.realestate.appModule.enquiry.uiinterfaces

import com.vastu.loanenquirycore.model.response.occupation.OccupationMainResponse

interface IOccupationResponseListener {
    fun onOccupationSuccessResponse(occupationMainResponse: OccupationMainResponse)
    fun onOccupationFailureResponse(occupationMainResponse: OccupationMainResponse)
}