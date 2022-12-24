package com.vastu.realestate.appModule.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.gson.internal.LinkedTreeMap
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.adapter.RealEstateAdapter
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IRealEstateListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.dashboard.viewmodel.RealEstateViewModel
import com.vastu.realestate.databinding.FragmentRealEstateBinding
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestate.utils.PreferenceKEYS.DASHBOARD_SLIDER_LIST
import com.vastu.realestate.utils.PreferenceManger
import com.vastu.realestatecore.model.response.ObjGetPropertyListResMain
import com.vastu.realestatecore.model.response.PropertyData
import com.vastu.slidercore.model.response.GetPropertySliderImagesResponse
import com.vastu.slidercore.model.response.PropertySliderImage


class RealEstateFragment : BaseFragment(), IRealEstateListener, IToolbarListener, RealEstateAdapter.OnItemClickListener {
    private lateinit var realEstateBinding: FragmentRealEstateBinding
    private lateinit var realEstateViewModel: RealEstateViewModel
    private lateinit var drawerViewModel: DrawerViewModel
    private lateinit var getPropertySliderImagesResponse: GetPropertySliderImagesResponse
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
        return realEstateBinding.root
    }
    override fun onResume() {
        super.onResume()
        drawerViewModel.toolbarTitle.set(getString(R.string.real_estate))
        drawerViewModel.isDashBoard.set(false)
        getRealEstateList()
    }
    private fun getRealEstateList(){
        setSliderData()
        showProgressDialog()
        DashboardActivity.userId?.let { realEstateViewModel.getPropertyList(it) }
    }
    private fun setSliderData(){
        getPropertySliderImagesResponse = PreferenceManger.getSlider(DASHBOARD_SLIDER_LIST)
        realEstateBinding.apply {
            for( slider in getPropertySliderImagesResponse.propertySliderImages){
                imageList.add(SlideModel(slider.image))
            }
            imageSlider.setImageList(imageList, ScaleTypes.FIT)
            imageSlider.startSliding(3000)
        }
    }

    private fun getRealEstateDetails(realEstate:List<PropertyData>) {
        val recyclerViewRealEstate = realEstateBinding.rvRealEstste
        //val realEstates = RealEstateList.getRealEstateData(requireContext())
        val realEstateAdapter = RealEstateAdapter(this,realEstate)
        recyclerViewRealEstate.adapter = realEstateAdapter
        recyclerViewRealEstate.layoutManager = LinearLayoutManager(activity)
        recyclerViewRealEstate.setHasFixedSize(true)
    }

    override fun onSuccessGetRealEstateList(objGetPropertyListResMain: ObjGetPropertyListResMain) {
        hideProgressDialog()
        val realEstates = objGetPropertyListResMain.getPropertyDetailsResponse.propertyData
        getRealEstateDetails(realEstates)
    }

    override fun onFailureGetRealEstateList(objGetPropertyListResMain: ObjGetPropertyListResMain) {
        showProgressDialog()
        Toast.makeText(requireContext(),objGetPropertyListResMain.propertyResponse.responseStatusHeader.statusDescription,
            Toast.LENGTH_LONG).show()
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
    }
    override fun onClickNotification() {
        //openNotificationFragment
    }
}