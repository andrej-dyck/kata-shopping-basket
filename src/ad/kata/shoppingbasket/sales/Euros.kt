package ad.kata.shoppingbasket.sales

import kotlin.math.round

@JvmInline
value class Euros(val cents: Long) {

    constructor(euros: Double) : this(round(euros * 100).toLong())

    override fun toString() = "${cents / 100.0} €"
}
