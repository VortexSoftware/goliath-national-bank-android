package com.example.internationalbusinessmen.Model

import java.math.BigDecimal

class Transaction {

    var sku: String = ""
    var amount = BigDecimal("0.00")
    var currency: String = ""

    constructor(sku: String, amount: BigDecimal, currency: String) {
        this.sku = sku
        this.amount = amount
        this.currency = currency
    }

    override fun toString(): String {
        return "Transaction(sku='$sku', amount=$amount, currency='$currency')"
    }


}

//[{"sku":"D9255","amount":"31.2","currency":"AUD"}