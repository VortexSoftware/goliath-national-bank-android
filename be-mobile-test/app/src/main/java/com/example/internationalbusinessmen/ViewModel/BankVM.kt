package com.example.internationalbusinessmen.ViewModel

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.internationalbusinessmen.DataHandler
import com.example.internationalbusinessmen.RateConverter
import com.example.internationalbusinessmen.TransactionHandler

class BankVM(){

    var rateConverter = RateConverter()
    val transactionHandler = TransactionHandler()
    val liveDataTransaction: MutableLiveData<TransactionHandler> = MutableLiveData()
    val dataHandler = DataHandler()



    fun downloadData(urlRates: String, urlTransactions: String) {

        dataHandler.downloadData(urlRates, urlTransactions)

    }



    fun updateRates(rateConverter: RateConverter) {

        this.rateConverter = rateConverter

        rateConverter.generateRates()

        rateConverter.genExchanges()

    }

    fun updateTransactions(transactionHandler: TransactionHandler) {

        this.transactionHandler.Refresh(transactionHandler)

        liveDataTransaction.postValue(this.transactionHandler)

    }


    fun getTransactionsFiltered(filter: String){

        transactionHandler.getTransactionsInRate(filter,"EUR",rateConverter)

    }



}