package com.vastu.deleteimage.callbacks.response

import com.vastu.deleteimage.model.response.DeleteImageMainResponse
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IDeleteImageResponseListener:NetworkFailedListener {
    fun onDeleteImageSuccess(deleteImageMainResponse: DeleteImageMainResponse)
    fun onDeleteImageFailure(deleteImageMainResponse: DeleteImageMainResponse)
}