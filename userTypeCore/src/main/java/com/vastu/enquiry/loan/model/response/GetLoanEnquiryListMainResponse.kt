package com.vastu.enquiry.loan.model.response


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetLoanEnquiryListMainResponse(
    @SerializedName("GetloanDetailsResponse")
    val getloanDetailsResponse: GetloanDetailsResponse,
    @SerializedName("loanDataResponse")
    val loanDataResponse: LoanDataResponse
): Serializable