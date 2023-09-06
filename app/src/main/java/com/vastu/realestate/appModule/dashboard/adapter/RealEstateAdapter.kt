package com.vastu.realestate.appModule.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vastu.realestate.R
import com.vastu.realestate.databinding.RealEstateItemviewBinding
import com.vastu.realestate.utils.CommonUtils.Companion.showImageFromURL
import com.vastu.realestatecore.model.response.AdSlider
import com.vastu.realestatecore.model.response.PropertyData


class RealEstateAdapter(
    private val itemClick: OnItemClickListener, realEstateList: List<PropertyData>,sliderList:List<AdSlider>
) : RecyclerView.Adapter<RealEstateViewHolder>() {
    private lateinit var context: Context
    private lateinit var binding: RealEstateItemviewBinding
    var realEstateListCurrent: List<PropertyData>
    var adSlider: List<AdSlider>
    lateinit var adSliderImage: AdSlider
    lateinit var adSliderVideo: AdSlider

    init {
        realEstateListCurrent = realEstateList
        adSlider = sliderList
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
            context.getString(R.string.rupee) + " " + property.priceMinWord + "-" + property.priceMaxWord
        holder.binding.propertyBhkDtlsTxt.text =
            property.bedroom + " " + context.getString(R.string.bhk_apartments)
        showImageFromURL(
            context,
            property.propertyThumbnail,
            holder.binding.propertyImage,
            R.drawable.vastu_logo_splash
        )

        if(adSlider.isNotEmpty() && adSlider.size==2){
            adSliderImage=adSlider[0]
            adSliderVideo=adSlider[1]
            if (position-1 == adSliderImage.position?.toInt()) {
                holder.binding.imgE.visibility = View.VISIBLE
                showImageFromURL(
                    context,
                    adSliderImage.slider,
                    holder.binding.imgE,
                    R.drawable.vastu_logo_splash
                )
            } else {
                holder.binding.imgE.visibility = View.GONE
            }
            if (position-1 == adSliderVideo.position?.toInt()) {
                holder.binding.video.andExoPlayerView.visibility = View.VISIBLE
                holder.binding.video.andExoPlayerView.setSource(adSliderVideo.slider.toString())
            } else {
                holder.binding.video.andExoPlayerView.visibility = View.GONE
            }

        }
        else {
            holder.binding.imgE.visibility = View.GONE
            holder.binding.video.andExoPlayerView.visibility = View.GONE
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


