package com.example.internationalbusinessmen.Model

class Conversion(rateIn: String, rateOut: String, var rate: Double) {
    var from: String = rateIn
    var to: String = rateOut

    override fun toString(): String {
        return "Conversion(from='$from', to='$to', rate=$rate)"
    }


}
