package ad.kata.shoppingbasket.sales

import kotlin.math.round

@JvmInline
value class Euros(val cents: Long) {

    constructor(euros: Double) : this(round(euros * 100).toLong())

    override fun toString() = "${cents / 100.0} €"
}

operator fun Euros.plus(euros: Euros) =
    Euros(cents = cents + euros.cents)

operator fun Euros.times(factor: Int) =
    Euros(cents = cents * factor)

operator fun Euros.times(factor: Double) =
    Euros(cents = round(cents * factor).toLong())

inline fun <T> Iterable<T>.sumOf(selector: (T) -> Euros): Euros =
    // 🤯 as Euros is compiled to Long, the extension method sumOf(selector: (T) -> Long)
    // has the same signature, and thus, cannot be used here
    Euros(cents = fold(0) { cents, e -> cents + selector(e).cents })
