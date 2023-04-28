package com.vastu.realestate.appModule.enquirylist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vastu.enquiry.getAssignedEnquiry.request.ObjGetAssignedEnquiryReq
import com.vastu.enquiry.loan.model.response.LoanData
import com.vastu.enquiry.loan.model.response.getAssignedLoanEnquiry.ObjEmpEnquiryDetailsData
import com.vastu.enquiry.property.model.response.EnquiryData
import com.vastu.enquiry.property.model.response.GetPropertyEnquiryListMainResponse
import com.vastu.enquiry.property.model.response.getAssignedPropertyEnquiry.ObjEmpPropertyEnquiryDtlsData
import com.vastu.enquiry.property.model.response.getAssignedPropertyEnquiry.ObjEmpPropertyEnquiryDtlsResMain
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.view.DashboardFragment.Companion.userType
import com.vastu.realestate.appModule.enquirylist.adapter.AssignedPropertyEnquiryAdapter
import com.vastu.realestate.appModule.enquirylist.adapter.PropertyEnquiryAdapter
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IAssignLeadListener
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IPropertyListListener
import com.vastu.realestate.appModule.enquirylist.viewmodel.PropertyEnquiryViewModel
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyDtls
import com.vastu.realestate.databinding.FragmentPropertyEnquiryListBinding
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestate.utils.PreferenceKEYS
import com.vastu.realestate.utils.PreferenceManger

