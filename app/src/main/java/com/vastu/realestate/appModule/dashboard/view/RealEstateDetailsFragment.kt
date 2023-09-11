package com.vastu.realestate.appModule.dashboard.view

import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BulletSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.rajat.pdfviewer.PdfViewerActivity
import com.vastu.propertycore.model.response.Amenity
import com.vastu.propertycore.model.response.PropertyDataResponseMain
import com.vastu.propertycore.model.response.PropertyIdData
import com.vastu.propertycore.model.response.RelatedProperty
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.adapter.AmenitiesAdapter
import com.vastu.realestate.appModule.dashboard.adapter.RelatedPropertyAdapter
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IPropertyDetailsListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IPropertySliderListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.DashboardFragment.Companion.userId
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.dashboard.viewmodel.RealEstateDetailsViewModel
import com.vastu.realestate.databinding.FragmentRealEstateDetailsBinding
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestatecore.model.response.PropertyData
import com.vastu.slidercore.model.response.property.PropertySliderImage
import com.vastu.slidercore.model.response.property.PropertySliderResponseMain
import java.util.regex.Pattern

class RealEstateDetailsFragment : BaseFragment(),IPropertyDetailsListener,IPropertySliderListener,
    IToolbarListener {
    private lateinit var realEstateDetailsBinding: FragmentRealEstateDetailsBinding
    private lateinit var realEstateDetailsViewModel: RealEstateDetailsViewModel
    private var propertyId : String? = null
    private var sliderList : List<PropertySliderImage>? = null
    private val imageList = ArrayList<SlideModel>()
    private lateinit var drawerViewModel: DrawerViewModel
    private val REMOVE_TAGS: Pattern = Pattern.compile("<.+?>")
    lateinit var  propertyIdDataList: PropertyIdData

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
        if(activity is DashboardActivity)
        {
            (activity as DashboardActivity).bottomNavigationView.visibility= View.GONE
        }
        val args = this.arguments
        if (args != null){
            if (args.getSerializable(BaseConstant.PROPERTY_DETAILS) != null) {
                val property =  args.getSerializable(BaseConstant.PROPERTY_DETAILS) as PropertyData
                propertyId = property.propertyId
            }
            if (args.getSerializable(BaseConstant.PROPERTY_ID) != null) {
                val property =  args.getSerializable(BaseConstant.PROPERTY_ID).toString()
                propertyId = property
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
        showDialog(propertyDataResponseMain.propertyIdResponse.responseStatusHeader.statusDescription!!,false,false)
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
    private fun setAmenityDetails(amenity:List<Amenity>){
        try {
            val recyclerViewAmenity = realEstateDetailsBinding.aminityRecyclerView
            val amenityAdapter = AmenitiesAdapter(requireContext(),amenity)
            recyclerViewAmenity.adapter = amenityAdapter
            recyclerViewAmenity.layoutManager = GridLayoutManager(activity,2)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun setRelatedPropertyDetails(relatedProperty:List<RelatedProperty>){
        try {
            val recyclerViewProperty = realEstateDetailsBinding.relatedRecyclerView
            val relatedPropertyAdapter = RelatedPropertyAdapter(requireContext(),relatedProperty)
            recyclerViewProperty.adapter = relatedPropertyAdapter
            recyclerViewProperty.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun chatEnquiry() {


    }

    override fun viewbroture() {
        PdfViewerActivity.launchPdfFromUrl(
            context, propertyIdDataList.brochure,
            propertyIdDataList.propertyTitle,
            "vastu",
            enableDownload = true
        )
    }

    override fun onSuccessGetPropertyDetails(propertyDataResponseMain: PropertyDataResponseMain) {
        hideProgressDialog()
        realEstateDetailsBinding.apply {
            val property = propertyDataResponseMain.getPropertyIdDetailsResponse.propertyIdData.get(0)
            propertyIdDataList = property
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

            if(property.description.length>20){
                val spannable = SpannableString(propertyDataResponseMain.getPropertyIdDetailsResponse.propertyIdData.get(0).description)
                spannable.setSpan(BulletSpan(50,resources.getColor(R.color.black)), 9, 18,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannable.setSpan(BulletSpan(50, resources.getColor(R.color.black)), 20,  spannable.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                descriptionTextview.text = Html.fromHtml(propertyDataResponseMain.getPropertyIdDetailsResponse.propertyIdData.get(0).description)
            }else{
                descriptionTextview.text = property.highlights
            }
            setAmenityDetails(propertyDataResponseMain.getPropertyIdDetailsResponse.amenities)
            setRelatedPropertyDetails(propertyDataResponseMain.getPropertyIdDetailsResponse.relatedProperty)
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
    override fun onPause() {
        if(activity is DashboardActivity)
        {
            (activity as DashboardActivity).bottomNavigationView.visibility= View.VISIBLE
        }
        super.onPause()
    }
}
