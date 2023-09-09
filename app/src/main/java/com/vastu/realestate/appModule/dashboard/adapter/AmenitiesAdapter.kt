package com.vastu.realestate.appModule.dashboard.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vastu.propertycore.model.response.Amenity
import com.vastu.realestate.R
import com.vastu.realestate.databinding.AminityItemViewBinding
import com.vastu.realestate.databinding.RealEstateItemviewBinding
import com.vastu.realestate.utils.CommonUtils
import com.vastu.realestatecore.model.response.PropertyData

class AmenitiesAdapter(private val context: Context, private val amenityList: List<Amenity>) :
        RecyclerView.Adapter<AmenitiesAdapter.ViewHolder>() {
    lateinit var amenityItemViewBinding: AminityItemViewBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            amenityItemViewBinding=AminityItemViewBinding.inflate(LayoutInflater.from(context),parent,false)
            return ViewHolder(amenityItemViewBinding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val amenity = amenityList[position]
            holder.bind(amenity)
            holder.binding.imageView.setImageURI(Uri.parse(amenity.icon))
            holder.binding.textView.text=amenity.amenitiesNameMarathi

            CommonUtils.showImageFromURL(
                context,
                amenity.icon,
                holder.binding.imageView,
                R.drawable.vastu_logo_splash
            )
        }

        override fun getItemCount(): Int {
            return amenityList.size
        }
    class ViewHolder(
        val binding: AminityItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(amenity: Amenity) {
            binding.amenity = amenity
        }
    }
    }
