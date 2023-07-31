package com.vastu.realestate.appModule.dashboard.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vastu.loanenquirycore.model.response.interest.loan.LoanInterstedData
import com.vastu.realestate.R
import com.vastu.realestate.databinding.LoanItemViewBinding

class LoanAdapter(private val itemClick:OnItemClickListener,
                  private val loanList: List<LoanInterstedData>,
) : RecyclerView.Adapter<LoanViewHolder>() {
    private lateinit var context: Context
    private lateinit var binding: LoanItemViewBinding
    private var selectedItemPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        context = parent.context
        binding = LoanItemViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return LoanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoanViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val loan = loanList[position]
        holder.bind(loan,context)
            holder.binding.loanLayout.setOnClickListener {
                selectedItemPosition = position
                itemClick.onItemClick(loan)
                notifyDataSetChanged()
            }
//        if (selectedItemPosition == position)
//            holder.binding.loanLayout.setBackgroundResource(R.drawable.item_selection_background)
//        else
//            holder.binding.loanLayout.setBackgroundResource(R.drawable.itemview_background)
    }

    override fun getItemCount(): Int = loanList.size
}

    interface OnItemClickListener{
        fun onItemClick(loanData: LoanInterstedData)
    }

     class LoanViewHolder(val binding:LoanItemViewBinding):RecyclerView.ViewHolder(binding.root){
       fun bind(loanData: LoanInterstedData,context: Context) {
           when(loanData.loanName?.uppercase()){
               (context.getString(R.string.personal_loan).uppercase())-> binding.ivTransactionIcon.setImageDrawable(context.getDrawable(R.drawable.ic_personal))
               (context.getString(R.string.business_loan).uppercase())-> binding.ivTransactionIcon.setImageDrawable(context.getDrawable(R.drawable.ic_business))
               (context.getString(R.string.auto_loan).uppercase())-> binding.ivTransactionIcon.setImageDrawable(context.getDrawable(R.drawable.ic_auto_loan))
               (context.getString(R.string.commercial_loan).uppercase())-> binding.ivTransactionIcon.setImageDrawable(context.getDrawable(R.drawable.ic_commercial))
               (context.getString(R.string.plot_purchase_loan).uppercase())-> binding.ivTransactionIcon.setImageDrawable(context.getDrawable(R.drawable.ic_plot_p))
               (context.getString(R.string.construction_loan).uppercase())-> binding.ivTransactionIcon.setImageDrawable(context.getDrawable(R.drawable.ic_plot_purchase))
               (context.getString(R.string.loan_against_property).uppercase())-> binding.ivTransactionIcon.setImageDrawable(context.getDrawable(R.drawable.ic_loan_against_1))
               (context.getString(R.string.home_loan).uppercase())-> binding.ivTransactionIcon.setImageDrawable(context.getDrawable(R.drawable.ic_home_loan))
               (context.getString(R.string.overdraft_loan).uppercase())-> binding.ivTransactionIcon.setImageDrawable(context.getDrawable(R.drawable.ic_overdraft__))
               (context.getString(R.string.term_loan).uppercase())-> binding.ivTransactionIcon.setImageDrawable(context.getDrawable(R.drawable.ic_term_loan))
               (context.getString(R.string.project_loan).uppercase())-> binding.ivTransactionIcon.setImageDrawable(context.getDrawable(R.drawable.ic_project_loan))
               (context.getString(R.string.loan_against).uppercase())-> binding.ivTransactionIcon.setImageDrawable(context.getDrawable(R.drawable.ic_loan_against_1))

           }
           binding.loanIntersetedData = loanData

       }}

