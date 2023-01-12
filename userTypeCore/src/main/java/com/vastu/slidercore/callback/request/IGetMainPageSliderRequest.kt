package com.vastu.slidercore.callback.request

import android.content.Context
import com.vastu.slidercore.callback.response.IGetMainPageSliderResponseListener
import com.vastu.slidercore.model.request.MainPagerSliderRequest

interface IGetMainPageSliderRequest {
    fun callMainPagerSlider(context: Context, mainPagerSliderRequest: MainPagerSliderRequest, urlEndPoint:String,iGetMainPageSliderResponseListener: IGetMainPageSliderResponseListener)
}