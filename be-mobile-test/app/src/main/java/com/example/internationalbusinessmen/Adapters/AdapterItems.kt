package com.example.internationalbusinessmen.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.listitem_sku.view.*
import com.example.internationalbusinessmen.R
import com.example.internationalbusinessmen.ScrollingActivity


class AdapterItems(val items: ArrayList<String>, val context: Context) : RecyclerView.Adapter<AdapterItems.ViewHolder>() {

    val liveDataItemSku: MutableLiveData<String> = MutableLiveData()

    fun refreshData(list:ArrayList<String>){

        items.clear()

        items.addAll(list)

        this.notifyDataSetChanged()

    }

    // Gets the number of elements in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var holder = ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.listitem_sku,
                parent,
                false
            )
        )

        return holder

    }

    // Binds each text in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.transactionSKU.text = items.get(position)

        holder.layout_item.setOnClickListener(View.OnClickListener {

            liveDataItemSku.postValue(items.get(position))

        })

    }



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each transaction to
        val transactionSKU = view.textView_Sku
        val layout_item = view.layout_item
    }
}

