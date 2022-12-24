package com.vastu.slidercore.callback.request

import com.vastu.slidercore.callback.response.IGetPropertySliderByIdResponse

interface IGetPropertySliderByIdRequest {
    fun callGetPropertySliderById(propertyId:String,urlEndPoint:String,iGetPropertySliderByIdResponse: IGetPropertySliderByIdResponse)
}