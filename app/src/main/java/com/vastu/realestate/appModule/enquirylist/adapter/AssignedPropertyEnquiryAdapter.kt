package com.vastu.realestate.appModule.enquirylist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vastu.enquiry.property.model.response.getAssignedPropertyEnquiry.ObjEmpPropertyEnquiryDtlsData
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IAssignLeadListener
import com.vastu.realestate.databinding.AssignedPropertyEnquiryBinding
import com.vastu.realestate.databinding.AssignedPropertyEnquiryItemviewBinding
import com.vastu.realestate.utils.BaseConstant

class AssignedPropertyEnquiryAdapter(private val propertyDataList: List<ObjEmpPropertyEnquiryDtlsData>,
                                     var iAssignLeadListener: IAssignLeadListener, var userType: String?
) : RecyclerView.Adapter<AssignedPropertyEnquiryViewHolder>() {
    private lateinit var context: Context

    private lateinit var binding: AssignedPropertyEnquiryItemviewBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AssignedPropertyEnquiryViewHolder {
        context = parent.context
        binding = AssignedPropertyEnquiryItemviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AssignedPropertyEnquiryViewHolder(binding)
    }

    override fun getItemCount(): Int =propertyDataList.size

    override fun onBindViewHolder(holder: AssignedPropertyEnquiryViewHolder, position: Int) {
        val loan = propertyDataList[position]
        holder.bind(loan)

        if(userType!!.equals(BaseConstant.EMPLOYEES))
            binding.btnAssignLead.text = "Update status"

        holder.binding.btnAssignLead.setOnClickListener {
            iAssignLeadListener.updatePropertyLeadStatus(loan)

        }
    }
}
class AssignedPropertyEnquiryViewHolder(val binding:AssignedPropertyEnquiryItemviewBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(enquiryData: ObjEmpPropertyEnquiryDtlsData) {
        binding.objEmpPropertyEnquiryDtlsData = enquiryData

    }
}
