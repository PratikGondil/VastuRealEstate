package com.vastu.realestate.appModule.rateUs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vastu.realestate.utils.ApiUrlEndPoints.RATE_US

class RateUsViewModel(application: Application) : AndroidViewModel(application), IRateUsResponseListener {
    lateinit var mContext: Application
    lateinit var iRateUsViewListener: IRateUsViewListener

    fun callRateUsApi(userId: String,rateUs: String,feedback: String){
        RateUsRepository.callRateUsApi(mContext,userId,rateUs,feedback,RATE_US,this)
    }

    override fun onGetSuccessResponse(response: RateUsDataResponseMain) {
       iRateUsViewListener.launchDashboardScreen(response)
    }

    override fun onGetFailureResponse(response: RateUsDataResponseMain) {
        iRateUsViewListener.onRateUsFail(response.objRateUsResponse)
    }

    override fun networkFailure() {
     //     iRateUsViewListener.onUserNotConnected()
    }

    fun submitClick(){
        iRateUsViewListener.onSubmitBtnClick()
    }
}