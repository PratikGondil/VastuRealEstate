package com.vastu.realestate.appModule.enquiry.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vastu.loanenquirycore.model.request.AddPropertyEnquiryRequest
import com.vastu.loanenquirycore.model.response.enquiry.EnquiryMainResponse
import com.vastu.loanenquirycore.model.response.interest.property.InterestedInData
import com.vastu.loanenquirycore.model.response.interest.property.PropertyInterestMainResponse
import com.vastu.loanenquirycore.model.response.occupation.OccupationData
import com.vastu.loanenquirycore.model.response.occupation.OccupationMainResponse
import com.vastu.loanenquirycore.model.response.ownership.OwnershipData
import com.vastu.loanenquirycore.model.response.ownership.OwnershipMainResponse
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.enquiry.uiinterfaces.IAddPropertyEnquiryListener
import com.vastu.realestate.appModule.enquiry.viewModel.AddPropertyEnquiryViewModel
import com.vastu.realestate.databinding.FragmentAddPropertyEnquiryBinding
import com.vastu.realestate.utils.BaseConstant

class AddPropertyEnquiryFragment : BaseFragment(),IAddPropertyEnquiryListener,IToolbarListener,View.OnTouchListener {

    private lateinit var propertyViewBinding:FragmentAddPropertyEnquiryBinding
    private lateinit var addPropertyEnquiryViewModel: AddPropertyEnquiryViewModel
    private var addPropertyEnquiryRequest = AddPropertyEnquiryRequest()
    private lateinit var drawerViewModel: DrawerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        addPropertyEnquiryViewModel = ViewModelProvider(this)[AddPropertyEnquiryViewModel::class.java]
        propertyViewBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_property_enquiry,container,false)
        propertyViewBinding.lifecycleOwner = this
        propertyViewBinding.addPropertyViewModel = addPropertyEnquiryViewModel
        addPropertyEnquiryViewModel.iAddPropertyEnquiryListener = this
        drawerViewModel.iToolbarListener = this
        propertyViewBinding.drawerViewModel= drawerViewModel
        initView()
        callOccupationList()
        observeOccupationList()
        return propertyViewBinding.root
    }
    private fun initView(){
        propertyViewBinding.autoCompleteOccupationList.setOnTouchListener(this)
        propertyViewBinding.autoCompletePropertyList.setOnTouchListener(this)
        propertyViewBinding.autoCompleteOwnershipList.setOnTouchListener(this)

    }
    override fun onResume() {
        super.onResume()
        drawerViewModel.toolbarTitle.set(getString(R.string.property_enquiry))
        drawerViewModel.isDashBoard.set(false)
    }
    private fun callOccupationList(){
        addPropertyEnquiryViewModel.callGetOccupation()
    }
    private fun observeOccupationList(){
        addPropertyEnquiryViewModel.occupationList.observe(viewLifecycleOwner){occList->
            val adapter: ArrayList<OccupationData> = occList
            propertyViewBinding.autoCompleteOccupationList.setAdapter(
                    ArrayAdapter(
                        requireContext(),
                        R.layout.drop_down_item, adapter))
            if(occList.isNotEmpty()){
                callPropertyInterestedIn()
                observePropertyList()
            }
        }
    }
    private fun callPropertyInterestedIn(){
        addPropertyEnquiryViewModel.callGetPropertyInterestedIn()
    }

    private fun observePropertyList(){
        addPropertyEnquiryViewModel.propertyInterestedIn.observe(viewLifecycleOwner){propertyList->
            val adapter:ArrayList<InterestedInData> = propertyList
            propertyViewBinding.autoCompletePropertyList.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.drop_down_item, adapter))
                if(propertyList.isNotEmpty()){
                    callGetOwnership()
                    observeOwnershipList()
                }
            }
        }

    private fun callGetOwnership(){
        addPropertyEnquiryViewModel.callGetOwnerShip()
    }

    private fun observeOwnershipList(){
        addPropertyEnquiryViewModel.ownershipList.observe(viewLifecycleOwner){ownershipList->
            val adapter:ArrayList<OwnershipData> = ownershipList
            propertyViewBinding.autoCompleteOwnershipList.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.drop_down_item, adapter))
        }
    }

    override fun onOccupationListFailure(occupationMainResponse: OccupationMainResponse) {
        showDialog(occupationMainResponse.occupationResponse.responseStatusHeader.statusDescription,false,false)
    }

    override fun onPropertyInterestedInListFailure(propertyInterestMainResponse: PropertyInterestMainResponse) {
        showDialog(propertyInterestMainResponse.interestedInResponse.responseStatusHeader.statusDescription,false,false)
    }

    override fun onOwnershipListFailure(ownershipMainResponse: OwnershipMainResponse) {
        showDialog(ownershipMainResponse.ownershipResponse.responseStatusHeader.statusDescription,false,false)
    }

    override fun onAddPropertyEnquiryFailure(enquiryMainResponse: EnquiryMainResponse) {
        hideProgressDialog()
        val bundle = Bundle()
        bundle.putSerializable(BaseConstant.ENQUIRY_RESPONSE, enquiryMainResponse)
        bundle.putBoolean(BaseConstant.STATUS,false)
        clearAllFields()
        showDialog(enquiryMainResponse.registerResponse.responseStatusHeader.statusDescription,false,false)
        //findNavController().navigate(R.id.action_AddPropertyEnquiryFragment_to_RealEstateFragment,bundle)
    }

    override fun onGotoDashboard(enquiryMainResponse: EnquiryMainResponse) {
      hideProgressDialog()
      val bundle = Bundle()
      bundle.putSerializable(BaseConstant.ENQUIRY_RESPONSE, enquiryMainResponse)
      bundle.putBoolean(BaseConstant.STATUS,true)
      clearAllFields()
      showDialog(enquiryMainResponse.registerResponse.responseStatusHeader.statusDescription,true,false)
      //findNavController().navigate(R.id.action_AddPropertyEnquiryFragment_to_RealEstateFragment,bundle)
    }

    override fun onUserNotConnected() {
        hideProgressDialog()
        showDialog("",false,true)
    }

    override fun onClickAddPropertyEnquiry() {
        showProgressDialog()
        addPropertyEnquiryViewModel.callAddPropertyEnquiry(getPropertyEnquiryInfo())
    }
    private fun getPropertyEnquiryInfo():AddPropertyEnquiryRequest{
        addPropertyEnquiryRequest = addPropertyEnquiryRequest.copy(
            firstName = addPropertyEnquiryViewModel.firstName.get(),
            middleName =addPropertyEnquiryViewModel.middleName.get(),
            lastName = addPropertyEnquiryViewModel.lastName.get(),
            mobile = addPropertyEnquiryViewModel.mobileNumber.get(),
            address = addPropertyEnquiryViewModel.address.get(),
            occupation = addPropertyEnquiryViewModel.occupationName.value!!.occupationName,
            interestedIn = addPropertyEnquiryViewModel.propertyName.value!!.propertyName,
            ownership = addPropertyEnquiryViewModel.ownershipName.value!!.ownershipName,
            area = addPropertyEnquiryViewModel.area.get(),
            budget = addPropertyEnquiryViewModel.budget.get())
        return addPropertyEnquiryRequest
    }

    override fun onClickBack() {

        activity?.onBackPressed()
    }

    override fun onClickMenu() {
        //onMenuClick
    }

    override fun onClickNotification() {
        //onNotificationClick
    }

    override fun onTouch(view: View?, p1: MotionEvent?): Boolean {
        when (view) {
            propertyViewBinding.autoCompleteOccupationList -> {
                onShowStateDropDown(view)
            }
            propertyViewBinding.autoCompletePropertyList -> {
                onShowStateDropDown(view)
            }
            propertyViewBinding.autoCompleteOwnershipList -> {
                onShowStateDropDown(view)
            }
         }
            return true
        }
    private fun onShowStateDropDown(view: View){
        (view as AutoCompleteTextView).showDropDown()
    }
    private fun clearAllFields(){
        propertyViewBinding.apply {
            edtFirstName.text?.clear()
            edtMiddleName.text?.clear()
            edtLastName.text?.clear()
            edtMobileNum.text?.clear()
            edtAddress.text?.clear()
            edtArea.text?.clear()
            edtBudget.text?.clear()
        }
    }
}