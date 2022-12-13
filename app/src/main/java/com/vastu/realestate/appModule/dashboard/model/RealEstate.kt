package com.vastu.realestate.appModule.dashboard.model


import java.io.Serializable

data class RealEstate( var propertyId : String,
                       val image: Int,
                       var propertyFeature : String,
                       var propertyName : String,
                       var propertyPrice : String,
                       var propertyDetails: String,
                       var propertyAddress :String
): Serializable
