package com.vastu.realestate.appModule.enquirylist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vastu.enquiry.loan.model.response.getAssignedLoanEnquiry.ObjEmpEnquiryDetailsData
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IAssignLeadListener
import com.vastu.realestate.databinding.AssignedLoanEnquiryBinding
import com.vastu.realestate.utils.BaseConstant

class AssignedLoanEnquiryAdapter(
    private val loanDataList: List<ObjEmpEnquiryDetailsData>,
    var iAssignLeadListener: IAssignLeadListener,
    var userType: String?
):
    RecyclerView.Adapter<AssignedLoanEnquiryViewHolder>()  {
    private lateinit var context: Context

    private lateinit var binding: AssignedLoanEnquiryBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignedLoanEnquiryViewHolder {
        context = parent.context
        binding = AssignedLoanEnquiryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AssignedLoanEnquiryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AssignedLoanEnquiryViewHolder, position: Int) {
        val loan = loanDataList[position]
        holder.bind(loan)
//        if (loan.assignee?.isNotEmpty() == true){
//            binding.txtAssignee.visibility = View.VISIBLE
//            binding.txtStatus.visibility = View.VISIBLE
//
        binding.remark.text = "Remark: "+loan.remark
            if(userType!!.equals(BaseConstant.EMPLOYEES))
                binding.btnAssignLead.text = "Update status"

//        }
//        else{
//            binding.txtAssignee.visibility = View.GONE
//            binding.txtStatus.visibility = View.GONE
//
//        }

        holder.binding.btnAssignLead.setOnClickListener {
            iAssignLeadListener.updateLoanLeadStatus(loan)

        }
    }

    override fun getItemCount(): Int =loanDataList.size
}
class AssignedLoanEnquiryViewHolder(val binding: AssignedLoanEnquiryBinding)
    :RecyclerView.ViewHolder(binding.root){
    fun bind(enquiryData: ObjEmpEnquiryDetailsData){
        binding.objEmpLoanEnquiryDtls = enquiryData
    }
}