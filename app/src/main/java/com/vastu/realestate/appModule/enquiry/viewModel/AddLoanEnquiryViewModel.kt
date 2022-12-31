package com.vastu.realestate.appModule.enquiry.viewModel

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vastu.loanenquirycore.callbacks.response.*
import com.vastu.loanenquirycore.model.request.AddLoanEnquiryRequest
import com.vastu.loanenquirycore.model.response.bank.BankData
import com.vastu.loanenquirycore.model.response.bank.BankResponseMain
import com.vastu.loanenquirycore.model.response.enquiry.EnquiryMainResponse
import com.vastu.loanenquirycore.model.response.interest.loan.LoanInterestMainResponse
import com.vastu.loanenquirycore.model.response.interest.loan.LoanInterstedData
import com.vastu.loanenquirycore.model.response.occupation.OccupationData
import com.vastu.loanenquirycore.model.response.occupation.OccupationMainResponse
import com.vastu.loanenquirycore.repository.*
import com.vastu.realestate.R
import com.vastu.realestate.appModule.enquiry.uiinterfaces.IAddLoanEnquiryListener
import com.vastu.realestate.utils.ApiUrlEndPoints.ADD_LOAN_ENQUIRY
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_BANKS
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_OCCUPATION
import com.vastu.realestate.utils.ApiUrlEndPoints.LOAN_INTERESTED_IN

class AddLoanEnquiryViewModel(application: Application) : AndroidViewModel(application),
    IGetOccupationResponseListener, IGetLoanInterestResponseListener
    ,IGetBanksResponseListener,IAddLoanEnquiryResponseListener{

    var firstName = ObservableField("")
    var middleName = ObservableField("")
    var lastName = ObservableField("")
    var mobileNumber = ObservableField("")
    var address = ObservableField("")

    var occupationName = MutableLiveData<OccupationData>()
    var occupationList = MutableLiveData<ArrayList<OccupationData>>()

    var loanName = MutableLiveData<LoanInterstedData>()
    var loanInterestedInList = MutableLiveData<ArrayList<LoanInterstedData>>()

    var bankName = MutableLiveData<BankData>()
    var preferredBankList = MutableLiveData<ArrayList<BankData>>()

    var loanAmount = ObservableField("")
    var loanTermYear = ObservableField("")

    var isBtnEnable = ObservableField(false)
    private var mContext: Application

    init {
        mContext = application
    }

    var fName = ObservableField(mContext.getString(R.string.required))
    var mName = ObservableField(mContext.getString(R.string.required))
    var lName = ObservableField(mContext.getString(R.string.required))
    var mobile = ObservableField(mContext.getString(R.string.required))
    var add = ObservableField(mContext.getString(R.string.required))
    var occ = ObservableField(mContext.getString(R.string.required))
    var loan = ObservableField(mContext.getString(R.string.required))
    var bank = ObservableField(mContext.getString(R.string.required))
    var amount = ObservableField(mContext.getString(R.string.required))
    var year = ObservableField(mContext.getString(R.string.required))

    var btnBackground = ObservableField(ContextCompat.getDrawable(mContext, R.drawable.button_inactive_background))
    lateinit var iAddLoanEnquiryListener: IAddLoanEnquiryListener


    fun callGetOccupation() {
        OccupationRepository.callGetOccupation(GET_OCCUPATION, this)
    }

    fun callLoanInterestedIn(){
        LoanInterestRepository.callGetLoanInterest(LOAN_INTERESTED_IN,this)
    }
    fun callGetBanks(){
        BankRepository.callGetBanks(GET_BANKS,this)
    }
    fun onSubmitLoanEnquiry(){
        iAddLoanEnquiryListener.onClickAddLoanEnquiry()
    }

    fun callAddLoanEnquiry(addLoanRequest: AddLoanEnquiryRequest){
        AddLoanEnquiryRepository.callAddLoanEnquiry(ADD_LOAN_ENQUIRY,addLoanRequest,this)
    }

    override fun getOccupationSuccessResponse(occupationMainResponse: OccupationMainResponse) {
        occupationList.value = occupationMainResponse.getoccupationDetailsResponse.occupationData as ArrayList<OccupationData>
    }

    override fun getOccupationFailureResponse(occupationMainResponse: OccupationMainResponse) {
        iAddLoanEnquiryListener.onOccupationListFailure(occupationMainResponse)
    }

    override fun getLoanInterestSuccessResponse(loanInterestMainResponse: LoanInterestMainResponse) {
            loanInterestedInList.value =  loanInterestMainResponse.getloanInterstedDetailsResponse.loanInterstedData as ArrayList<LoanInterstedData>
    }

    override fun getLoanInterestFailureResponse(loanInterestMainResponse: LoanInterestMainResponse) {
       iAddLoanEnquiryListener.onLoanInterestedInLListFailure(loanInterestMainResponse)
    }

    override fun getBanksSuccessResponse(bankResponseMain: BankResponseMain) {
        preferredBankList.value = bankResponseMain.getbankDetailsResponse.bankData as ArrayList<BankData>
    }

    override fun getBanksFailureResponse(bankResponseMain: BankResponseMain) {
        iAddLoanEnquiryListener.onBankListFailure(bankResponseMain)
    }

    override fun onAddLoanEnquirySuccessResponse(enquiryMainResponse: EnquiryMainResponse) {
        iAddLoanEnquiryListener.onGotoDashboard(enquiryMainResponse)
    }

    override fun onAddLoanEnquiryFailureResponse(enquiryMainResponse: EnquiryMainResponse) {
       iAddLoanEnquiryListener.onAddLoanEnquiryFailure(enquiryMainResponse)
    }

    override fun networkFailure() {
       iAddLoanEnquiryListener.onUserNotConnected()
    }
}