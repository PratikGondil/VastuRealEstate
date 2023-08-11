package com.vastu.realestate.appModule.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vastu.realestate.R
import com.vastu.realestate.databinding.RealEstateItemviewBinding
import com.vastu.realestate.utils.CommonUtils.Companion.showImageFromURL
import com.vastu.realestatecore.model.response.PropertyData


class RealEstateAdapter(
    private val itemClick: OnItemClickListener, realEstateList: List<PropertyData>
) : RecyclerView.Adapter<RealEstateViewHolder>() {
    private lateinit var context: Context
    private lateinit var binding: RealEstateItemviewBinding
    var realEstateListCurrent: List<PropertyData>

    init {
        realEstateListCurrent = realEstateList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RealEstateViewHolder {
        context = parent.context
        binding = RealEstateItemviewBinding.inflate(LayoutInflater.from(context), parent, false)
        return RealEstateViewHolder(binding)
    }

    fun filterList(filterlist: ArrayList<PropertyData>) {
        // below line is to add our filtered
        // list in our course array list.
        realEstateListCurrent = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RealEstateViewHolder, position: Int) {
        val property = realEstateListCurrent[position]
        holder.bind(property)
        holder.binding.propertyPrizeTextview.text =
            context.getString(R.string.rupee) + " " + property.price
        holder.binding.propertyDetailsTextview.text =
            property.propertyArea + " " + context.getString(R.string.st_ft)
        showImageFromURL(
            context,
            property.propertyThumbnail,
            holder.binding.propertyImage,
            R.drawable.vastu_logo_splash
        )

        if(position==0){
            holder.binding.imgE.visibility=View.VISIBLE
        showImageFromURL(
            context,
            property.propertyThumbnail,
            holder.binding.imgE,
            R.drawable.vastu_logo_splash
        )
        }else{
            holder.binding.imgE.visibility=View.GONE
        }

        if(position==2) {
            holder.binding.video.andExoPlayerView.visibility=View.VISIBLE
            holder.binding.video.andExoPlayerView.setSource("https://myclanservices.co.in/pratik/video.mp4")
        }else{
            holder.binding.video.andExoPlayerView.visibility=View.GONE
        }
        holder.binding.layoutContainer.setOnClickListener {
            itemClick.onItemClick(property)
        }
    }

    override fun getItemCount(): Int = realEstateListCurrent.size

    interface OnItemClickListener {
        fun onItemClick(propertyData: PropertyData)
    }

}

class RealEstateViewHolder(
    val binding: RealEstateItemviewBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(propertyData: PropertyData) {
        binding.propertyData = propertyData
    }
}


