package com.example.internationalbusinessmen

import org.junit.Test

import org.junit.Assert.*

class DataHandlerTests {

    @Test
    fun getRates() {

        var rates = RateConverter()

        DataHandler.downloadSync(rates,"http://quiet-stone-2094.herokuapp.com/rates.json")

        assertNotEquals(0,rates.getRatesSize())


    }

    @Test
    fun getTransactions() {

        var transactions = TransactionHandler()

        DataHandler.downloadSync(transactions,"http://quiet-stone-2094.herokuapp.com/transactions.json")

        assertNotEquals(0,transactions.getTransactionsSize())


    }


}
