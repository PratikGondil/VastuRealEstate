package com.vastu.realestate.appModule.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vastu.loanenquirycore.model.response.interest.loan.LoanInterstedData
import com.vastu.realestate.R
import com.vastu.realestate.databinding.LoanItemViewBinding

class LoanAdapter(private val itemClick:OnItemClickListener, private val loanList: List<LoanInterstedData>
) : RecyclerView.Adapter<LoanViewHolder>() {
    private lateinit var context: Context
    private lateinit var binding: LoanItemViewBinding
    private var selectedItemPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        context = parent.context
        binding = LoanItemViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return LoanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        val loan = loanList[position]
        holder.bind(loan)
            holder.binding.loanLayout.setOnClickListener {
                selectedItemPosition = position
                itemClick.onItemClick(loan)
                notifyDataSetChanged()
            }
        if (selectedItemPosition == position)
            holder.binding.loanLayout.setBackgroundResource(R.drawable.item_selection_background)
        else
            holder.binding.loanLayout.setBackgroundResource(R.drawable.itemview_background)
    }

    override fun getItemCount(): Int = loanList.size
}

    interface OnItemClickListener{
        fun onItemClick(loanData: LoanInterstedData)
    }

     class LoanViewHolder(val binding:LoanItemViewBinding):RecyclerView.ViewHolder(binding.root){
       fun bind(loanData: LoanInterstedData){
            binding.loanIntersetedData = loanData
        }
    }
