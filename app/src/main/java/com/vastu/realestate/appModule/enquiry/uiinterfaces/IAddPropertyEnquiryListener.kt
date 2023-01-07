package com.vastu.realestate.appModule.enquiry.uiinterfaces

import com.vastu.loanenquirycore.model.response.enquiry.EnquiryMainResponse
import com.vastu.loanenquirycore.model.response.interest.property.PropertyInterestMainResponse
import com.vastu.loanenquirycore.model.response.occupation.OccupationMainResponse
import com.vastu.loanenquirycore.model.response.ownership.OwnershipMainResponse
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.INetworkFailListener

interface IAddPropertyEnquiryListener :INetworkFailListener{
    fun onOccupationListFailure(occupationMainResponse: OccupationMainResponse)
    fun onPropertyInterestedInListFailure(propertyInterestMainResponse: PropertyInterestMainResponse)
    fun onOwnershipListFailure(ownershipMainResponse: OwnershipMainResponse)
    fun onClickAddPropertyEnquiry()
    fun onAddPropertyEnquiryFailure(enquiryMainResponse: EnquiryMainResponse)
    fun onGotoDashboard(enquiryMainResponse: EnquiryMainResponse)
}