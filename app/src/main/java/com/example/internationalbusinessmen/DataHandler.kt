package com.example.internationalbusinessmen

import androidx.lifecycle.MutableLiveData
import com.example.internationalbusinessmen.Model.Conversion
import com.example.internationalbusinessmen.Model.Transaction
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import java.math.BigDecimal


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

    fun downloadJson(url: String): String? {

        val request = Request.Builder()
            .addHeader("Accept", "application/json")
            .url(url)
            .build()

        return OkHttpClient().newCall(request).execute().body?.string()

    }

    fun downloadSync(rates: RateConverter, url: String) {

        var s = downloadJson(url)

        val gsons = s?.substring(1, s.length-2)?.split("},")

        if (gsons != null) {
            for (gson in gsons) {
                try {

                    rates.addConversion(Gson().fromJson(gson.plus("}"), Conversion::class.java))

                } catch (e: JsonSyntaxException) {
                    print(e.cause)
                }
            }
        }

    }

    fun downloadSync(trasactions: TransactionHandler, url: String) {

        val s = downloadJson(url)

        val gsons = s?.substring(1, s.length-2)?.split("},")

        if (gsons != null) {
            for (gson in gsons) {
                try {

                    trasactions.addTransaction(Gson().fromJson(gson.plus("}"), Transaction::class.java))

                } catch (e: JsonSyntaxException) {
                    print(e.cause)
                }
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

