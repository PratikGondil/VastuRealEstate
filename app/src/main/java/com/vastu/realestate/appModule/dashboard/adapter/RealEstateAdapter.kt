package com.vastu.realestate.appModule.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vastu.realestate.R
import com.vastu.realestate.databinding.RealEstateItemviewBinding
import com.vastu.realestate.utils.CommonUtils.Companion.showImageFromURL
import com.vastu.realestatecore.model.response.PropertyData


class RealEstateAdapter(private val itemClick: OnItemClickListener, private val realEstateList: List<PropertyData>
) : RecyclerView.Adapter<RealEstateViewHolder>() {
    private lateinit var context: Context
    private lateinit var binding: RealEstateItemviewBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RealEstateViewHolder {
        context = parent.context
        binding = RealEstateItemviewBinding.inflate(LayoutInflater.from(context), parent, false)
        return RealEstateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RealEstateViewHolder, position: Int) {
        val property = realEstateList[position]
        holder.bind(property)
        holder.binding.propertyPrizeTextview.text = context.getString(R.string.rupee)+" "+property.price
        holder.binding.propertyDetailsTextview.text = property.area+" "+ "sq.ft"
        showImageFromURL(context,property.propertyThumbnail, holder.binding.propertyImage)
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


