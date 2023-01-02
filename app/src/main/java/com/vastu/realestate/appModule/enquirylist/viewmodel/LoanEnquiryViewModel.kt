package com.vastu.realestate.appModule.enquirylist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vastu.enquiry.loan.callback.response.IGetLoanEnquiryListResponseListener
import com.vastu.enquiry.loan.model.response.GetLoanEnquiryListMainResponse
import com.vastu.enquiry.loan.repository.GetLoanEnquiryRepository
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.ILoanListListener
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_LOAN_ENQUIRY_LIST

class LoanEnquiryViewModel(application: Application) : AndroidViewModel(application),IGetLoanEnquiryListResponseListener {

    lateinit var iLoanListListener: ILoanListListener

    var mContext :Application
    init {
        mContext = application
    }
    fun callGetLoanEnquiry(){
        GetLoanEnquiryRepository.callGetLoanEnquiryList(mContext,GET_LOAN_ENQUIRY_LIST,this)
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

}