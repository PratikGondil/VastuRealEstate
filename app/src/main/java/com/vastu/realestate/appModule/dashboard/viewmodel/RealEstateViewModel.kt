package com.vastu.realestate.appModule.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vastu.realestatecore.callback.response.IGetPropertyListResListener
import com.vastu.realestatecore.model.response.ObjGetPropertyListResMain
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IRealEstateListener
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_PROPERTY_LIST
import com.vastu.realestatecore.repository.PropertyListRepository

class RealEstateViewModel(application: Application) : AndroidViewModel(application),
    IGetPropertyListResListener {

    lateinit var iRealEstateListener: IRealEstateListener

    var mContext :Application
    init {
        mContext = application
    }

    fun getPropertyList(userId:String){
        PropertyListRepository.callGetPropertyList(mContext,userId,GET_PROPERTY_LIST,this)
    }

    override fun getPropertyListSuccessResponse(objGetPropertyListResMain: ObjGetPropertyListResMain) {
        iRealEstateListener.onSuccessGetRealEstateList(objGetPropertyListResMain)
    }

    override fun getPropertyListFailureResponse(objGetPropertyListResMain: ObjGetPropertyListResMain) {
       iRealEstateListener.onFailureGetRealEstateList(objGetPropertyListResMain)
    }

    override fun networkFailure() {
      iRealEstateListener.onUserNotConnected()
    }

}