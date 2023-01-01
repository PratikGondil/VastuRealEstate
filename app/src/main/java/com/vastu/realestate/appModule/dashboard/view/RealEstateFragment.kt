package com.vastu.realestate.appModule.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.vastu.loanenquirycore.model.response.enquiry.EnquiryMainResponse
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.adapter.RealEstateAdapter
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IPropertySliderListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IRealEstateListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.dashboard.viewmodel.RealEstateDetailsViewModel
import com.vastu.realestate.appModule.dashboard.viewmodel.RealEstateViewModel
import com.vastu.realestate.databinding.FragmentRealEstateBinding
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestate.utils.BaseConstant.STATUS
import com.vastu.realestate.utils.PreferenceKEYS
import com.vastu.realestate.utils.PreferenceKEYS.DASHBOARD_SLIDER_LIST
import com.vastu.realestate.utils.PreferenceKEYS.PROPERTY_SLIDER_LIST
import com.vastu.realestate.utils.PreferenceManger
import com.vastu.realestatecore.model.response.ObjGetPropertyListResMain
import com.vastu.realestatecore.model.response.PropertyData
import com.vastu.slidercore.model.response.property.GetPropertySliderImagesResponse
import com.vastu.slidercore.model.response.property.PropertySliderResponseMain


class RealEstateFragment : BaseFragment(), IRealEstateListener, IToolbarListener, RealEstateAdapter.OnItemClickListener,
    IPropertySliderListener {
    private lateinit var realEstateBinding: FragmentRealEstateBinding
    private lateinit var realEstateViewModel: RealEstateViewModel
    private lateinit var drawerViewModel: DrawerViewModel
    private lateinit var getPropertySliderImagesResponse: GetPropertySliderImagesResponse
    private lateinit var realEstateDetailsViewModel: RealEstateDetailsViewModel
    private val imageList = ArrayList<SlideModel>()
    private lateinit var enquiryMainResponse: EnquiryMainResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        realEstateDetailsViewModel = ViewModelProvider(this)[RealEstateDetailsViewModel::class.java]
        realEstateViewModel = ViewModelProvider(this)[RealEstateViewModel::class.java]
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        realEstateBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_real_estate, container, false)
        realEstateBinding.lifecycleOwner = this
        realEstateBinding.realEstateViewModel = realEstateViewModel
        realEstateBinding.drawerViewModel= drawerViewModel
        realEstateViewModel.iRealEstateListener = this
        drawerViewModel.iToolbarListener = this
        realEstateDetailsViewModel.iPropertySliderListener = this
        getPropertySlider()
        return realEstateBinding.root
    }
    private fun getPropertySlider(){
        showProgressDialog()
        realEstateDetailsViewModel.getPropertySlider("1")
    }
    override fun onResume() {
        super.onResume()
        drawerViewModel.toolbarTitle.set(getString(R.string.real_estate))
        drawerViewModel.isDashBoard.set(false)
        getRealEstateList()
        //getPropertyData()
    }
    private fun getPropertyData() {
        val args = arguments
        if (args != null) {
            if (args.getSerializable(BaseConstant.ENQUIRY_RESPONSE) != null) {
                enquiryMainResponse = args.getSerializable(BaseConstant.ENQUIRY_RESPONSE) as EnquiryMainResponse
                val status = args.getBoolean(STATUS,false)
                showDialog(enquiryMainResponse.registerResponse.responseStatusHeader.statusDescription,status)
            }
        }
    }
    private fun getRealEstateList(){
        showProgressDialog()
        DashboardActivity.userId?.let { realEstateViewModel.getPropertyList(it) }
    }

    private fun getRealEstateDetails(realEstate:List<PropertyData>) {
        realEstateBinding.apply {
            if(realEstate.isNotEmpty())
                floatPropertyEnquiry.visibility = View.VISIBLE
            else
                floatPropertyEnquiry.visibility = View.GONE
        }
        val recyclerViewRealEstate = realEstateBinding.rvRealEstste
        //val realEstates = RealEstateList.getRealEstateData(requireContext())
        val realEstateAdapter = RealEstateAdapter(this,realEstate)
        recyclerViewRealEstate.adapter = realEstateAdapter
        recyclerViewRealEstate.layoutManager = LinearLayoutManager(activity)
    }

    override fun fabAddPropertyEnquiry() {
        findNavController().navigate(R.id.action_RealEstateFragment_to_AddPropertyEnquiryFragment)
    }

    override fun onSuccessGetRealEstateList(objGetPropertyListResMain: ObjGetPropertyListResMain) {
        hideProgressDialog()
        val realEstates = objGetPropertyListResMain.getPropertyDetailsResponse.propertyData
        getRealEstateDetails(realEstates)
    }

    override fun onFailureGetRealEstateList(objGetPropertyListResMain: ObjGetPropertyListResMain) {
        hideProgressDialog()
        showDialog(objGetPropertyListResMain.propertyResponse.responseStatusHeader.statusDescription,false)
       /* Toast.makeText(requireContext(),objGetPropertyListResMain.propertyResponse.responseStatusHeader.statusDescription,
            Toast.LENGTH_LONG).show()*/
    }

    override fun onSuccessPropertySliderById(propertySliderResponseMain: PropertySliderResponseMain) {
        hideProgressDialog()
        imageList.clear()
        getPropertySliderImagesResponse = propertySliderResponseMain.getPropertySliderImagesResponse
        for( slider in getPropertySliderImagesResponse.propertySliderImages){
            imageList.add(SlideModel(slider.image))
        }
        PreferenceManger.saveSlider(getPropertySliderImagesResponse, PROPERTY_SLIDER_LIST)
         realEstateBinding.apply {
             imageSlider.setImageList(imageList, ScaleTypes.FIT)
             imageSlider.startSliding(3000)
         }
    }

    private fun setSliderData(){
        getPropertySliderImagesResponse = PreferenceManger.getSlider(PROPERTY_SLIDER_LIST)
        realEstateBinding.apply {
            for( slider in getPropertySliderImagesResponse.propertySliderImages){
                imageList.add(SlideModel(slider.image))
            }
            imageSlider.setImageList(imageList, ScaleTypes.FIT)
            imageSlider.startSliding(3000)
        }
    }

    override fun onFailurePropertySliderById(propertySliderResponseMain: PropertySliderResponseMain) {
        hideProgressDialog()
    }

    override fun onUserNotConnected() {

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
}