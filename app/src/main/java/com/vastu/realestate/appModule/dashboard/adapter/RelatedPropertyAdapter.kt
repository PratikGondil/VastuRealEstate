package com.vastu.realestate.appModule.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vastu.propertycore.model.response.Amenity
import com.vastu.propertycore.model.response.RelatedProperty
import com.vastu.realestate.R
import com.vastu.realestate.databinding.RelatedPropertyItemViewBinding
import com.vastu.realestate.utils.CommonUtils
import com.vastu.realestatecore.model.response.PropertyData
import java.util.zip.Inflater

class RelatedPropertyAdapter(private val itemClick: OnItemClickListener,val  context: Context,val relatedProperty:List<RelatedProperty>)
    :RecyclerView.Adapter<RelatedPropertyAdapter.RelatedPropertyViewHolder>() {
    lateinit var binding:RelatedPropertyItemViewBinding

    inner class RelatedPropertyViewHolder(val binding: RelatedPropertyItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(relatedProperty: RelatedProperty ) {
            binding.relatedProperty = relatedProperty
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedPropertyViewHolder {
        binding=RelatedPropertyItemViewBinding.inflate(LayoutInflater.from(context),parent,false)
        return RelatedPropertyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RelatedPropertyViewHolder, position: Int) {
        val relatedProperty =relatedProperty[position]
        holder.bind(relatedProperty)
        CommonUtils.showImageFromURL(
            context,
            relatedProperty.propertyThumbnail,
            holder.binding.imageView,
            R.drawable.load
        )
        holder.binding.title.text=relatedProperty.propertyTitle
        holder.binding.owener.text=relatedProperty.owner
        holder.binding.propertyPrizeTextview.text =
            context.getString(R.string.rupee) + " " + relatedProperty.priceMinWord + "-" + relatedProperty.priceMaxWord
        holder.binding.propertyBhkDtlsTxt.text =
            relatedProperty.bedroom + " " + context.getString(R.string.bhk_apartments)

        holder.binding.cardItem.setOnClickListener {
            itemClick.onItemClick(relatedProperty)
        }
    }
    interface OnItemClickListener {
        fun onItemClick(propertyData: RelatedProperty)
    }

    override fun getItemCount(): Int {
        return relatedProperty.size
    }
}
