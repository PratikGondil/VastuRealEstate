package com.vastu.realestate.appModule.ourServies.viewPlan

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ViewPlansViewModel(application: Application) : AndroidViewModel(application) {

   lateinit var iViewPlanListener: IViewPlanListener
     fun onBuilderPlanClick() {
         iViewPlanListener.onBuilderPlanClick()
    }

     fun onAdvertisePlanClick() {
         iViewPlanListener.onAdvertisePlanClick()
    }
}