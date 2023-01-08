package com.vastu.realestate.appModule.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vastu.loanenquirycore.callbacks.response.IGetLoanInterestResponseListener
import com.vastu.loanenquirycore.model.response.interest.loan.LoanInterestMainResponse
import com.vastu.loanenquirycore.model.response.interest.loan.LoanInterstedData
import com.vastu.loanenquirycore.repository.LoanInterestRepository
import com.vastu.realestate.appModule.dashboard.uiInterfaces.ILoanListener
import com.vastu.realestate.appModule.login.uiInterfaces.ILoginViewListener
import com.vastu.realestate.utils.ApiUrlEndPoints

class LoanViewModel(application: Application) : AndroidViewModel(application),
    IGetLoanInterestResponseListener {
    lateinit var iLoanListener: ILoanListener
    
    var loanList = MutableLiveData<ArrayList<LoanInterstedData>>()

    private var mContext: Application
    init {
        mContext = application
    }
    fun callLoanInterestedIn(){
        LoanInterestRepository.callGetLoanInterest(mContext, ApiUrlEndPoints.LOAN_INTERESTED_IN,this)
    }

    override fun getLoanInterestSuccessResponse(loanInterestMainResponse: LoanInterestMainResponse) {
        loanList.value =  loanInterestMainResponse.getloanInterstedDetailsResponse.loanInterstedData as ArrayList<LoanInterstedData>
        iLoanListener.onLoanInterestedInListSuccess(loanInterestMainResponse)
    }

    override fun getLoanInterestFailureResponse(loanInterestMainResponse: LoanInterestMainResponse) {
      iLoanListener.onLoanInterestedInListFailure(loanInterestMainResponse)
    }

    override fun networkFailure() {
        iLoanListener.onUserNotConnected()
    }

    fun onClickPersonalLoan() {
        iLoanListener.onClickPersonalLoan()
    }

    fun onClickHomeHomeLoan() {
        iLoanListener.onClickHomeLoan()
    }

    fun onClickCarLoan() {
        iLoanListener.onClickCarLoan()
    }

    fun onClickAddLoanEnquiry() {
        iLoanListener.fabAddLoanEnquiry()
    }
}
