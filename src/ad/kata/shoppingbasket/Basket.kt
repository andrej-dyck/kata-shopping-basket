package ad.kata.shoppingbasket

import ad.kata.shoppingbasket.products.Sku

class Basket(
    private val items: Map<Sku, Quantity> = emptyMap()
) {
    fun withItem(sku: Sku, quantity: Quantity = Quantity(1)): Basket {
        val newQuantity = (items[sku] ?: Quantity(0)) + quantity
        return Basket(items + (sku to newQuantity)) // 🤯 `Map.plus()` preserves insert order
    }

    fun basketItems() = items.map { BasketItem(it.key, it.value) }
}

data class BasketItem(val sku: Sku, val quantity: Quantity = Quantity(1))

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
