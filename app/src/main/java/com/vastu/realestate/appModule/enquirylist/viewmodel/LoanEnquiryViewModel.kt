package com.vastu.realestate.appModule.enquirylist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vastu.enquiry.getAssignedEnquiry.request.ObjGetAssignedEnquiryReq
import com.vastu.enquiry.loan.callback.getAssignEnquiry.response.IGetAssignedLoanLeadRes
import com.vastu.enquiry.loan.callback.response.IGetLoanEnquiryListResponseListener
import com.vastu.enquiry.loan.model.response.GetLoanEnquiryListMainResponse
import com.vastu.enquiry.loan.model.response.getAssignedLoanEnquiry.ObjEmpEnquiryDetailsResMain
import com.vastu.enquiry.loan.repository.AssignedLoanEnquiryRepository
import com.vastu.enquiry.loan.repository.GetLoanEnquiryRepository
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.ILoanListListener
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_ASSINGED_LOAD_ENQUIRES
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_LOAN_ENQUIRY_LIST

class LoanEnquiryViewModel(application: Application) : AndroidViewModel(application),IGetLoanEnquiryListResponseListener,
    IGetAssignedLoanLeadRes {

    lateinit var iLoanListListener: ILoanListListener

    var mContext :Application
    init {
        mContext = application
    }
    fun callGetLoanEnquiry(){
        GetLoanEnquiryRepository.callGetLoanEnquiryList(mContext,GET_LOAN_ENQUIRY_LIST,this)
    }

    fun callGetAssigndLoanEnq(objGetAssignedEnquiryReq: ObjGetAssignedEnquiryReq){
        AssignedLoanEnquiryRepository.getAssignLoanLead(mContext,GET_ASSINGED_LOAD_ENQUIRES,objGetAssignedEnquiryReq,this)

    }
    override fun getLoanEnquiryListSuccess(iGetLoanEnquiryListMainResponse: GetLoanEnquiryListMainResponse) {
     iLoanListListener.onSuccessGetLoanEnquiry(iGetLoanEnquiryListMainResponse)
    }

    override fun getLoanEnquiryListFailure(iGetLoanEnquiryListMainResponse: GetLoanEnquiryListMainResponse) {
        iLoanListListener.onFailureGetLoanEnquiry(iGetLoanEnquiryListMainResponse)
    }

    override fun networkFailure() {
        iLoanListListener.onUserNotConnected()
    }

    override fun onSuccessAssignedLoanLead(objEmpEnquiryDetailsResMain: ObjEmpEnquiryDetailsResMain) {
        iLoanListListener.onGetAssignedLoanLeadSuccess(objEmpEnquiryDetailsResMain)
    }

    override fun onFailureAssignedLoanLead(objEmpEnquiryDetailsResMain: ObjEmpEnquiryDetailsResMain) {
    }

}