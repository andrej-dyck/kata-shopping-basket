package ad.kata.shoppingbasket

@JvmInline
value class Quantity(val units: Int) {
    init {
        require(units >= 0) { "Amount must not be negative" }
        // 💡 an upper limit could also be interesting
    }
}

operator fun Quantity.plus(quantity: Quantity) =
    // 💡 add custom overflow exception with unit < Int.MAX_VALUE - quantity.units
    Quantity(units + quantity.units)

operator fun Quantity.compareTo(quantity: Int) =
    units.compareTo(quantity)
