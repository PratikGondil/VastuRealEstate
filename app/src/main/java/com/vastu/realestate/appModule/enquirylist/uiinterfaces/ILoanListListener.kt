package com.vastu.realestate.appModule.enquirylist.uiinterfaces

import com.vastu.enquiry.loan.model.response.GetLoanEnquiryListMainResponse

interface ILoanListListener :INetworkFailListener{
    fun onSuccessGetLoanEnquiry(getLoanEnquiryListMainResponse: GetLoanEnquiryListMainResponse)
    fun onFailureGetLoanEnquiry(getLoanEnquiryListMainResponse: GetLoanEnquiryListMainResponse)
}