package com.vastu.realestate.appModule.dashboard.adapter

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
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
    lateinit var mediaPlayer: MediaPlayer
    var isunMute = false

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
        holder.binding.imgLike.setOnClickListener {
            itemClick.onWishlistClick(property)
        }
        manageAddSlider(holder,position)
        holder.binding.layoutContainer.setOnClickListener {
            itemClick.onItemClick(property)
        }
    }

    private fun manageAddSlider(holder: RealEstateViewHolder, position: Int) {
        if(adSlider.isNotEmpty() && adSlider.size==2){
            adSliderImage=adSlider[0]
            adSliderVideo=adSlider[1]
            if (position == adSliderImage.position?.toInt()) {
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
            if (position == adSliderVideo.position?.toInt()) {
                binding.video.setMediaController(null);
                binding.video.setVideoURI(Uri.parse(adSliderVideo.slider.toString()))
                holder.binding.video.visibility = View.VISIBLE
                holder.binding.mute.visibility = View.VISIBLE
                binding.video.setOnPreparedListener(OnPreparedListener { mp ->
                    mediaPlayer = mp
                    mp.setVolume(0f, 0f)
                    mp.isLooping = true
                })
                binding.video.requestFocus()
                binding.video.start()

                holder.binding.mute.setOnClickListener {
                    if(!isunMute) {
                        isunMute = true
                        holder.binding.mute.setImageResource(R.drawable.baseline_volume_up_24)
                        setVolume(100)
                    }else{
                        isunMute = false
                        holder.binding.mute.setImageResource(R.drawable.baseline_volume_off_24)
                        setVolume(0)
                    }

                }


            } else {
                holder.binding.video.visibility = View.GONE
            }

        }
        else {
            holder.binding.imgE.visibility = View.GONE
            holder.binding.video.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = realEstateListCurrent.size

    interface OnItemClickListener {
        fun onItemClick(propertyData: PropertyData)
        fun onWishlistClick(propertyData: PropertyData)
    }
    private fun setVolume(amount: Int) {
        val max = 100
        val numerator: Double = if (max - amount > 0) Math.log((max - amount).toDouble()) else 0.0
        val volume = (1 - numerator / Math.log(max.toDouble())).toFloat()
        mediaPlayer!!.setVolume(volume, volume)
    }

}
class RealEstateViewHolder(
    val binding: RealEstateItemviewBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(propertyData: PropertyData) {
        binding.propertyData = propertyData

    }
}



