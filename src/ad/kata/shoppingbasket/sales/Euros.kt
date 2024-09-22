package ad.kata.shoppingbasket.sales

import kotlin.math.round

@JvmInline
value class Euros(val cents: Long) {

    constructor(euros: Double) : this(round(euros * 100).toLong())

    override fun toString() = "${cents / 100.0} €"
}

operator fun Euros.times(factor: Int) =
    Euros(cents = cents * factor)

operator fun Euros.times(factor: Double) =
    Euros(cents = round(cents * factor).toLong())

fun Collection<Euros>.sum() =
    Euros(cents = sumOf { it.cents })
