package com.vastu.realestate.appModule.signUp.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.ITermsConditionListener
import com.vastu.realestate.appModule.signUp.viewModel.SignUpViewModel
import com.vastu.realestate.databinding.LayTermsBinding
import com.vastu.termsandconditions.model.respone.TermsConditionData

class TermsConditionsScreen(var callback: SignUpFragment,var terms: List<TermsConditionData>): BottomSheetDialogFragment(),ITermsConditionListener {

    lateinit var signUpViewModel:SignUpViewModel
    lateinit var layTermsBinding: LayTermsBinding
    lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
             bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                behaviour.expandedOffset = 50
                //setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                bottomSheetDialog.setCancelable(false)
            }
        }
        return dialog
    }
    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        var dMetrics = resources.getDisplayMetrics()
        val h = Math.round(dMetrics.heightPixels / dMetrics.density)
        layoutParams.height = h*2

        bottomSheet.layoutParams = layoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        signUpViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        layTermsBinding = DataBindingUtil.inflate(inflater, R.layout.lay_terms,container,false)
        layTermsBinding.lifecycleOwner = this
        layTermsBinding.signUpViewModel = signUpViewModel
        signUpViewModel.iTermsConditionListener = this
        initView()
        return layTermsBinding.root
    }
    private fun initView(){
        layTermsBinding.apply {
            tvTerms.text = terms.get(0).termCondition
        }
    }

    override fun onAcceptTerms() {
       callback.onAcceptTerms()
       bottomSheetDialog.setCancelable(true)
    }

    override fun onRejectTerms() {
      callback.onFailureTerms()
      bottomSheetDialog.setCancelable(true)
    }
}