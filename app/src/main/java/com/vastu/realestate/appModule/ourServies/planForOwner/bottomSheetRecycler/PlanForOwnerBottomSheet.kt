package com.vastu.realestate.appModule.ourServies.planForOwner.bottomSheetRecycler

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.ChipGroup
import com.vastu.realestate.R
import com.vastu.realestate.appModule.ourServies.planForOwner.PlanForOwnerViewModel
import com.vastu.realestate.appModule.ourServies.planForOwner.PlanForPropertyOwnerFragment
import com.vastu.realestate.databinding.OurServiceBottomSheetDBinding


class PlanForOwnerBottomSheet(var callback: PlanForPropertyOwnerFragment):
    BottomSheetDialogFragment()  {
    lateinit var planforOwnerBottomSheetDBinding: OurServiceBottomSheetDBinding
    lateinit var planForOwnerViewModel: PlanForOwnerViewModel
    lateinit var itemsList : ArrayList<PlanDuration>
    lateinit var bottomSheetAdapter: BottomSheetAdapter
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                behaviour.expandedOffset = 50
               // setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        planForOwnerViewModel = ViewModelProvider(this)[PlanForOwnerViewModel::class.java]
        planforOwnerBottomSheetDBinding = DataBindingUtil.inflate(inflater, R.layout.our_service_bottom_sheet_d,container,false)
        planforOwnerBottomSheetDBinding.lifecycleOwner = this
        planforOwnerBottomSheetDBinding.planForOwnerViewModel = planForOwnerViewModel
            //  planForOwnerViewModel.iPlanForPropertyOwner = this
     //   iniView()
        setPlanListData()
        return planforOwnerBottomSheetDBinding.root
    }
    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        var dMetrics = resources.getDisplayMetrics()
        val h = Math.round(dMetrics.heightPixels / dMetrics.density)
        layoutParams.height = h*2

        bottomSheet.layoutParams = layoutParams

    }

    fun setPlanListData(){
        val layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, true)
        planforOwnerBottomSheetDBinding.rvPlanList.layoutManager = layoutManager


        val planData=ArrayList<PlanDuration>()
        val i1=PlanDuration(true,"2 month","₹ 23049","₹ 999 /- month")
        val i2=PlanDuration(false,"2 month","₹ 23049","₹ 999 /- month")
        val i3=PlanDuration(false,"2 month","₹ 23049","₹ 999 /- month")
        val i4=PlanDuration(false,"2 month","₹ 23049","₹ 999 /- month")
        val i5=PlanDuration(false,"2 month","₹ 23049","₹ 999 /- month")


        planData.add(i1)
        planData.add(i2)
        planData.add(i3)
        planData.add(i4)
        planData.add(i5)
        planforOwnerBottomSheetDBinding.rvPlanList.adapter=BottomSheetAdapter(planData)
    }
}