package com.vastu.realestate.appModule.enquirylist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vastu.enquiry.property.callbacks.response.IGetPropertyEnquiryListResponseListener
import com.vastu.enquiry.property.model.response.GetPropertyEnquiryListMainResponse
import com.vastu.enquiry.property.repository.GetPropertyEnquiryRepository
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IPropertyListListener
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_PROPERTY_ENQUIRY_LIST

class PropertyEnquiryViewModel(application: Application) : AndroidViewModel(application),IGetPropertyEnquiryListResponseListener {

    lateinit var iPropertyListListener: IPropertyListListener

    fun callGetPropertyEnquiry(){
        GetPropertyEnquiryRepository.callGetPropertyEnquiryList(GET_PROPERTY_ENQUIRY_LIST,this)
    }

    override fun getPropertyEnquiryListSuccess(getPropertyEnquiryListMainResponse: GetPropertyEnquiryListMainResponse) {
        iPropertyListListener.onSuccessGetPropertyEnquiry(getPropertyEnquiryListMainResponse)
    }

    override fun getPropertyEnquiryListFailure(getPropertyEnquiryListMainResponse: GetPropertyEnquiryListMainResponse) {
       iPropertyListListener.onFailureGetPropertyEnquiry(getPropertyEnquiryListMainResponse)
    }

    override fun networkFailure() {
        iPropertyListListener.onUserNotConnected()
    }
}