class PropertyEnquiryListFragment : BaseFragment() ,IPropertyListListener, IAssignLeadListener,
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var propertyEnquiryViewModel: PropertyEnquiryViewModel
    private lateinit var propertyEnquiryViewBinding:FragmentPropertyEnquiryListBinding
    var objVerifyDetails = ObjVerifyDtls()
    var objGetAssignedEnquiryReq = ObjGetAssignedEnquiryReq()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        propertyEnquiryViewModel = ViewModelProvider(this)[PropertyEnquiryViewModel::class.java]
        propertyEnquiryViewBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_property_enquiry_list, container, false)
        propertyEnquiryViewBinding.lifecycleOwner = this
        propertyEnquiryViewBinding.propertyEnquiryViewModel = propertyEnquiryViewModel
        propertyEnquiryViewModel.iPropertyListListener = this
        if(userType.equals(BaseConstant.ADMIN))
            getPropertyEnquiry()
        else if (userType.equals(BaseConstant.EMPLOYEES))
            getAssignedLoanEnquiry()
        propertyEnquiryViewBinding.swipeContainer.setOnRefreshListener(this)
        propertyEnquiryViewBinding.swipeContainer.setColorSchemeResources(R.color.button_color)
        return propertyEnquiryViewBinding.root
    }
    private fun getPropertyEnquiry(){
        propertyEnquiryViewBinding.loadingLayout.startShimmerAnimation()
        propertyEnquiryViewModel.callGetPropertyEnquiry()
    }
    fun getAssignedLoanEnquiry(){
        propertyEnquiryViewBinding.loadingLayout.startShimmerAnimation()
        objVerifyDetails = PreferenceManger.get<ObjVerifyDtls>(PreferenceKEYS.USER)!!
        objGetAssignedEnquiryReq = objGetAssignedEnquiryReq.copy(empId = objVerifyDetails.userId)
        propertyEnquiryViewModel.callGetAssigndLoanEnq(objGetAssignedEnquiryReq)
    }
    override fun onSuccessGetPropertyEnquiry(getPropertyEnquiryListMainResponse: GetPropertyEnquiryListMainResponse) {
        stopShimmerAnimation()
        val propertyEnquiryList =
            getPropertyEnquiryListMainResponse.getEnquiryDetailsResponse.enquiryData
        if (propertyEnquiryList.isNotEmpty()) {
            stopShimmerAnimation()
            propertyEnquiryViewBinding.rvPropertyEnquiry.visibility = View.VISIBLE
            setPropertyEnquiryDetails(propertyEnquiryList)
        }else{
            stopShimmerAnimation()
            propertyEnquiryViewBinding.rvPropertyEnquiry.visibility = View.GONE
        }
    }

    private fun stopShimmerAnimation(){
        propertyEnquiryViewBinding.apply {
            loadingLayout.visibility = View.GONE
            loadingLayout.stopShimmerAnimation()
        }
    }
    private fun setPropertyEnquiryDetails(propertyEnquiryList:List<EnquiryData>){
        val recyclerviewPropertyEnquiry = propertyEnquiryViewBinding.rvPropertyEnquiry
        val propertyEnquiryAdapter = PropertyEnquiryAdapter(propertyEnquiryList,this,userType)
        recyclerviewPropertyEnquiry.layoutManager = LinearLayoutManager(activity)
        recyclerviewPropertyEnquiry.setHasFixedSize(true)
        recyclerviewPropertyEnquiry.adapter = propertyEnquiryAdapter
    }

    override fun onFailureGetPropertyEnquiry(getPropertyEnquiryListMainResponse: GetPropertyEnquiryListMainResponse) {
       stopShimmerAnimation()
       showDialog(getPropertyEnquiryListMainResponse.enquiryDataResponse.responseStatusHeader.statusDescription!!,false,false)
    }

    override fun onSuccessAssignedPropertyLead(objEmpPropertyEnquiryDtlsResMain: ObjEmpPropertyEnquiryDtlsResMain) {
        stopShimmerAnimation()
        propertyEnquiryViewBinding.rvPropertyEnquiry.visibility = View.VISIBLE

        var response = objEmpPropertyEnquiryDtlsResMain.objGetEmpPropertyEnquiryDtlsResponse?.objEmpPropertyEnquiryDtlsData
        val loanRecyclerview = propertyEnquiryViewBinding.rvPropertyEnquiry
        val loanAdapter = response?.let { AssignedPropertyEnquiryAdapter(it,this,userType) }
        loanRecyclerview.layoutManager = LinearLayoutManager(activity)
        loanRecyclerview.adapter = loanAdapter
    }

    override fun onFailureAssignedPropertyLead(objEmpPropertyEnquiryDtlsResMain: ObjEmpPropertyEnquiryDtlsResMain) {
        stopShimmerAnimation()
        showDialog(objEmpPropertyEnquiryDtlsResMain.objEmpPropertyEnquiryDtlsRes?.ResponseStatusHeader?.statusDescription.toString(),false,false)
    }

    override fun onUserNotConnected() {
        stopShimmerAnimation()
        showDialog("",false,true)
    }

    override fun onRefresh() {
        propertyEnquiryViewBinding.swipeContainer.isRefreshing = false
        if(userType.equals(BaseConstant.ADMIN))
            getPropertyEnquiry()
        else if (userType.equals(BaseConstant.EMPLOYEES))
            getAssignedLoanEnquiry()
    }

    override fun assignLoanLeadToEmployee(loanData: LoanData) {

    }

    override fun assignPropertyLeadToEmployee(PropertyData: EnquiryData) {
        try{
            val modalbottomSheetFragment = AssignLeadsFragment(this)
            modalbottomSheetFragment.setStyle(
                BottomSheetDialogFragment.STYLE_NORMAL,android.R.style.Theme_Translucent_NoTitleBar
            )
            modalbottomSheetFragment.arguments = bundleOf(BaseConstant.PROPERTY_DATA to PropertyData,BaseConstant.USER_TYPE to userType)

            modalbottomSheetFragment.show(requireActivity().supportFragmentManager,modalbottomSheetFragment.tag)
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    override fun onAssignLeadSuccess() {
        onRefresh()
    }

    override fun onEmpListFailure(message: String, isSuccess: Boolean, isNetworkFailure: Boolean) {
        showDialog(message,isSuccess,isNetworkFailure)
    }

    override fun updateLoanLeadStatus(PropertyData: ObjEmpEnquiryDetailsData) {

    }

    override fun updatePropertyLeadStatus(PropertyData: ObjEmpPropertyEnquiryDtlsData) {
        try{
            val modalbottomSheetFragment = UpdateStatusFragment(this)
            modalbottomSheetFragment.setStyle(
                BottomSheetDialogFragment.STYLE_NORMAL,android.R.style.Theme_Translucent_NoTitleBar
            )
            val bundle = Bundle()
            modalbottomSheetFragment.arguments = bundleOf(BaseConstant.ASSIGNED_PROPERTY_DATA to PropertyData,BaseConstant.USER_TYPE to userType)
            modalbottomSheetFragment.show(requireActivity().supportFragmentManager,modalbottomSheetFragment.tag)
        }catch (e : Exception){
            e.printStackTrace()
        }    }
}