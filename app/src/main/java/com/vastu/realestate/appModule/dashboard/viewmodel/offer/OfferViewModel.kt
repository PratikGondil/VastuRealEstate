package com.vastu.realestate.appModule.dashboard.viewmodel.offer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vastu.offers.callbacks.response.IGetOffersResponseListener
import com.vastu.offers.model.response.OffersMainResponse
import com.vastu.offers.repository.GetOffersRepository
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IOffersListener
import com.vastu.realestate.utils.ApiUrlEndPoints

class OfferViewModel(application: Application):AndroidViewModel(application),IGetOffersResponseListener{

    lateinit var iOffersListener:IOffersListener

    var mContext :Application
    init {
        mContext = application
    }
    fun callGetOffers(){
        GetOffersRepository.callGetOffers(mContext,ApiUrlEndPoints.OFFERS,this)
    }

    override fun getOffersSuccess(offersMainResponse: OffersMainResponse) {
        iOffersListener.onSuccessGetOffers(offersMainResponse)
    }

    override fun getOffersFailure(offersMainResponse: OffersMainResponse) {
        iOffersListener.onFailureGetOffers(offersMainResponse)
    }

    override fun networkFailure() {
       iOffersListener.onUserNotConnected()
    }
}