package com.vastu.realestate.appModule.enquirylist.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vastu.enquiry.employee.model.response.ObjEmployeeData
import com.vastu.enquiry.employee.model.response.ObjEmployeeResponse
import com.vastu.enquiry.loan.model.request.assignEnquiry.ObjAssignLoanEnquiryReq
import com.vastu.enquiry.loan.model.response.LoanData
import com.vastu.enquiry.property.model.request.assignEnquiry.ObjAssignPropertyEnquiryReq
import com.vastu.enquiry.property.model.response.EnquiryData
import com.vastu.realestate.R
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IAssignLeadListener
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IAssignLeadViewListener
import com.vastu.realestate.appModule.enquirylist.viewmodel.AssignLeadsViewModel
import com.vastu.realestate.databinding.AssignLeadsFragmentBinding
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestatecore.model.response.PropertyData

class AssignLeadsFragment(var listerner: IAssignLeadListener): BottomSheetDialogFragment(),View.OnTouchListener,
    IAssignLeadViewListener {
    lateinit var assignLeadsBinding :AssignLeadsFragmentBinding
    lateinit var assignLeadsViewModel: AssignLeadsViewModel
    lateinit var loanData :LoanData
    lateinit var propertyData:EnquiryData
     var objAssignLoanEnquiryReq = ObjAssignLoanEnquiryReq()
    var objAssignPropertyEnquiryReq = ObjAssignPropertyEnquiryReq()
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
        getBundleData()
        observeEmpList()
        return assignLeadsBinding.root
    }
    fun getBundleData(){
        try {
            val args = arguments
            if(args!=null) {
                if(args.getSerializable(BaseConstant.LOAN_DATA)!=null) {
                    loanData =
                        args.getSerializable(BaseConstant.LOAN_DATA) as LoanData

                }
                else if(args.getSerializable(BaseConstant.PROPERTY_DATA)!=null) {
                    propertyData =
                        args.getSerializable(BaseConstant.PROPERTY_DATA) as EnquiryData

                }

            }
            getEmployeeList()
        }catch (e:Exception){
            e.printStackTrace()
        }

    }
    fun getEmployeeList(){
        assignLeadsBinding.autoCompEmpName.setOnTouchListener(this)

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
        if(this::loanData.isInitialized){
            objAssignLoanEnquiryReq = objAssignLoanEnquiryReq.copy(loan_enq_id = loanData.loanId,
                emp_id = (assignLeadsViewModel.empName.value as ObjEmployeeData).empId)
            assignLeadsViewModel.callAssignLoanLeadApi(objAssignLoanEnquiryReq)
        }
        else  if(this::propertyData.isInitialized){
            objAssignPropertyEnquiryReq = objAssignPropertyEnquiryReq.copy( property_enq_id= propertyData.propertyId,
                emp_id = (assignLeadsViewModel.empName.value as ObjEmployeeData).empId)
            assignLeadsViewModel.callAssignPropertyLeadApi(objAssignPropertyEnquiryReq)
        }
    }
    override fun onEmpListFailure(error: String){
        listerner.onEmpListFailure(error,false,false)

    }
    override fun onLeadAssignSuccess(){
        listerner.onAssignLeadSuccess()
    }

    override fun onTouch(view: View?, p1: MotionEvent?): Boolean {
        if (view == assignLeadsBinding.autoCompEmpName) {
            view.let {
                onShowStateDropDown(it!!)
            }
        }
        return true
    }
    fun onShowStateDropDown(view: View){
        (view as AutoCompleteTextView).showDropDown()
    }

}