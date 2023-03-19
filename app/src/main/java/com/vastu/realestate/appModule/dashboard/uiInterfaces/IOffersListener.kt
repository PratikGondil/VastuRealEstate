package com.vastu.realestate.appModule.dashboard.uiInterfaces

import com.vastu.offers.model.response.OffersMainResponse
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.INetworkFailListener

interface IOffersListener: INetworkFailListener {
    fun onSuccessGetOffers(offersMainResponse: OffersMainResponse)
    fun onFailureGetOffers(offersMainResponse: OffersMainResponse)
}