package com.vastu.realestate.appModule.enquirylist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vastu.enquiry.property.model.response.EnquiryData
import com.vastu.enquiry.property.model.response.GetPropertyEnquiryListMainResponse
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.enquirylist.adapter.PropertyEnquiryAdapter
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IPropertyListListener
import com.vastu.realestate.appModule.enquirylist.viewmodel.PropertyEnquiryViewModel
import com.vastu.realestate.databinding.FragmentPropertyEnquiryListBinding

class PropertyEnquiryListFragment : BaseFragment() ,IPropertyListListener,
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var propertyEnquiryViewModel: PropertyEnquiryViewModel
    private lateinit var propertyEnquiryViewBinding:FragmentPropertyEnquiryListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        propertyEnquiryViewModel = ViewModelProvider(this)[PropertyEnquiryViewModel::class.java]
        propertyEnquiryViewBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_property_enquiry_list, container, false)
        propertyEnquiryViewBinding.lifecycleOwner = this
        propertyEnquiryViewBinding.propertyEnquiryViewModel = propertyEnquiryViewModel
        propertyEnquiryViewModel.iPropertyListListener = this
        getPropertyEnquiry()
        propertyEnquiryViewBinding.swipeContainer.setOnRefreshListener(this)
        propertyEnquiryViewBinding.swipeContainer.setColorSchemeResources(R.color.button_color)
        return propertyEnquiryViewBinding.root
    }
    private fun getPropertyEnquiry(){
        propertyEnquiryViewBinding.loadingLayout.startShimmerAnimation()
        propertyEnquiryViewModel.callGetPropertyEnquiry()
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
        val propertyEnquiryAdapter = PropertyEnquiryAdapter(propertyEnquiryList)
        recyclerviewPropertyEnquiry.layoutManager = LinearLayoutManager(activity)
        recyclerviewPropertyEnquiry.setHasFixedSize(true)
        recyclerviewPropertyEnquiry.adapter = propertyEnquiryAdapter
    }

    override fun onFailureGetPropertyEnquiry(getPropertyEnquiryListMainResponse: GetPropertyEnquiryListMainResponse) {
       stopShimmerAnimation()
       showDialog(getPropertyEnquiryListMainResponse.enquiryDataResponse.responseStatusHeader.statusDescription,false,false)
    }

    override fun onUserNotConnected() {
        stopShimmerAnimation()
        showDialog("",false,true)
    }

    override fun onRefresh() {
        propertyEnquiryViewBinding.swipeContainer.isRefreshing = false
        getPropertyEnquiry()
    }
}