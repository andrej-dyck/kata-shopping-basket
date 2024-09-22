package ad.kata.shoppingbasket.sales

import ad.kata.shoppingbasket.Quantity
import ad.kata.shoppingbasket.products.Sku

typealias Price = Euros // 💡 could be own type for multiple currencies or restriction like 'not negative'

class PriceList(
    private val pricesBySku: Map<Sku, Price>
) {
    internal constructor(vararg items: Pair<Sku, Price>) : this(items.toMap())

    fun priceFor(sku: Sku): Price? = pricesBySku[sku]
}

fun emptyPriceList() = PriceList()

// pricing items
fun PriceList.tryPriceItem(sku: Sku, quantity: Quantity = Quantity(1)) =
    PricedItem(sku, skuPrice = priceFor(sku) ?: throw NoPrice(sku), quantity)

data class PricedItem(
    val sku: Sku,
    val skuPrice: Price,
    val quantity: Quantity = Quantity(1)
) {
    val itemTotal: Price by lazy { skuPrice * quantity.units }
}

class NoPrice(sku: Sku) : Exception("No price for $sku")
