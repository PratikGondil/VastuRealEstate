package com.vastu.realestate.appModule.realCreator.creatorDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.aemerse.slider.model.CarouselItem
import com.denzcoskun.imageslider.models.SlideModel
import com.vastu.propertycore.model.response.PropertyIdData
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.view.DashboardActivity
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.dashboard.viewmodel.RealEstateDetailsViewModel
import com.vastu.realestate.databinding.CreatorDetailsPageBinding
import com.vastu.realestate.databinding.FragmentRealEstateDetailsBinding
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestatecore.model.response.PropertyData
import com.vastu.slidercore.model.response.realestatedetails.BrochureSlider
import com.vastu.slidercore.model.response.realestatedetails.BuilderSlider
import com.vastu.slidercore.model.response.realestatedetails.PropertySlider
import java.util.regex.Pattern

class CreatorDetailsFragment: BaseFragment(), IToolbarListener,ICreatorDetailsListener {



    private lateinit var realEstateDetailsBinding: CreatorDetailsPageBinding
    private lateinit var realEstateDetailsViewModel: CreatorDetailsViewModel
    private var propertyId : String? = null
    private var propertySliderList : List<PropertySlider>? = null
    private var buiderSlider : List<BuilderSlider>? = null
    private var brouchureSlider : List<BrochureSlider>? = null

    private val imageList = ArrayList<SlideModel>()
    private val imageListbuilder = ArrayList<CarouselItem>()
    private val imageListCarouselProperty = ArrayList<CarouselItem>()
    private val imageListbrocure = ArrayList<CarouselItem>()

    private lateinit var drawerViewModel: DrawerViewModel
    private val REMOVE_TAGS: Pattern = Pattern.compile("<.+?>")
    lateinit var  propertyIdDataList: PropertyIdData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        realEstateDetailsViewModel = ViewModelProvider(this)[CreatorDetailsViewModel::class.java]
        realEstateDetailsBinding = DataBindingUtil.inflate(inflater,
            R.layout.creator_details_page, container, false)
        realEstateDetailsBinding.creatorListDetailsViewModel = realEstateDetailsViewModel
        realEstateDetailsBinding.lifecycleOwner = this
        drawerViewModel.iToolbarListener = this
        realEstateDetailsViewModel.iCreatorDetailsListener  = this

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







    override fun onClickBack() {
        TODO("Not yet implemented")
    }

    override fun onClickMenu() {
        TODO("Not yet implemented")
    }

    override fun onClickNotification() {
        TODO("Not yet implemented")
    }
}