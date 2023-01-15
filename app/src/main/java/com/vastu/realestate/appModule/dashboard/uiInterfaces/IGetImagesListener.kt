package com.vastu.realestate.appModule.dashboard.uiInterfaces

import com.vastu.deleteimage.model.response.DeleteImageMainResponse
import com.vastu.getimages.model.response.GetImageMainResponse
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.INetworkFailListener

interface IGetImagesListener: INetworkFailListener {
    fun onSuccessGetImages(getImageMainResponse: GetImageMainResponse)
    fun onFailureGetImages(getImageMainResponse: GetImageMainResponse)
    fun onSuccessDeleteImage(deleteImageMainResponse: DeleteImageMainResponse)
    fun onFailureDeleteImage(deleteImageMainResponse: DeleteImageMainResponse)
}