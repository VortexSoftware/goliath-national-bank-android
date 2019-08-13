package com.example.internationalbusinessmen

import androidx.lifecycle.MutableLiveData
import com.example.internationalbusinessmen.Model.Transaction
import org.jetbrains.anko.doAsync
import java.math.BigDecimal
import kotlin.collections.ArrayList

class TransactionHandler {
    internal val transactions = ArrayList<Transaction>()
    private val items = ArrayList<String>()

    val liveDataItemSku: MutableLiveData<List<Transaction>> = MutableLiveData()


    constructor()

    fun Refresh(transactionHandler: TransactionHandler) {

        this.transactions.clear()
        this.transactions.addAll(transactionHandler.transactions)

        genItemsList()

    }


    fun init() {

        genItemsList()

    }


    fun genItemsList() {

        items.clear()

        for (transaction in transactions) {
            if (!items.contains(transaction.sku)) {
                items.add(transaction.sku)
            }
        }

    }

    fun getItemsList(): ArrayList<String> {

        return items

    }


    fun getTransactionsInRate(itemSku: String, exchange: String, rateConverter: RateConverter) {

        doAsync {
            var transactionsFiltered = transactions.filter { it.sku.equals(itemSku) }

            var total = BigDecimal(0)

            for (transaction in transactionsFiltered) {

                total += transaction.amount.multiply(BigDecimal(rateConverter.rate(transaction.currency, exchange)))
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN)

            }

            transactionsFiltered = transactionsFiltered.plus(
                Transaction(
                    "Total",
                    total,
                    exchange
                )
            )

            liveDataItemSku.postValue(transactionsFiltered)
        }
    }

    fun getTransactionsInRateTest(itemSku: String, exchange: String, rateConverter: RateConverter): List<Transaction> {

        var transactionsFiltered = transactions.filter { it.sku.equals(itemSku) }

        var total = BigDecimal(0)

        for (transaction in transactionsFiltered) {

            total += transaction.amount.multiply(BigDecimal(rateConverter.rate(transaction.currency, exchange)))
                .setScale(2, BigDecimal.ROUND_HALF_EVEN)

        }

        transactionsFiltered = transactionsFiltered.plus(
            Transaction(
                "Total",
                total,
                exchange
            )
        )

        return transactionsFiltered
    }

    fun getTransactionsSize(): Int {
        return transactions.size
    }


    fun addTransaction(transaction: Transaction) {
        transactions.add(transaction)
    }

    override fun toString(): String {
        return "TransactionHandler(transactions=$transactions, items=$items)"
    }


}