package com.example.internationalbusinessmen

import android.animation.Animator
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.internationalbusinessmen.Adapters.AdapterItems
import com.example.internationalbusinessmen.Dialogs.DialogTransactions
import com.example.internationalbusinessmen.Model.Transaction
import com.example.internationalbusinessmen.ViewModel.BankVM
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*

class ScrollingActivity : AppCompatActivity() {

    var bankVM = BankVM()
    var bundle = Bundle()
    var fragmentTransaction = supportFragmentManager.beginTransaction()
    var priorInstance = supportFragmentManager.findFragmentByTag("transaction")
    var adapterItems = AdapterItems(ArrayList(),this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Created by VORTEX SOFTWARE - 2019", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val urlTransactions = getString(R.string.url_transactions)
        val urlRates = getString(R.string.url_rates)


        SuscribeDialog(bankVM.transactionHandler)

        SuscribeRates(bankVM.dataHandler)

        SuscribeTransactions(bankVM.dataHandler)

        SubscribeToVM(bankVM)

        SubscribeAdapter(adapterItems)

        bankVM.downloadData(urlTransactions,urlRates)

        recyclerTransactions.layoutManager = LinearLayoutManager(this)

        recyclerTransactions.layoutManager = GridLayoutManager(this, 2)

        recyclerTransactions.adapter = adapterItems

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item)
    }


    fun downloadFinished(transactionHandler: TransactionHandler) {

        if(!layoutDownloading.visibility.equals(View.GONE)){
            textView_downloading.text = getString(R.string.done)


            layoutDownloading.animate().alpha(0F).translationY(
                (layoutDownloading.height).toFloat()
            ).setDuration(1000).setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {

                    layoutDownloading.visibility = View.GONE

                }
            })

        }

        adapterItems.refreshData(transactionHandler.getItemsList())


    }


    private fun SubscribeToVM(viewModel: BankVM) {

        val transactionObserver = Observer<TransactionHandler> {

            downloadFinished(it)

        }
        viewModel.liveDataTransaction.observe(this, transactionObserver)
    }

    private fun SubscribeAdapter(adapter: AdapterItems) {

        val itemClick = Observer<String> {

            bankVM.getTransactionsFiltered(it)

        }
        adapter.liveDataItemSku.observe(this, itemClick)
    }



    private fun SuscribeDialog(transactionHandler: TransactionHandler) {

        val dialogCreate = Observer<List<Transaction>> {

            var dialogFragment = DialogTransactions(it)

            dialogFragment.setArguments(bundle)

            fragmentTransaction = supportFragmentManager.beginTransaction()

            if (priorInstance != null) {
                fragmentTransaction.remove(priorInstance!!)
            }

            fragmentTransaction.addToBackStack(null)

            dialogFragment.show(fragmentTransaction, "transaction")

        }
        transactionHandler.liveDataItemSku.observe(this, dialogCreate)
    }

    private fun SuscribeRates(dataHandler: DataHandler) {

        val suscribeRates = Observer<RateConverter> {

            bankVM.updateRates(it)

        }
        dataHandler.liveDataRateConverter.observe(this,suscribeRates)
    }

    private fun SuscribeTransactions(dataHandler: DataHandler) {

        val suscribeTransactions = Observer<TransactionHandler> {

            bankVM.updateTransactions(it)

        }
        dataHandler.liveDataTrasactionHandler.observe(this,suscribeTransactions)
    }

}
