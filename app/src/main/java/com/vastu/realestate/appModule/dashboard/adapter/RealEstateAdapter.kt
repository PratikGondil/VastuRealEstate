package com.vastu.realestate.appModule.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vastu.realestate.appModule.enquirylist.view.LoanEnquiryListFragment
import com.vastu.realestate.databinding.RealEstateItemviewBinding
import com.vastu.realestatecore.model.response.PropertyData


class RealEstateAdapter(private val itemClick: OnItemClickListener, private val realEstateList: List<PropertyData>
) : RecyclerView.Adapter<RealEstateViewHolder>() {

    private lateinit var binding: RealEstateItemviewBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RealEstateViewHolder {
        binding = RealEstateItemviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RealEstateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RealEstateViewHolder, position: Int) {
        val property = realEstateList[position]
        holder.bind(property)
        holder.itemView.setOnClickListener {
            itemClick.onItemClick(property)
        }
    }

    override fun getItemCount(): Int = realEstateList.size

    interface OnItemClickListener{
        fun onItemClick(propertyData: PropertyData)
    }

}
    class RealEstateViewHolder(val binding: RealEstateItemviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(propertyData: PropertyData) {
            binding.propertyData = propertyData
        }
    }


