package com.vastu.realestate.appModule.properties.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.adapter.RealEstateAdapter
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IRealEstateListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.view.DashboardFragment.Companion.userId
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.properties.viewmodel.PropertiesViewModel
import com.vastu.realestate.databinding.FragmentPropertiesBinding
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestatecore.model.response.*


class PropertiesFragment : BaseFragment(),IRealEstateListener, RealEstateAdapter.OnItemClickListener,
    IToolbarListener {
    private lateinit var propertiesViewModel: PropertiesViewModel
    private lateinit var propertiesDataBinding:FragmentPropertiesBinding
    private lateinit var drawerViewModel: DrawerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        propertiesViewModel = ViewModelProvider(this)[PropertiesViewModel::class.java]
        propertiesDataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_properties, container, false)
        propertiesDataBinding.lifecycleOwner = this
        propertiesViewModel.iRealEstateListener = this
        drawerViewModel.iToolbarListener = this
        propertiesDataBinding.drawerViewModel= drawerViewModel
        getProperties()
        return propertiesDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        drawerViewModel.toolbarTitle.set(getString(R.string.properties))
        drawerViewModel.isDashBoard.set(false)
    }

    private fun getProperties(){
        try {
            propertiesDataBinding.loadingLayout.startShimmerAnimation()
            userId?.let { propertiesViewModel.getPropertyList(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun onSuccessGetRealEstateList(objGetPropertyListResMain: ObjGetPropertyListResMain) {
        try {
            val realEstates = objGetPropertyListResMain.getPropertyDetailsResponse.propertyData
            val adSlider=objGetPropertyListResMain.getPropertyDetailsResponse.adSlider
            propertiesDataBinding.apply {
                if(realEstates.isNotEmpty()) {
                    rvPropertyList.visibility = View.VISIBLE
                    stopShimmerAnimation()
                    getRealEstateDetails(realEstates,adSlider)
                }else {
                    rvPropertyList.visibility = View.GONE
                    stopShimmerAnimation()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getRealEstateDetails(realEstate:List<PropertyData>,adSlider:List<AdSlider>) {
        try {
            val recyclerViewRealEstate = propertiesDataBinding.rvPropertyList
            val realEstateAdapter = RealEstateAdapter(this,realEstate,adSlider)
            recyclerViewRealEstate.adapter = realEstateAdapter
            recyclerViewRealEstate.layoutManager = LinearLayoutManager(activity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun stopShimmerAnimation(){
        try {
            propertiesDataBinding.apply {
                loadingLayout.stopShimmerAnimation()
                loadingLayout.visibility = View.GONE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onFailureGetRealEstateList(objGetPropertyListResMain: ObjGetPropertyListResMain) {
        stopShimmerAnimation()
        showDialog(objGetPropertyListResMain.propertyResponse.responseStatusHeader.statusDescription!!,false,false)
    }

    override fun onFilterPropertyListSuccess(objGetFilterDataResponse: ObjGetFilterDataResponse) {
      hideProgressDialog()
    }

    override fun onFilterPropertyListFailure(objFilterDataResponseMain: ObjFilterDataResponseMain) {
       hideProgressDialog()
        showDialog(objFilterDataResponseMain.objfilterDataResponse.responseStatusHeader.statusDescription!!,isSuccess = false,isNetworkFailure = false)
    }

    override fun searchFilter(searchTxt: String) {

    }

    override fun onUserNotConnected() {
       hideProgressDialog()
       showDialog("", isSuccess = false, isNetworkFailure = true)
    }

    override fun onItemClick(propertyData: PropertyData) {
        val bundle = Bundle()
        bundle.putSerializable(BaseConstant.PROPERTY_DETAILS, propertyData)
        bundle.putBoolean(BaseConstant.IS_FROM_PROPERTY_LIST,true)
        findNavController().navigate(R.id.action_PropertiesFragment_to_AddPropertyFragment,bundle)
    }

    override fun onClickBack() {
        activity?.onBackPressed()
    }

    override fun onClickMenu() {
        //menu
    }

    override fun onClickNotification() {
        //notification
    }
}