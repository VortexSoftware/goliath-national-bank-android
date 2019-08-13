package com.example.internationalbusinessmen.Dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.internationalbusinessmen.Adapters.AdapterTransactions
import com.example.internationalbusinessmen.Model.Transaction
import com.example.internationalbusinessmen.R
import kotlinx.android.synthetic.main.dialog_transactions.view.*

class DialogTransactions constructor(var route: List<Transaction>) : DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_transactions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.recyclerViewTransactions.layoutManager = LinearLayoutManager(context)

        view.textView_sku_title.text = String.format(resources.getString(R.string.title_sku), route.first().sku)

        view.textViewTotal.text =
            String.format(resources.getString(R.string.total_sku), route.last().amount, route.last().currency)

        view.recyclerViewTransactions.adapter =
            context?.let {
                AdapterTransactions(
                    route.minusElement(route.last()),
                    it
                )
            }

        view.button_close.setOnClickListener { dismiss() }

    }

}