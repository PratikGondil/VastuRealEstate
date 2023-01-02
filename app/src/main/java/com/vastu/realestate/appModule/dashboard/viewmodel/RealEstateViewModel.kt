package com.vastu.realestate.appModule.dashboard.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IBackClickListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IFilterClickListener
import java.util.*

class RealEstateViewModel(application: Application) : AndroidViewModel(application) {
    var isFilterViewVisible = ObservableField(View.GONE)
    var isRealEstateVisible = ObservableField(View.VISIBLE)
    lateinit var iBackClickListener: IBackClickListener
    lateinit var iFilterClickListener: IFilterClickListener
    fun filterClick(){
        iFilterClickListener.setFilterView()
    }
}