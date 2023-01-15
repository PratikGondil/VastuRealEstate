package com.vastu.realestate.appModule.enquirylist.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vastu.enquiry.employee.model.response.ObjEmployeeData
import com.vastu.enquiry.employee.model.response.ObjEmployeeResponse
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.viewmodel.RealEstateViewModel
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IAssignLeadListener
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IAssignLeadViewListener
import com.vastu.realestate.appModule.enquirylist.viewmodel.AssignLeadsViewModel
import com.vastu.realestate.databinding.AssignLeadsFragmentBinding
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaDataList

class AssignLeadsFragment(var listerner: IAssignLeadListener): BottomSheetDialogFragment(),
    IAssignLeadViewListener {
    lateinit var assignLeadsBinding :AssignLeadsFragmentBinding
    lateinit var assignLeadsViewModel: AssignLeadsViewModel
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setHeight(it)
                behaviour.expandedOffset = 50
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }
    private fun setHeight(bottomSheet: View) {
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
        assignLeadsViewModel = ViewModelProvider(this)[AssignLeadsViewModel::class.java]
        assignLeadsBinding = DataBindingUtil.inflate(inflater, R.layout.assign_leads_fragment,container,false)
//        tabLayout = multipleFiltersBinding.filterType
        assignLeadsBinding.lifecycleOwner = this
        assignLeadsBinding.assignLeadsViewModel = assignLeadsViewModel
        assignLeadsViewModel.iAssignLeadViewListener = this
        getEmployeeList()
        observeEmpList()
        return assignLeadsBinding.root
    }

    fun getEmployeeList(){
        assignLeadsViewModel.callEmployeeListApi()
    }

    fun observeEmpList(){
        assignLeadsViewModel.employeeList.observe(viewLifecycleOwner) { empList ->

            val adapter: ArrayList<ObjEmployeeData> = empList
            assignLeadsBinding.autoCompEmpName.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.drop_down_item, adapter
                )
            )
        }

    }

    override fun callAssignApi() {
    //assign
    }
    override fun onEmpListFailure(objEmployeeResponse: ObjEmployeeResponse){
        listerner.onEmpListFailure(objEmployeeResponse.ResponseStatusHeader!!.statusDescription!!,false,false)

    }
}