package com.vastu.realestate.appModule.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.dashboard.viewmodel.LoanViewModel
import com.vastu.realestate.databinding.FragmentLoanBinding
import com.vastu.realestate.utils.PreferenceKEYS
import com.vastu.realestate.utils.PreferenceManger
import com.vastu.slidercore.model.response.GetPropertySliderImagesResponse
import com.vastu.slidercore.model.response.PropertySliderImage

class LoanFragment : BaseFragment(),IToolbarListener {

    private lateinit var loanBinding: FragmentLoanBinding
    private lateinit var loanViewModel: LoanViewModel
    private lateinit var drawerViewModel: DrawerViewModel
    private lateinit var getPropertySliderImagesResponse: GetPropertySliderImagesResponse
    private val imageList = ArrayList<SlideModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loanViewModel = ViewModelProvider(this)[LoanViewModel::class.java]
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        loanBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_loan, container, false)
        loanBinding.lifecycleOwner = this
        drawerViewModel.iToolbarListener = this
        loanBinding.loanViewModel = loanViewModel
        loanBinding.drawerViewModel= drawerViewModel
        setSliderData()
        return loanBinding.root
    }
    private fun setSliderData(){
        getPropertySliderImagesResponse = PreferenceManger.getSlider(PreferenceKEYS.DASHBOARD_SLIDER_LIST)
        loanBinding.apply {
            for( slider in getPropertySliderImagesResponse.propertySliderImages){
                imageList.add(SlideModel(slider.image))
            }
            imageSlider.setImageList(imageList, ScaleTypes.FIT)
            imageSlider.startSliding(3000)
        }
    }

    override fun onResume() {
        super.onResume()
        drawerViewModel.toolbarTitle.set(getString(R.string.loan))
        drawerViewModel.isDashBoard.set(false)
    }

    override fun onClickBack() {
        activity?.onBackPressed()
    }

    override fun onClickMenu() {
        TODO("Not yet implemented")
    }

    override fun onClickNotification() {
        TODO("Not yet implemented")
    }
}