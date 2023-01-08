package com.vastu.realestate.appModule.dashboard.view.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.viewmodel.FilterViewModel
import com.vastu.realestate.databinding.FilterViewFargmentBinding

class FilterViewFragment:Fragment() {
    lateinit var filterViewModel: FilterViewModel
    lateinit var filterViewBinding: FilterViewFargmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        filterViewModel = ViewModelProvider(this)[FilterViewModel::class.java]
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}