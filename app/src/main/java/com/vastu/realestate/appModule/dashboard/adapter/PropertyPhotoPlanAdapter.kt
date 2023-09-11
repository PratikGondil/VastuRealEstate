package com.vastu.realestate.appModule.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vastu.realestate.R
import com.vastu.realestate.databinding.PropertyPhotoPlanItemViewBinding
import com.vastu.realestate.utils.CommonUtils
import com.vastu.slidercore.model.response.property.PropertySliderImage


class PropertyPhotoPlanAdapter(
    val context: Context,
    private val images: List<PropertySliderImage>
) :
    RecyclerView.Adapter<PropertyPhotoPlanAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = PropertyPhotoPlanItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val photos = images[position]
        holder.bind(photos)
        CommonUtils.showImageFromURL(
            context,
            photos.image,
            holder.binding.imageView,
            R.drawable.vastu_logo_splash
        )
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ImageViewHolder(val binding: PropertyPhotoPlanItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(propertySliderImage: PropertySliderImage) {
           binding.photoItem=propertySliderImage
        }
    }
}