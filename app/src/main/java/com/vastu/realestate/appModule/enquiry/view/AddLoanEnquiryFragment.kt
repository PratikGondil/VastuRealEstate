package com.vastu.realestate.appModule.enquiry.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.vastu.loanenquirycore.model.request.AddLoanEnquiryRequest
import com.vastu.loanenquirycore.model.response.bank.BankData
import com.vastu.loanenquirycore.model.response.bank.BankResponseMain
import com.vastu.loanenquirycore.model.response.enquiry.EnquiryMainResponse
import com.vastu.loanenquirycore.model.response.interest.loan.LoanInterestMainResponse
import com.vastu.loanenquirycore.model.response.interest.loan.LoanInterstedData
import com.vastu.loanenquirycore.model.response.occupation.OccupationData
import com.vastu.loanenquirycore.model.response.occupation.OccupationMainResponse
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.view.DashboardFragment
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.enquiry.uiinterfaces.IAddLoanEnquiryListener
import com.vastu.realestate.appModule.enquiry.viewModel.AddLoanEnquiryViewModel
import com.vastu.realestate.databinding.FragmentAddLoanEnquiryBinding
import com.vastu.realestate.utils.BaseConstant.LOAN_DATA


class AddLoanEnquiryFragment : BaseFragment(),IAddLoanEnquiryListener,IToolbarListener,View.OnTouchListener {
    private lateinit var loanEnquiryViewModel: AddLoanEnquiryViewModel
    private lateinit var loanViewBinding: FragmentAddLoanEnquiryBinding
    private var addLoanEnquiryRequest = AddLoanEnquiryRequest()
    private lateinit var drawerViewModel: DrawerViewModel
    private lateinit var loanData: LoanInterstedData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        loanEnquiryViewModel = ViewModelProvider(this)[AddLoanEnquiryViewModel::class.java]
        loanViewBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_loan_enquiry, container, false)
        loanViewBinding.lifecycleOwner = this
        loanViewBinding.addLoanViewModel = loanEnquiryViewModel
        loanEnquiryViewModel.iAddLoanEnquiryListener = this
        drawerViewModel.iToolbarListener = this
        loanViewBinding.drawerViewModel= drawerViewModel
        getBundleData()
        initView()
        callOccupationList()
        observeOccupationList()
        return loanViewBinding.root
    }
    private fun getBundleData(){
        val args = this.arguments
        if (args != null){
            if (args.getSerializable(LOAN_DATA) != null) {
                loanData =  args.getSerializable(LOAN_DATA) as LoanInterstedData
            }
        }
    }
    private fun initView(){
        loanViewBinding.autoCompleteOccupationList.setOnTouchListener(this)
        loanViewBinding.autoCompleteLoanInterestedIn.setOnTouchListener(this)
        loanViewBinding.autoCompletePreferredBank.setOnTouchListener(this)
        loanViewBinding.loanInterestedFor.text = loanData.loanName.toString()

    }
    override fun onResume() {
        super.onResume()
        drawerViewModel.toolbarTitle.set(getString(R.string.loan_enquiry))
        drawerViewModel.isDashBoard.set(false)
    }
    private fun callOccupationList(){
        loanEnquiryViewModel.callGetOccupation()
    }
    private fun observeOccupationList(){
        loanEnquiryViewModel.occupationList.observe(viewLifecycleOwner){ occList->
            val adapter: ArrayList<OccupationData> = occList
           loanViewBinding.autoCompleteOccupationList.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.drop_down_item, adapter)
            )
            if(occList.isNotEmpty()){
                callLoanInterestedIn()
                observeLoanInterestedList()
            }
        }
    }

    private fun callLoanInterestedIn(){
        loanEnquiryViewModel.callLoanInterestedIn()
    }
    private fun observeLoanInterestedList(){
        loanEnquiryViewModel.loanInterestedInList.observe(viewLifecycleOwner){loanList->
            val adapter: ArrayList<LoanInterstedData> = loanList
            loanViewBinding.autoCompleteLoanInterestedIn.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.drop_down_item, adapter)
            )
            if(loanList.isNotEmpty()){
                callGetBank()
                observeBankList()
            }
        }
    }

    private fun callGetBank(){
        loanEnquiryViewModel.callGetBanks()
    }
    private fun observeBankList(){
        loanEnquiryViewModel.preferredBankList.observe(viewLifecycleOwner){bankList->
            val adapter: ArrayList<BankData> = bankList
            loanViewBinding.autoCompletePreferredBank.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.drop_down_item, adapter)
            )
        }
    }

    override fun onClickAddLoanEnquiry() {
        createDialog(this)

    }
    fun redirectedAfterTermsLoan()
    {
        showProgressDialog()
        loanEnquiryViewModel.callAddLoanEnquiry(getLoanEnquiryInfo())
    }
    private fun getLoanEnquiryInfo():AddLoanEnquiryRequest{
        addLoanEnquiryRequest = addLoanEnquiryRequest.copy(
            firstName = loanEnquiryViewModel.firstName.get(),
            middleName = loanEnquiryViewModel.middleName.get(),
            lastName = loanEnquiryViewModel.lastName.get(),
            mobile = loanEnquiryViewModel.mobileNumber.get(),
            address = loanEnquiryViewModel.address.get(),
            occupation = loanEnquiryViewModel.occupationName.value!!.occupationName,
            interestedIn = loanViewBinding.loanInterestedFor.text.toString(),//loanEnquiryViewModel.loanName.value!!.loanName,
            preferredBank = loanEnquiryViewModel.bankName.value!!.bankName,
            loanAmount = loanEnquiryViewModel.loanAmount.get(),
            loanTermYear = loanEnquiryViewModel.loanTermYear.get(),
            userId = DashboardFragment.userId
        )
        return addLoanEnquiryRequest
    }

    override fun onAddLoanEnquiryFailure(enquiryMainResponse: EnquiryMainResponse) {
        hideProgressDialog()
        clearAllFields()
        showDialog(enquiryMainResponse.registerResponse.responseStatusHeader.statusDescription!!,false,false)
        Handler(Looper.getMainLooper()).postDelayed({  onClickBack() }, 1000)
    }

    override fun onGotoDashboard(enquiryMainResponse: EnquiryMainResponse) {
        hideProgressDialog()
        clearAllFields()
        showDialog(enquiryMainResponse.registerResponse.responseStatusHeader.statusDescription!!,true,false)
        Handler(Looper.getMainLooper()).postDelayed({  onClickBack() }, 1000)

    }

    override fun onUserNotConnected() {
        hideProgressDialog()
        showDialog("",false,true)
    }

    override fun onOccupationListFailure(occupationMainResponse: OccupationMainResponse) {
        showDialog(occupationMainResponse.occupationResponse.responseStatusHeader.statusDescription!!,false,false)
    }

    override fun onLoanInterestedInLListFailure(loanInterestMainResponse: LoanInterestMainResponse) {
        showDialog(loanInterestMainResponse.loanInterstedResponse.responseStatusHeader.statusDescription!!,false,false)
    }

    override fun onBankListFailure(bankResponseMain: BankResponseMain) {
        showDialog(bankResponseMain.bankResponse.responseStatusHeader.statusDescription!!,false,false)
    }

    override fun onClickBack() {
     activity?.onBackPressed()
        hideDialog()
    }

    override fun onClickMenu() {
        //onMenuClick
    }

    override fun onClickNotification() {
        //onNotificationClick
    }
    override fun onTouch(view: View?, p1: MotionEvent?): Boolean {
        when (view) {
            loanViewBinding.autoCompleteOccupationList -> {
                onShowStateDropDown(view)
            }
            loanViewBinding.autoCompleteLoanInterestedIn -> {
                onShowStateDropDown(view)
            }
            loanViewBinding.autoCompletePreferredBank -> {
                onShowStateDropDown(view)
            }
        }
        return true
    }
    private fun onShowStateDropDown(view: View){
        (view as AutoCompleteTextView).showDropDown()
    }
    private fun clearAllFields(){
        loanViewBinding.apply {
            edtFirstName.text?.clear()
            edtMiddleName.text?.clear()
            edtLastName.text?.clear()
            edtMobileNum.text?.clear()
            edtAddress.text?.clear()
            edtLoanAmount.text?.clear()
            edtLoanTermYear.text?.clear()
        }
    }
}