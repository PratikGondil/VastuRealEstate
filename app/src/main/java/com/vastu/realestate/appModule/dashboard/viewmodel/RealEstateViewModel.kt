package com.vastu.realestate.appModule.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IBackClickListener

class RealEstateViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var iBackClickListener: IBackClickListener
}