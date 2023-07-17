package com.vastu.realestate.appModule.dashboard.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vastu.offers.model.response.OfferData
import com.vastu.realestate.R
import com.vastu.realestate.databinding.OffersItemViewBinding
import com.vastu.realestate.utils.CommonUtils
import com.vastu.realestate.utils.IRecycleViewClick

class OffersAdapter(private val offersList: List<OfferData>,iRecycleViewClick: IRecycleViewClick):RecyclerView.Adapter<OffersAdapter.OffersViewHolder>() {
    private lateinit var context: Context
    private lateinit var binding: OffersItemViewBinding
    var iRecycleViewClickCopy:IRecycleViewClick = iRecycleViewClick

    class OffersViewHolder(val binding:OffersItemViewBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(offerData: OfferData){
            binding.offers = offerData
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffersViewHolder {
        context = parent.context
        binding = OffersItemViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return OffersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OffersViewHolder, position: Int) {
        val offer = offersList[position]
        holder.bind(offer)
        holder.binding.description.setText(Html.fromHtml(offer.description))
        holder.binding.cvGbpView.setOnClickListener {
            iRecycleViewClickCopy.onClick(offersList[position].property)
        }
        CommonUtils.showImageFromURL(
            context,
            offer.image,
            holder.binding.offerImage,
            R.drawable.vastu_logo_splash
        )
    }

    override fun getItemCount(): Int = offersList.size

}