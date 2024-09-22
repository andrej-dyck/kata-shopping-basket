package ad.kata.shoppingbasket

import ad.kata.shoppingbasket.products.Sku
import ad.kata.shoppingbasket.sales.Euros
import ad.kata.shoppingbasket.sales.PriceList
import ad.kata.shoppingbasket.sales.sumOf
import ad.kata.shoppingbasket.sales.tryPriceItem

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

// total
fun Basket.total(priceList: PriceList): Euros =
    basketItems()
        .map { priceList.tryPriceItem(it.sku, it.quantity) }
        .sumOf { it.itemTotal }
