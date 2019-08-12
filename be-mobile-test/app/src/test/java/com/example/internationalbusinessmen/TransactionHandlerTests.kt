package com.example.internationalbusinessmen

import com.example.internationalbusinessmen.Model.Conversion
import org.junit.Test

import org.junit.Assert.*

class TransactionHandlerTests {

    fun initializeRate(): RateConverter {
        val convUC = Conversion("USD", "EUR", 10.00)
        val convCU = Conversion("EUR", "USD", 10.00)
        val convUR = Conversion("EUR", "AUD", 10.00)
        val convRU = Conversion("AUD", "EUR", 10.00)
        val convCA = Conversion("CAD", "EUR", 10.00)
        val convAC = Conversion("EUR", "CAD", 10.00)

        val conv = RateConverter()

        conv.addConversion(convUC)
        conv.addConversion(convCU)
        conv.addConversion(convUR)
        conv.addConversion(convRU)
        conv.addConversion(convCA)
        conv.addConversion(convAC)

        conv.generateRates()

       // conv.

        return conv
    }

    fun initializeTransaction(): TransactionHandler{

        var transactionHandler = TransactionHandler()

        DataHandler.generateData(transactionHandler,"[{\"sku\":\"Y1903\",\"amount\":\"50.5\",\"currency\":\"AUD\"},{\"sku\":\"D9255\",\"amount\":\"32.9\",\"currency\":\"AUD\"},{\"sku\":\"Y1903\",\"amount\":\"1000\",\"currency\":\"EUR\"}]")

        return transactionHandler

    }

    @Test
    fun getFiltered() {

        var converter = initializeRate()

        var transactions = initializeTransaction()

        var transactionFiltered = transactions.getTransactionsInRate("Y1903","EUR",converter)


        assertEquals("[Transaction(sku='Y1903', amount=50.5, currency='AUD'), Transaction(sku='Y1903', amount=1000.0, currency='EUR'), Transaction(sku='Total', amount=1505.0, currency='EUR')]", transactionFiltered.toString())


    }


}
