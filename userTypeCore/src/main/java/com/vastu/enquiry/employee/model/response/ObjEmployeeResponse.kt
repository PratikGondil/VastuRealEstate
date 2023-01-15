package com.vastu.enquiry.employee.model.response

import com.google.gson.annotations.SerializedName
import com.vastu.usertypecore.model.response.ResponseStatusHeader

data class ObjEmployeeResponse (
    @SerializedName("ResponseStatusHeader" ) var ResponseStatusHeader : ResponseStatusHeader?

)
