package com.vastu.realestate.appModule.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.callback.IDashboardViewListener
import com.vastu.realestate.appModule.dashboard.viewholder.DashboardViewModel
import com.vastu.realestate.databinding.FragmentVastuDashboardBinding


class VastuDashboardFragment : Fragment() {
        lateinit var dashboardBinding: FragmentVastuDashboardBinding
        lateinit var dashboardViewModel: DashboardViewModel
        lateinit var iDashboardViewListener: IDashboardViewListener
        val imageList = ArrayList<SlideModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =ViewModelProvider(this)[DashboardViewModel::class.java]
        dashboardBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_vastu_dashboard, container, false)
        dashboardBinding.lifecycleOwner = this
        dashboardBinding.vastuViewModel = dashboardViewModel
        initSlider()
        return dashboardBinding.root
    }
    private fun initSlider(){
        imageList.add(SlideModel(R.drawable.banner))
        imageList.add(SlideModel(R.drawable.banner))
        imageList.add(SlideModel(R.drawable.banner))
        dashboardBinding.apply {
            menuImageview.setOnClickListener {

            }
            imageSlider.setImageList(imageList,ScaleTypes.FIT)
            imageSlider.startSliding(100)
            imageSlider.startSliding()
            imageSlider.stopSliding()
        }
    }
}