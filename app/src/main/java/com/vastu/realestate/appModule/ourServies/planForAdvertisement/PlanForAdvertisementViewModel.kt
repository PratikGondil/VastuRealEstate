package com.vastu.realestate.appModule.ourServies.planForAdvertisement

import android.app.Application
import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.vastu.realestate.R


class PlanForAdvertisementViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var mContext: Application
    var selectedQuery: ObservableField<String> = ObservableField<String>()

    lateinit var iPlanForAdvertisementViewListener: IPlanForAdvertisementViewListener

    init {
        mContext = application
        selectedQuery.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                Log.e("***", "text :${selectedQuery.get()}")
            }
        })
    }

    val allQuery: ObservableField<List<String>> =
        ObservableField<List<String>>(
            listOf(
                mContext.getString(R.string.profile_advertisement),
                mContext.getString(R.string.builder_and_developers),
                mContext.getString(R.string.engineer_and_architect),
                mContext.getString(R.string.construction_contractor),
                mContext.getString(R.string.cement),
                mContext.getString(R.string.steel),
                mContext.getString(R.string.hardware),
                mContext.getString(R.string.marbles),
                mContext.getString(R.string.centring_works),
                mContext.getString(R.string.electric_material),
                mContext.getString(R.string.pop_and_designs),
                mContext.getString(R.string.paint_colour),
                mContext.getString(R.string.interior_designs_and_material),
                mContext.getString(R.string.furniture),
                mContext.getString(R.string.bricks_cement_blocks),
                mContext.getString(R.string.alluminium_works),
                mContext.getString(R.string.fabrication),
                mContext.getString(R.string.plumbing),
                mContext.getString(R.string.home_appliances),
                mContext.getString(R.string.home_decor)
            )
        )

    fun submitClick(){
        iPlanForAdvertisementViewListener.onViewPlanBtnClick()
    }
}