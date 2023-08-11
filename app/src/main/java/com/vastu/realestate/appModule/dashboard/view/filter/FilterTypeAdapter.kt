package com.vastu.realestate.appModule.dashboard.view.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IFilterTypeClickListener
import com.vastu.realestate.databinding.SortingTypeItemBinding
import com.vastu.realestatecore.model.filter.ObjFilterTypeList

class FilterTypeAdapter(var context :Context, var itemsList: ArrayList<ObjFilterTypeList>, var filterTypeClickListener: IFilterTypeClickListener): RecyclerView.Adapter<FilterTypeAdapter.FilterTypeViewHolder>() {
    var previousHolder: FilterTypeViewHolder? = null
    var selectedItemPosition :Int = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterTypeAdapter.FilterTypeViewHolder {

//        val itemView = LayoutInflater.from(parent.context)
//            .inflate(R.layout.sorting_type_item, parent, false)
//        return FilterTypeHolder(itemView)
        val binding: SortingTypeItemBinding = SortingTypeItemBinding.inflate(LayoutInflater.from(parent.context))

        return FilterTypeViewHolder( binding,binding.root)

    }

    override fun onBindViewHolder(holder: FilterTypeViewHolder, position: Int) {
        val item = itemsList[position]
        previousHolder = holder
        holder.bind(item)
        holder.binding.llFilterTypeLayout.setOnClickListener {
            if (previousHolder != null) {
                previousHolder = null
            }
            previousHolder = holder
            selectedItemPosition = position
            filterTypeClickListener.onFilterItemClickListener(item.filterType)

            notifyDataSetChanged()
        }
        if(selectedItemPosition == position) {
            holder.binding.llFilterTypeLayout.setBackgroundColor(context.resources.getColor(R.color.white))
            holder.binding.itemTextView.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.text_color
                )
            )
        } else {
            holder.binding.itemTextView.setTextColor(ContextCompat.getColor(context,R.color.white))
            holder.binding.llFilterTypeLayout.setBackgroundColor(context.resources.getColor(R.color.button_color))
//            holder.binding.llFilterTypeLayout.background= ContextCompat.getDrawable(context,R.drawable.sort_selected_state)
        }


    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
    class FilterTypeViewHolder(@Nullable val binding: SortingTypeItemBinding, view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ObjFilterTypeList) {

            binding.filterList = item
            binding.executePendingBindings()
        }

    }
}