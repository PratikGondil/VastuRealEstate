package com.vastu.realestate.appModule.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IDashboardViewListener

class VastuDashboardViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var iDashboardViewListener : IDashboardViewListener

    fun onClickRealEstate(){
        iDashboardViewListener.onRealEstateClick()
    }
    fun onClickLoan(){
        iDashboardViewListener.onLoanClick()
    }

}