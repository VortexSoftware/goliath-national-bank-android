package com.example.internationalbusinessmen.Model



class Conversion{
    var from: String = "nameRateIn"
    var to: String = "nameRateOut"
    var rate: Double = 1.00

    constructor(rateIn: String, rateOut: String, rate: Double) {
        this.from = rateIn
        this.to = rateOut
        this.rate = rate
    }

    override fun toString(): String {
        return "Conversion(from='$from', to='$to', rate=$rate)"
    }


}
