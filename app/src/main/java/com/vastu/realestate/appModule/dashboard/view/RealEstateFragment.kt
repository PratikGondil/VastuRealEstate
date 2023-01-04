package com.vastu.realestate.appModule.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.adapter.RealEstateAdapter
import com.vastu.realestate.appModule.dashboard.model.RealEstateList
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IRealEstateListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.dashboard.viewmodel.RealEstateViewModel
import com.vastu.realestate.databinding.FragmentRealEstateBinding
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestate.utils.PreferenceKEYS
import com.vastu.realestate.utils.PreferenceManger
import com.vastu.realestatecore.model.response.ObjGetPropertyListResMain
import com.vastu.realestatecore.model.response.PropertyData
import com.vastu.slidercore.model.response.advertisement.GetAdvertiseDetailsResponse


class RealEstateFragment : BaseFragment(), IRealEstateListener, IToolbarListener, RealEstateAdapter.OnItemClickListener, OnRefreshListener {
    private lateinit var realEstateBinding: FragmentRealEstateBinding
    private lateinit var realEstateViewModel: RealEstateViewModel
    private lateinit var drawerViewModel: DrawerViewModel
    private lateinit var getAdvertisementSlider: GetAdvertiseDetailsResponse
    private val imageList = ArrayList<SlideModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        realEstateViewModel = ViewModelProvider(this)[RealEstateViewModel::class.java]
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        realEstateBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_real_estate, container, false)
        realEstateBinding.lifecycleOwner = this
        realEstateBinding.realEstateViewModel = realEstateViewModel
        realEstateBinding.drawerViewModel= drawerViewModel
        realEstateViewModel.iRealEstateListener = this
        drawerViewModel.iToolbarListener = this
        setSliderData()
        realEstateBinding.swipeContainer.setOnRefreshListener(this)
        realEstateBinding.swipeContainer.setColorSchemeResources(R.color.button_color)
        return realEstateBinding.root
    }

    private fun setSliderData(){
        imageList.clear()
        getAdvertisementSlider = PreferenceManger.getAdvertisementSlider(PreferenceKEYS.DASHBOARD_SLIDER_LIST)
        realEstateBinding.apply {
            for( slider in getAdvertisementSlider.advertiseData){
                imageList.add(SlideModel(slider.adSlider))
            }
            imageSlider.setImageList(imageList, ScaleTypes.FIT)
            imageSlider.startSliding(3000)
        }
        getRealEstateList()
    }

    override fun onResume() {
        super.onResume()
        drawerViewModel.toolbarTitle.set(getString(R.string.real_estate))
        drawerViewModel.isDashBoard.set(false)
    }

    private fun getRealEstateList(){
        realEstateBinding.loadingLayout.startShimmerAnimation()
        DashboardActivity.userId?.let { realEstateViewModel.getPropertyList(it) }
    }
    override fun onSuccessGetRealEstateList(objGetPropertyListResMain: ObjGetPropertyListResMain) {
        val realEstates = objGetPropertyListResMain.getPropertyDetailsResponse.propertyData
        realEstateBinding.apply {
            if(realEstates.isNotEmpty()) {
                searchFilterLayout.visibility = View.VISIBLE
                rvRealEstste.visibility = View.VISIBLE
                stopShimmerAnimation()
                getRealEstateDetails(realEstates)
            }else {
                searchFilterLayout.visibility = View.GONE
                rvRealEstste.visibility = View.GONE
                stopShimmerAnimation()
            }
        }
    }

    private fun getRealEstateDetails(realEstate:List<PropertyData>) {
        val recyclerViewRealEstate = realEstateBinding.rvRealEstste
        val realEstates = RealEstateList.getRealEstateData(requireContext())
        val realEstateAdapter = RealEstateAdapter(this,realEstate)
        recyclerViewRealEstate.adapter = realEstateAdapter
        recyclerViewRealEstate.layoutManager = LinearLayoutManager(activity)
    }
    private fun stopShimmerAnimation(){
        realEstateBinding.apply {
            loadingLayout.stopShimmerAnimation()
            loadingLayout.visibility = View.GONE
        }
    }

    override fun onFailureGetRealEstateList(objGetPropertyListResMain: ObjGetPropertyListResMain) {
        stopShimmerAnimation()
        showDialog(objGetPropertyListResMain.propertyResponse.responseStatusHeader.statusDescription,false,false)
    }

    override fun onUserNotConnected() {
        stopShimmerAnimation()
        showDialog("",false,true)
    }


    override fun onItemClick(propertyData: PropertyData) {
        val bundle = Bundle()
        bundle.putSerializable(BaseConstant.PROPERTY_DETAILS, propertyData)
        findNavController().navigate(R.id.action_RealEstateFragment_to_RealEstateDetailsFragment,bundle)
    }
    override fun onClickBack() {
        activity?.onBackPressed()
    }
    override fun onClickMenu() {
        //onClickMenu
    }
    override fun onClickNotification() {
        //onClickNotifications
    }

    override fun onRefresh() {
        realEstateBinding.swipeContainer.isRefreshing = false
        getRealEstateList()
    }
}