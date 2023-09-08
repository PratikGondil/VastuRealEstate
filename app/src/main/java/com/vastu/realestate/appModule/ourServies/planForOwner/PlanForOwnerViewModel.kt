package com.vastu.realestate.appModule.ourServies.planForOwner

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel

class PlanForOwnerViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var iPlanForPropertyOwner: IPlanForPropertyOwner
    var mContext:Application
    init {
        mContext=application
    }
     fun onClickDialog()
    {
        iPlanForPropertyOwner.onClickDialog()
        Log.e("hello","1")
    }
}