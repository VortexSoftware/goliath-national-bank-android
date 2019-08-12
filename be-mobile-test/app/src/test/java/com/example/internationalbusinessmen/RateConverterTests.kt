package com.example.internationalbusinessmen

import com.example.internationalbusinessmen.Model.Conversion
import org.junit.Test

import org.junit.Assert.*

class RateConverterTests {

    fun generate(): RateConverter {

        val conv = RateConverter()

        for (i in 100..140 step 2) {

            var coinOne: String = i.toString()
            var coinTwo: String = (i+1).toString()
            var exchange: Double = 0.5 + Math.random()

            conv.addConversion(Conversion(coinOne, coinTwo, exchange))
            conv.addConversion(Conversion(coinTwo, coinOne, 1 / exchange))
        }

        return conv

    }

    fun initialize(): RateConverter {
        val convUC = Conversion("USD", "CAD", 2.00)
        val convCU = Conversion("CAD", "USD", 0.50)
        val convUR = Conversion("USD", "RAB", 4.00)
        val convRU = Conversion("RAB", "USD", 0.25)
        val convCA = Conversion("CAD", "ARS", 10.00)
        val convAC = Conversion("ARS", "CAD", 0.10)

        val conv = RateConverter()

        conv.addConversion(convUC)
        conv.addConversion(convCU)
        conv.addConversion(convUR)
        conv.addConversion(convRU)
        conv.addConversion(convCA)
        conv.addConversion(convAC)

        //  DataHandler.downloadSync(conv)

        return conv
    }

    @Test
    fun currency_list() {

        val conv = initialize()

        assertEquals(" USD CAD RAB ARS", conv.printCurrency())
    }

    @Test
    fun conversion_given() {

        val conv = initialize()

        assertEquals(2.00, conv.rate("USD", "CAD"), 0.0)

    }

    @Test
    fun conversion_not_given_2() {

        val conv = initialize()

        val rate = Conversion("USD", "ARS", 20.00)

        assertEquals(rate.toString(), conv.createRate("USD", "ARS", 1.0, 5).toString())

    }

    @Test
    fun conversion_not_given_3() {

        val conv = initialize()

        val rate = Conversion("RAB", "ARS", 5.0)

        assertEquals(rate.toString(), conv.createRate("RAB", "ARS", 1.0, 5).toString())

    }

    @Test
    fun conversion_not_given_4() {

        val conv = initialize()

        val rate = Conversion("USD", "NONE", -1.0)

        assertEquals(rate.toString(), conv.createRate("USD", "NONE", 1.0, 5).toString())

    }

    @Test
    fun generate_missing() {

        val conv = initialize()

        conv.generateRates()

        val result = "Conversion(from='USD', to='CAD', rate=2.0)\n" +
                "Conversion(from='CAD', to='USD', rate=0.5)\n" +
                "Conversion(from='USD', to='RAB', rate=4.0)\n" +
                "Conversion(from='RAB', to='USD', rate=0.25)\n" +
                "Conversion(from='CAD', to='ARS', rate=10.0)\n" +
                "Conversion(from='ARS', to='CAD', rate=0.1)\n" +
                "Conversion(from='USD', to='ARS', rate=20.0)\n" +
                "Conversion(from='CAD', to='RAB', rate=2.0)\n" +
                "Conversion(from='RAB', to='CAD', rate=0.5)\n" +
                "Conversion(from='RAB', to='ARS', rate=5.0)\n" +
                "Conversion(from='ARS', to='USD', rate=0.05)\n" +
                "Conversion(from='ARS', to='RAB', rate=0.2)\n"

        assertEquals(result, conv.exchanges())

    }

    @Test
    fun generate_missing_random() {

        val conv = generate()

        conv.generateRates()

        val result = ""

        assertEquals(1722, conv.sizeOfExchanges())

    }


}
