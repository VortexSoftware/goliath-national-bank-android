package com.example.internationalbusinessmen

import androidx.lifecycle.MutableLiveData
import com.example.internationalbusinessmen.Model.Conversion
import com.example.internationalbusinessmen.Model.Transaction
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import java.net.URL

class DataHandler() {

    val liveDataRateConverter: MutableLiveData<RateConverter> = MutableLiveData()
    val liveDataTrasactionHandler: MutableLiveData<TransactionHandler> = MutableLiveData()


    fun downloadData(
         trasactionsUrl: String,
        ratesUrl: String
    ) {
        val rates = RateConverter()
        val transactions = TransactionHandler()

        doAsync {
            downloadSync(rates, ratesUrl)

            downloadSync(transactions, trasactionsUrl)

            onComplete {

                liveDataRateConverter.postValue(rates)
                liveDataTrasactionHandler.postValue(transactions)

            }
        }
    }

    fun downloadSync(rates: RateConverter, url: String) {

        val s: String = URL(url).readText()

        val gsons = s.substring(1, s.length - 1).split("},")

        for (gson in gsons) {
            try {

                rates.addConversion(Gson().fromJson(gson.plus("}"), Conversion::class.java))

            } catch (e: JsonSyntaxException) {
                print(e.cause)
            }
        }

    }

    fun downloadSync(trasactions: TransactionHandler, url: String) {
        val s: String = URL(url).readText()

        val gsons = s.substring(1, s.length - 1).split("},")

        for (gson in gsons) {
            try {

                trasactions.addTransaction(Gson().fromJson(gson.plus("}"), Transaction::class.java))

            } catch (e: JsonSyntaxException) {
                print(e.cause)
            }
        }

    }

    fun generateData(trasactions: TransactionHandler, data: String) {

        val collectionType = object : TypeToken<List<Transaction>>() {}.type

        val lcs = Gson().fromJson(data, collectionType) as List<Transaction>

        for (transaction in lcs) {

            trasactions.addTransaction(transaction)

        }
    }
}
