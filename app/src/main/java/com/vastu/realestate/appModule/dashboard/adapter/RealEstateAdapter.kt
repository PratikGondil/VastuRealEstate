package com.vastu.realestate.appModule.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vastu.realestate.appModule.dashboard.model.RealEstate
import com.vastu.realestate.databinding.RealEstateItemviewBinding


class RealEstateAdapter(private val realEstateList: List<RealEstate>
) : RecyclerView.Adapter<RealEstateViewHolder>() {

    private lateinit var binding: RealEstateItemviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RealEstateViewHolder {
        binding = RealEstateItemviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RealEstateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RealEstateViewHolder, position: Int) {
        val largeNews = realEstateList[position]
        holder.bind(largeNews)
    }

    override fun getItemCount(): Int = realEstateList.size

}
    class RealEstateViewHolder(private val binding: RealEstateItemviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(realEstate: RealEstate) {
            binding.realestate = realEstate
        }
    }

