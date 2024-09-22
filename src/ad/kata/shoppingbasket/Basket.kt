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
