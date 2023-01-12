package com.vastu.slidercore.callback.response

import com.vastu.slidercore.model.response.mainpage.MainPageSliderResponse
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IGetMainPageSliderResponseListener : NetworkFailedListener {
    fun onMainPagerSliderSuccess(mainPageSliderResponse: MainPageSliderResponse)
    fun onMainPageSliderFailure(mainPageSliderResponse: MainPageSliderResponse)
}