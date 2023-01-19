package com.vastu.enquiry.statusUpdate.enquiryStatus.model.response

import com.google.gson.annotations.SerializedName
import com.vastu.usertypecore.model.response.ResponseStatusHeader

data class ObjEnquiryStatusResponse (
    @SerializedName("ResponseStatusHeader" ) var responseStatusHeader : ResponseStatusHeader? = ResponseStatusHeader()

    )
