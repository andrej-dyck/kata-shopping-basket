package ad.kata.shoppingbasket.sales

import ad.kata.shoppingbasket.products.Sku

typealias Price = Euros // 💡 could be own type for multiple currencies or restriction like 'not negative'

class PriceList(
    private val pricesBySku: Map<Sku, Price>
) {
    fun priceFor(sku: Sku): Price? = pricesBySku[sku]
}

fun emptyPriceList() =
    PriceList(emptyMap())

fun priceListFor(vararg items: ItemForSale) =
    PriceList(items.associateBy({ it.sku }, { it.price }))

data class ItemForSale(val sku: Sku, val price: Price)

// convenience functions
fun PriceList.priceForOrThrow(sku: Sku): Price =
    priceFor(sku) ?: throw NoPrice(sku)

class NoPrice(sku: Sku) : Exception("No price for $sku")
