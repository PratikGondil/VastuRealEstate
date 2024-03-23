package com.vastu.realestate.appModule.ourServies.planForOwner

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.vastu.realestate.appModule.ourServies.planForOwner.response.ObjPlanByTypeResponseMain
import com.vastu.realestate.appModule.ourServies.viewPlan.IPlanTypeResponseListener
import com.vastu.realestate.appModule.ourServies.viewPlan.IPlansByTypeViewListener
import com.vastu.realestate.appModule.ourServies.viewPlan.IViewPlanListener
import com.vastu.realestate.appModule.ourServies.viewPlan.PlanTypeDataResponseMain
import com.vastu.realestate.appModule.ourServies.viewPlan.PlanTypeRepository
import com.vastu.realestate.appModule.selectlanguage.uiinterface.iSelectLanguage
import com.vastu.realestate.utils.ApiUrlEndPoints

class PlanForOwnerViewModel(application: Application) : AndroidViewModel(application),
    IPlanTypeResponseListener {
    lateinit var iPlanForPropertyOwner: IPlanForPropertyOwner
    lateinit var iPlansTypeViewListener: IPlansByTypeViewListener

    var mContext:Application
    init {
        mContext=application
    }
     fun onClickDialog()
    {
        iPlanForPropertyOwner.onClickDialog()
        Log.e("hello","1")
    }

    fun callPlansTypeApi(language: String,planTypeId:String,profileId:String) {
        PlanTypeRepository.callPlansTypeApi(mContext,language, planTypeId,profileId,
            ApiUrlEndPoints.GET_PLANS, this)
    }

    override fun onGetSuccessResponse(response: ObjPlanByTypeResponseMain) {
        iPlansTypeViewListener.onPlanSuccess(response)
    }

    override fun onGetFailureResponse(response: ObjPlanByTypeResponseMain) {
        iPlansTypeViewListener.onPlansFail(response)
    }

    override fun networkFailure() {

    }
}