package com.vastu.enquiry.loan.model.response


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetloanDetailsResponse(
    @SerializedName("loanData")
    val loanData: List<LoanData>
): Serializable