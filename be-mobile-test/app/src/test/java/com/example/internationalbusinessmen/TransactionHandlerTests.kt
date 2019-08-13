package com.example.internationalbusinessmen

import com.example.internationalbusinessmen.Model.Conversion
import com.example.internationalbusinessmen.Model.Transaction
import org.junit.Test

import org.junit.Assert.*
import java.math.BigDecimal

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


        return conv
    }

    fun initializeTransaction(): TransactionHandler {

        var transactionHandler = TransactionHandler()

        transactionHandler.addTransaction(Transaction("Y1903", BigDecimal("50.5"), "AUD"))
        transactionHandler.addTransaction(Transaction("Y1902", BigDecimal("50.5"), "EUR"))
        transactionHandler.addTransaction(Transaction("Y1903", BigDecimal("500.5"), "USD"))


        return transactionHandler

    }

    @Test
    fun getFiltered() {

        var converter = initializeRate()

        var transactions = initializeTransaction()

        var transactionFiltered = transactions.getTransactionsInRateTest("Y1903", "EUR", converter)


        assertEquals(
            "[Transaction(sku='Y1903', amount=50.5, currency='AUD'), Transaction(sku='Y1903', amount=1000.0, currency='EUR'), Transaction(sku='Total', amount=1505.0, currency='EUR')]",
            transactionFiltered.toString()
        )

    }

}
