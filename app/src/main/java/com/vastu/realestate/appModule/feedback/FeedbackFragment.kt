package com.vastu.realestate.appModule.feedback

import android.os.Bundle
import android.os.Handler
import android.text.Selection.setSelection
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vastu.networkService.util.Constants
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.view.DashboardFragment
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyDtls
import com.vastu.realestate.databinding.FeedbackFragmentBinding
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaDataList
import com.vastu.realestate.utils.PreferenceManger
import java.util.ArrayList

class FeedbackFragment :BaseFragment(), IToolbarListener, IFeedbackViewListener {

    lateinit var feedbackViewModel: FeedbackViewModel
    lateinit var drawerViewModel: DrawerViewModel
    lateinit var feedbackFragmentBinding: FeedbackFragmentBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        feedbackViewModel = ViewModelProvider(this)[FeedbackViewModel::class.java]
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        feedbackFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.feedback_fragment,container,false)
        feedbackFragmentBinding.feedbackViewModel = feedbackViewModel
        drawerViewModel.iToolbarListener = this
        feedbackViewModel.iFeedbackViewListener = this
        feedbackFragmentBinding.drawerViewModel= drawerViewModel
        initView()
        return feedbackFragmentBinding.root
    }

    private fun initView() {
        drawerViewModel.toolbarTitle.set(getString(R.string.feedback))
        drawerViewModel.isDashBoard.set(false)
        setSpinner()
    }

    private fun setSpinner() {
        lateinit var adapter:List<String>
        var isMarathi = false
        var language = PreferenceManger.get<String>(Constants.SELECTED_LANGUAGE)
        if(language.equals(Constants.MARATHI)){
            adapter  =feedbackViewModel.allQueryMarathi.get()!!
            isMarathi = true
        }else{
            isMarathi = false
            adapter  =feedbackViewModel.allQuery.get()!!
        }
        feedbackFragmentBinding.spinner.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.drop_down_item, adapter
            )
        )


        feedbackFragmentBinding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var selectedItem = ""
                if(isMarathi){
                    selectedItem = feedbackViewModel.allQueryMarathi.get()!!.get(position)
                    feedbackViewModel.selectedQuery.set(selectedItem)
                }else{
                    selectedItem = feedbackViewModel.allQuery.get()!!.get(position)
                    feedbackViewModel.selectedQuery.set(selectedItem)
                }



            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }



    }

    override fun onClickBack() {
        findNavController().navigate(R.id.action_feedback_to_Dashboard)
    }

    override fun onClickMenu() {

    }

    override fun onClickNotification() {

    }



    override fun onFeedbackFail(objFeedbackResponse: ObjFeedbackResponse) {
        hideProgressDialog()
        showDialog(objFeedbackResponse.objResponseStatusHdr.statusDescr,false,false)
    }

    override fun launchDashboardScreen(feedbackDataResponseMain: FeedbackDataResponseMain) {
       showDialog(feedbackDataResponseMain.objFeedbackResponse.objResponseStatusHdr.statusDescr,true,false)
        Handler().postDelayed({
            hideDialog()
        }, 750)
    }

    override fun onSubmitBtnClick() {
       feedbackViewModel.callFeedbackApi(DashboardFragment.userId!!,feedbackViewModel.selectedQuery!!.get().toString(),feedbackFragmentBinding.edtReview.text.toString())


    }


}