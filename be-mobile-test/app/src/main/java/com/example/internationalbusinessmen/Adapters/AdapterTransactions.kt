package com.example.internationalbusinessmen.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.listitem_transaction.view.*
import android.view.animation.AnimationUtils
import com.example.internationalbusinessmen.R
import com.example.internationalbusinessmen.Model.Transaction


class AdapterTransactions(val items: List<Transaction>, val context: Context) :
    RecyclerView.Adapter<AdapterTransactions.ViewHolder>() {

    var lastPosition = -1

    // Gets the number of elements in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.listitem_transaction,
                parent,
                false
            )
        )

    }

    // Binds each text in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.transactionCurrency.text = items.get(position).currency

        holder.transactionNumber.text = position.toString()

        holder.transactionAmount.text = items.get(position).amount.toString()

        val animation = AnimationUtils.loadAnimation(
            context, R.anim.item_animation_fall_down
        )

        holder.itemView.startAnimation(animation)
        lastPosition = position


    }


    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each transaction to
        val transactionCurrency = view.textView_currency
        val transactionAmount = view.textView_amount
        val transactionNumber = view.textView_number
    }
}

