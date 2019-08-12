package com.example.internationalbusinessmen

import com.example.internationalbusinessmen.Model.Conversion
import java.util.ArrayList


class RateConverter {

    private var conversions = ArrayList<Conversion>()
    private val exchanges = ArrayList<String>()

    constructor(conversions: ArrayList<Conversion>) {
        this.conversions = conversions
    }

    constructor()

    fun getRatesSize(): Int {

        return exchanges.size

    }

    fun genExchanges(){
        for (exchange in conversions) {
            if (!exchanges.contains(exchange.from)) {
                exchanges.add(exchange.from)
            }
        }
    }


    fun setConversions(conversions: ArrayList<Conversion>) {
        this.conversions = conversions
        for (exchange in conversions) {
            if (!exchanges.contains(exchange.from)) {
                exchanges.add(exchange.from)
            }
        }
    }

    fun addConversion(conversion: Conversion) {
        conversions.add(conversion)
        if (!exchanges.contains(conversion.from)) {
            exchanges.add(conversion.from)
        }
    }

    fun printCurrency(): String {

        var currencies = ""

        for (exchange in exchanges) {
            currencies = currencies + " " + exchange;
        }

        return currencies
    }

    fun rate(currencyIn: String, currencyOut: String): Double {
        if (currencyIn.equals(currencyOut)) {
            return 1.00;
        }

        val res = conversions.filter { it.from.equals(currencyIn) and it.to.equals(currencyOut) }

        if (!res.isEmpty()) {
            return res.get(0).rate
        }

        return -1.00
    }


    fun createRate(`in`: String, out: String, oldRate: Double, maxPath: Int): Conversion {

        val roots = ArrayList<Conversion>()
        var newRate: Double

        newRate = rate(`in`, out)

        if (newRate != -1.0) {
            return Conversion(`in`, out, newRate * oldRate)
        }



        for (conv in conversions.filter { it.from.equals(`in`) and !it.to.equals(`in`) }) {
            roots.add(conv)
        }

        for (root in roots) {

            var rootRate = root.rate

            newRate = rate(root.to, out)

            if (newRate != -1.0) {

                newRate *= rootRate

                return Conversion(`in`, out, newRate)
            }

        }

        if (maxPath > 0) {

            for (root in roots) {

                var solution = createRate(root.to, out, root.rate, maxPath - 1)

                var rootRate = root.rate

                if (solution.rate != -1.0) {

                    solution.rate *= rootRate

                    solution.from = `in`

                    return solution

                }

            }
        }

        return Conversion(`in`, out, -1.00)
    }


    fun generateRates() {

        if(!exchanges.isEmpty()){

            for (from in exchanges) {
                for (to in exchanges) {

                    if (!from.equals(to) and (rate(from, to) == -1.0)) {

                        addConversion(createRate(from, to, 1.0, 5))

                    }
                }
            }
        }

    }

    fun sizeOfExchanges(): Int {
        return conversions.size
    }

    fun exchanges(): String {
        var convertionString = ""
        for (cov in conversions) {
            convertionString += cov.toString() + "\n"
        }
        return convertionString
    }

    override fun toString(): String {
        return "RateConverter(conversions=$conversions, exchanges=$exchanges)"
    }


}