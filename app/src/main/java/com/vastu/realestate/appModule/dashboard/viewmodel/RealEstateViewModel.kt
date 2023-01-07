package com.vastu.realestate.appModule.dashboard.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IFilterClickListener
import com.vastu.realestatecore.callback.response.IGetPropertyListResListener
import com.vastu.realestatecore.model.response.ObjGetPropertyListResMain
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IRealEstateListener
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_PROPERTY_LIST
import com.vastu.realestatecore.repository.PropertyListRepository

class RealEstateViewModel(application: Application) : AndroidViewModel(application),
    IGetPropertyListResListener {
    var isFilterViewVisible = ObservableField(View.GONE)
    var isRealEstateVisible = ObservableField(View.VISIBLE)
    lateinit var iFilterClickListener: IFilterClickListener

    lateinit var iRealEstateListener: IRealEstateListener

    fun getPropertyList(userId:String){
        PropertyListRepository.callGetPropertyList(userId,GET_PROPERTY_LIST,this)
    }
    fun filterClick(){
        iFilterClickListener.setFilterView()
    }

    override fun getPropertyListSuccessResponse(objGetPropertyListResMain: ObjGetPropertyListResMain) {
        iRealEstateListener.onSuccessGetRealEstateList(objGetPropertyListResMain)
    }

    override fun getPropertyListFailureResponse(objGetPropertyListResMain: ObjGetPropertyListResMain) {
       iRealEstateListener.onFailureGetRealEstateList(objGetPropertyListResMain)
    }

}