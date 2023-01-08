package com.vastu.realestate.appModule.dashboard.view

import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BulletSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.vastu.propertycore.model.response.PropertyDataResponseMain
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IPropertyDetailsListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IPropertySliderListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.VastuDashboardFragment.Companion.userId
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.dashboard.viewmodel.RealEstateDetailsViewModel
import com.vastu.realestate.databinding.FragmentRealEstateDetailsBinding
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestatecore.model.response.PropertyData
import com.vastu.slidercore.model.response.property.PropertySliderImage
import com.vastu.slidercore.model.response.property.PropertySliderResponseMain

class RealEstateDetailsFragment : BaseFragment(),IPropertyDetailsListener,IPropertySliderListener,
    IToolbarListener {
    private lateinit var realEstateDetailsBinding: FragmentRealEstateDetailsBinding
    private lateinit var realEstateDetailsViewModel: RealEstateDetailsViewModel
    private var propertyId : String? = null
    private var sliderList : List<PropertySliderImage>? = null
    private val imageList = ArrayList<SlideModel>()
    private lateinit var drawerViewModel: DrawerViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        realEstateDetailsViewModel = ViewModelProvider(this)[RealEstateDetailsViewModel::class.java]
        realEstateDetailsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_real_estate_details, container, false)
        realEstateDetailsBinding.realEstateDetailsViewModel = realEstateDetailsViewModel
        realEstateDetailsBinding.lifecycleOwner = this
        drawerViewModel.iToolbarListener = this
        realEstateDetailsViewModel.iPropertyDetailsListener = this
        realEstateDetailsViewModel.iPropertySliderListener = this
        realEstateDetailsBinding.drawerViewModel = drawerViewModel
        getBundleData()
        return realEstateDetailsBinding.root
    }
    private fun getBundleData(){
        val args = this.arguments
        if (args != null){
            if (args.getSerializable(BaseConstant.PROPERTY_DETAILS) != null) {
                val property =  args.getSerializable(BaseConstant.PROPERTY_DETAILS) as PropertyData
                propertyId = property.propertyId
            }
        }
    }

    override fun onResume() {
        super.onResume()
        drawerViewModel.toolbarTitle.set(getString(R.string.real_estate))
        drawerViewModel.isDashBoard.set(false)
        getPropertySlider()
    }
    private fun getPropertySlider(){
        showProgressDialog()
        propertyId?.let { realEstateDetailsViewModel.getPropertySlider(it) }
    }

    override fun onFailureGetPropertyDetails(propertyDataResponseMain: PropertyDataResponseMain) {
        hideProgressDialog()
        showDialog(propertyDataResponseMain.propertyIdResponse.responseStatusHeader.statusDescription,false,false)
    }

    override fun onSuccessPropertySliderById(propertySliderResponseMain: PropertySliderResponseMain) {
        imageList.clear()
        hideProgressDialog()
        sliderList = propertySliderResponseMain.getPropertySliderImagesResponse.propertySliderImages
        for( slider in sliderList!!){
            imageList.add(SlideModel(slider.image))
        }
        realEstateDetailsBinding.apply {
            imageSlider.setImageList(imageList, ScaleTypes.FIT)
            imageSlider.startSliding(3000)
        }
        getPropertyDetails()
    }

    override fun onFailurePropertySliderById(propertySliderResponseMain: PropertySliderResponseMain) {
        hideProgressDialog()
    }

    private fun getPropertyDetails(){
        hideProgressDialog()
        userId?.let {
            propertyId?.let { it1 ->
                realEstateDetailsViewModel.getPropertyDetails(it,it1)
            }
        }
    }

    override fun addPropertyEnquiry() {
        val bundle = Bundle()
        bundle.putSerializable(BaseConstant.PROPERTY_ID, propertyId)
        findNavController().navigate(R.id.action_RealEstateDetailsFragment_to_AddPropertyEnquiryFragment,bundle)
    }

    override fun chatEnquiry() {

    }

    override fun onSuccessGetPropertyDetails(propertyDataResponseMain: PropertyDataResponseMain) {
        hideProgressDialog()
        realEstateDetailsBinding.apply {
            val property = propertyDataResponseMain.getPropertyIdDetailsResponse.propertyIdData.get(0)
            propertyData = property
            val htmlPattern = "<[^>]*>"

            if(property.highlights.length>20){
                val spannable = SpannableString(propertyDataResponseMain.getPropertyIdDetailsResponse.propertyIdData.get(0).highlights)
                spannable.setSpan(BulletSpan(50,resources.getColor(R.color.black)), 9, 18,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannable.setSpan(BulletSpan(50, resources.getColor(R.color.black)), 20,  spannable.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                highlightsTextview.text = Html.fromHtml(propertyDataResponseMain.getPropertyIdDetailsResponse.propertyIdData.get(0).highlights.trim())
            }else{
                highlightsTextview.text = property.highlights
            }
            descriptionTextview.text =  property.description.replace(htmlPattern," ",ignoreCase = false)
        }
    }

    override fun onUserNotConnected() {
        hideProgressDialog()
        showDialog("",false,true)
    }

    override fun onClickBack() {
       activity?.onBackPressed()
    }

    override fun onClickMenu() {
    }

    override fun onClickNotification() {
    }
}
