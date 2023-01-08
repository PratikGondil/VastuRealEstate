package com.vastu.realestate.appModule.dashboard.view.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.vastu.realestate.R

class SlidingPanel:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    private var mBottomSheetBehavior: BottomSheetBehavior<View?>? = null

//    private fun configureBackdrop() {
//        // Get the fragment reference
//        val fragment = requireActivity().supportFragmentManager.findFragmentById(R.id.filter_fragment)
//
//        fragment?.let {
//            // Get the BottomSheetBehavior from the fragment view
//            BottomSheetBehavior.from(it.view)?.let { bsb ->
//                // Set the initial state of the BottomSheetBehavior to HIDDEN
//                bsb.state = BottomSheetBehavior.STATE_HIDDEN
//
//                // Set the trigger that will expand your view
//                fab_filter.setOnClickListener { bsb.state = BottomSheetBehavior.STATE_EXPANDED }
//
//                // Set the reference into class attribute (will be used latter)
//                mBottomSheetBehavior = bsb
//            }
//        }
//    }
}