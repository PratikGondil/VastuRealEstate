package com.vastu.realestate.appModule.enquirylist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vastu.enquiry.property.model.response.EnquiryData
import com.vastu.realestate.R
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IAssignLeadListener
import com.vastu.realestate.databinding.PropertyEnquiryItemviewBinding

class PropertyEnquiryAdapter(private val propertyDataList: List<EnquiryData>,var iAssignLeadListener: IAssignLeadListener
) : RecyclerView.Adapter<PropertyEnquiryListViewHolder>() {
    private lateinit var context:Context

    private lateinit var binding:PropertyEnquiryItemviewBinding

    override fun onBindViewHolder(holder:PropertyEnquiryListViewHolder, position: Int) {
        val propertyData = propertyDataList[position]
        holder.bind(propertyData)
        binding.propertyNameTextview.text = context.getString(R.string.username,propertyData.firstName,propertyData.middleName,propertyData.lastName)
        holder.binding.btnAssignLead.setOnClickListener {
            iAssignLeadListener.assignPropertyLeadToEmployee(propertyData)

        }
    }

    override fun getItemCount(): Int = propertyDataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyEnquiryListViewHolder {
       context = parent.context
       binding = PropertyEnquiryItemviewBinding.inflate(LayoutInflater.from(context), parent, false)
       return PropertyEnquiryListViewHolder(binding)
    }

}
class PropertyEnquiryListViewHolder(val binding:PropertyEnquiryItemviewBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(enquiryData: EnquiryData) {
      binding.enquiryData = enquiryData
    }
}


