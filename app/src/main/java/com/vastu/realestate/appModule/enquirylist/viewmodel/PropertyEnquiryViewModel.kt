package com.vastu.realestate.appModule.enquirylist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vastu.enquiry.getAssignedEnquiry.request.ObjGetAssignedEnquiryReq
import com.vastu.enquiry.property.callbacks.getAssignedEnquiry.response.IGetAssignedPropertyLeadRes
import com.vastu.enquiry.property.callbacks.response.IGetPropertyEnquiryListResponseListener
import com.vastu.enquiry.property.model.response.GetPropertyEnquiryListMainResponse
import com.vastu.enquiry.property.model.response.getAssignedPropertyEnquiry.ObjEmpPropertyEnquiryDtlsResMain
import com.vastu.enquiry.property.repository.AssignedPropertyEnquiryRepository
import com.vastu.enquiry.property.repository.GetPropertyEnquiryRepository
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IPropertyListListener
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_ASSIGNED_PROPERTY_ENQUIRES
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_PROPERTY_ENQUIRY_LIST

class PropertyEnquiryViewModel(application: Application) : AndroidViewModel(application),IGetPropertyEnquiryListResponseListener,IGetAssignedPropertyLeadRes {

    lateinit var iPropertyListListener: IPropertyListListener

    var mContext :Application
    init {
        mContext = application
    }

    fun callGetPropertyEnquiry(){
        GetPropertyEnquiryRepository.callGetPropertyEnquiryList(mContext,GET_PROPERTY_ENQUIRY_LIST,this)
    }

    fun callGetAssigndLoanEnq(objGetAssignedEnquiryReq: ObjGetAssignedEnquiryReq){
        AssignedPropertyEnquiryRepository.getAssignPropertyLead(mContext,GET_ASSIGNED_PROPERTY_ENQUIRES,objGetAssignedEnquiryReq,this)

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

    override fun onSuccessAssignedPropertyLead(objEmpPropertyEnquiryDtlsResMain: ObjEmpPropertyEnquiryDtlsResMain) {
        iPropertyListListener.onSuccessAssignedPropertyLead(objEmpPropertyEnquiryDtlsResMain)
    }

    override fun onFailureAssignedPropertyLead(objEmpPropertyEnquiryDtlsResMain: ObjEmpPropertyEnquiryDtlsResMain) {
        iPropertyListListener.onFailureAssignedPropertyLead(objEmpPropertyEnquiryDtlsResMain)
    }
}