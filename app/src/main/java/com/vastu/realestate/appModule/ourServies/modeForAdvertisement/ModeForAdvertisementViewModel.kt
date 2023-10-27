package com.vastu.realestate.appModule.ourServies.modeForAdvertisement

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ModeForAdvertisementViewModel (application: Application) : AndroidViewModel(application) {
    lateinit var mContext: Application
    lateinit var iModeForAdvertisementListener: IModeForAdvertisementListener

    init {
        mContext = application
    }

    fun onPlanVedioAdvertisementClick() {
        iModeForAdvertisementListener.onPlanVedioAdvertisementClick()
    }

    fun onPlanBannerAdvertisementClick() {
       iModeForAdvertisementListener.onPlanBannerAdvertisementClick()
    }
}