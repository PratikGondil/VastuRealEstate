package com.vastu.realestate.appModule.properties.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IRealEstateListener
import com.vastu.realestate.utils.ApiUrlEndPoints
import com.vastu.realestatecore.callback.response.IGetPropertyListResListener
import com.vastu.realestatecore.model.response.ObjGetPropertyListResMain
import com.vastu.realestatecore.repository.PropertyListRepository

class PropertiesViewModel(application: Application) : AndroidViewModel(application),IGetPropertyListResListener{

    private var mContext: Application

    init {
        mContext = application
    }
    lateinit var iRealEstateListener: IRealEstateListener

    fun getPropertyList(userId:String){
        PropertyListRepository.callGetPropertyList(mContext,userId,
            ApiUrlEndPoints.GET_PROPERTY_LIST,this)
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