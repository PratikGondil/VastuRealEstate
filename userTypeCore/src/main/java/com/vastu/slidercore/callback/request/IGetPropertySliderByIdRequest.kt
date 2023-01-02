package com.vastu.slidercore.callback.request

import android.content.Context
import com.vastu.slidercore.callback.response.IGetPropertySliderByIdResponse

interface IGetPropertySliderByIdRequest {
    fun callGetPropertySliderById(context: Context, propertyId:String, urlEndPoint:String, iGetPropertySliderByIdResponse: IGetPropertySliderByIdResponse)
}