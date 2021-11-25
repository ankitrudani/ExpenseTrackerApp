package com.ankit.expensetracker

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapter(mCtx : Context, val allTransaction : ArrayList<Transaction>) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>(){
    var mCtx = mCtx

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var tagImage : ImageView
        var transactionName : TextView
        var transactionCategory :TextView
        var transactionAmount : TextView
        var transactionCardView :CardView
        init {
            tagImage = view.findViewById(R.id.tagImage)
            transactionName = view.findViewById(R.id.transactionName)
            transactionCategory = view.findViewById(R.id.transactionCategory)
            transactionAmount = view.findViewById(R.id.transactionAmount)
            transactionCardView = view.findViewById(R.id.transaction_cardview)
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionAdapter.ViewHolder {

        var v = LayoutInflater.from(parent.context).inflate(R.layout.transaction_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: TransactionAdapter.ViewHolder, position: Int) {
        val transaction : Transaction = allTransaction[position]

        holder.transactionName.text = transaction.transactionTitle
        holder.transactionCategory.text = transaction.transactionTag

        when(transaction.transactionType){
            "Income" ->{
                holder.transactionAmount.setTextColor(
                    ContextCompat.getColor(
                    holder.transactionAmount.context,R.color.income)
                )
                holder.transactionAmount.text = "+ ₹"+(transaction.transactionAmount.toString())
            }
            "Expense" ->{
                holder.transactionAmount.setTextColor(
                    ContextCompat.getColor(
                        holder.transactionAmount.context,R.color.expense)
                )
                holder.transactionAmount.text = "- ₹"+(transaction.transactionAmount.toString())
            }
        }

        when(transaction.transactionTag){
            "Housing" ->{
                holder.tagImage.setImageResource(R.drawable.ic_house)
            }
            "Food" ->{
                holder.tagImage.setImageResource(R.drawable.ic_food)
            }
            "Transportation"->{
                holder.tagImage.setImageResource(R.drawable.ic_transportation)
            }
            "Utilities"->{
                holder.tagImage.setImageResource(R.drawable.ic_utilities)
            }
            "Healthcare"->{
                holder.tagImage.setImageResource(R.drawable.ic_healthcare)
            }
            "Personal"->{
                holder.tagImage.setImageResource(R.drawable.ic_healthcare)
            }
            "Entertainment"->{
                holder.tagImage.setImageResource(R.drawable.ic_healthcare)
            }
            else->{
                holder.tagImage.setImageResource(R.drawable.ic_other)
            }
        }

        holder.transactionCardView.setOnClickListener {
            var intent = Intent(mCtx,TransactionDetailsActivity::class.java)
                intent.putExtra("transactionId",transaction.transactionID)
            mCtx.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return allTransaction.size
    }
}