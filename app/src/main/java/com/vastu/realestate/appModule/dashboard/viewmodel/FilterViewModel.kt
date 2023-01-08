package com.vastu.realestate.appModule.dashboard.viewmodel

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IFilterViewHandler
import com.vastu.realestate.appModule.utils.BaseUtils
import com.vastu.realestate.registrationcore.callbacks.response.ISubAreaResponseListener
import com.vastu.realestate.registrationcore.model.request.ObjSubAreaReq
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaDataList
import com.vastu.realestate.registrationcore.model.response.subArea.ObjCityAreaData
import com.vastu.realestate.registrationcore.model.response.subArea.ObjGetCityAreaDetailResponseMain
import com.vastu.realestate.registrationcore.repository.SubAreaRequestRepository
import com.vastu.realestate.utils.ApiUrlEndPoints
import com.vastu.realestatecore.callback.response.IFilterPropertyListResListener
import com.vastu.realestatecore.model.filter.ObjManageFilterVisibility
import com.vastu.realestatecore.model.request.ObjFilterData
import com.vastu.realestatecore.model.response.ObjFilterDataResponseMain
import com.vastu.realestatecore.repository.FilterPropertyListRepository

class FilterViewModel(application: Application):AndroidViewModel (application),ISubAreaResponseListener{
    var mContext:Application

init {
    mContext =application
}

    lateinit var iFilterViewHandler : IFilterViewHandler
    var subAreaList = MutableLiveData<ArrayList<ObjCityAreaData>>()


    override fun onGetSubAreaResponseSuccess(response: ObjGetCityAreaDetailResponseMain) {
        subAreaList.value = response.objGetCityAreaDetailsResponse.objCityAreaData as ArrayList<ObjCityAreaData>
    }

    override fun onGetSubAreaResponseFailure(responseMain: ObjGetCityAreaDetailResponseMain) {
        iFilterViewHandler.onSubAreaListApiFailure(responseMain)
    }

    override fun networkFailure() {
//        iFilterViewHandler.onUserNotConnected()

    }

}