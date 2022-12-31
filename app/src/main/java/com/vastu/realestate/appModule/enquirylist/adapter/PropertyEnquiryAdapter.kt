package com.vastu.realestate.appModule.enquirylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vastu.enquiry.property.model.response.EnquiryData
import com.vastu.realestate.databinding.PropertyEnquiryItemviewBinding

class PropertyEnquiryAdapter(private val propertyDataList: List<EnquiryData>
) : RecyclerView.Adapter<PropertyEnquiryListViewHolder>() {

    private lateinit var binding:PropertyEnquiryItemviewBinding

    override fun onBindViewHolder(holder:PropertyEnquiryListViewHolder, position: Int) {
        val propertyData = propertyDataList[position]
        holder.bind(propertyData)
    }

    override fun getItemCount(): Int = propertyDataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyEnquiryListViewHolder {
       binding = PropertyEnquiryItemviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
       return PropertyEnquiryListViewHolder(binding)
    }

}
class PropertyEnquiryListViewHolder(val binding:PropertyEnquiryItemviewBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(enquiryData: EnquiryData) {
      binding.enquiryData = enquiryData
    }
}


