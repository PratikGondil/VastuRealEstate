package com.vastu.realestate.appModule.enquirylist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vastu.enquiry.loan.model.response.LoanData
import com.vastu.realestate.R
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IAssignLeadListener
import com.vastu.realestate.databinding.LoanEnquiryItemviewBinding

class LoanEnquiryAdapter(private val loanDataList :List<LoanData>,var iAssignLeadListener: IAssignLeadListener):
    RecyclerView.Adapter<LoanEnquiryViewHolder>() {
    private lateinit var context: Context

    private lateinit var binding: LoanEnquiryItemviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanEnquiryViewHolder {
       context = parent.context
       binding = LoanEnquiryItemviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       return LoanEnquiryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoanEnquiryViewHolder, position: Int) {
        val loan = loanDataList[position]
        holder.bind(loan)
        binding.loanNameTextview.text = context.getString(R.string.username,loan.firstName,loan.middleName,loan.lastName)
        holder.binding.btnAssignLead.setOnClickListener {
            iAssignLeadListener.assignLoanLeadToEmployee(loan)

        }

    }

    override fun getItemCount(): Int = loanDataList.size
    }

    class LoanEnquiryViewHolder(val binding: LoanEnquiryItemviewBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(loanData: LoanData){
               binding.loanData = loanData
            }
    }

