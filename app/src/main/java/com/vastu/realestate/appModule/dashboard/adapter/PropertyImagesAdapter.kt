package com.vastu.realestate.appModule.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vastu.getimages.model.response.ImageData
import com.vastu.realestate.R
import com.vastu.realestate.databinding.ImagesLayoutBinding
import com.vastu.realestate.utils.CommonUtils

class PropertyImagesAdapter(private val itemClick:OnItemClickListener, private val imageList: List<ImageData>
) : RecyclerView.Adapter<PropertyImagesAdapter.ImageViewHolder>() {
    private lateinit var context: Context
    private lateinit var binding: ImagesLayoutBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        context = parent.context
        binding = ImagesLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = imageList[position]
        holder.bind(image)
        CommonUtils.showImageFromURL(
            context,
            image.image,
            holder.binding.PropertyImage,
            R.drawable.vastu_logo_splash
        )
        holder.binding.btnDelete.setOnClickListener {
            itemClick.onItemClick(image)
        }
    }

    override fun getItemCount(): Int = imageList.size

    interface OnItemClickListener{
        fun onItemClick(images: ImageData)
    }
    class ImageViewHolder(val binding: ImagesLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(images: ImageData) {
            binding.imageData = images
        }
    }
}