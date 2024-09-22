package ad.kata.shoppingbasket.sales

import ad.kata.shoppingbasket.Quantity
import ad.kata.shoppingbasket.compareTo
import ad.kata.shoppingbasket.plus
import ad.kata.shoppingbasket.products.Sku
import kotlin.collections.ifEmpty
import kotlin.collections.reduce

interface Deal {
    fun discountFor(items: Collection<PricedItem>): Discount?
}

data class PricedItem(val sku: Sku, val skuPrice: Price, val quantity: Quantity = Quantity(1))

data class Discount(val sku: Sku, val discountInEuros: Euros)

// existing deals
class Buy1Get1Free(private val sku: Sku) : Deal {

    override fun discountFor(items: Collection<PricedItem>): Discount? {
        val item = items.reduceToSingleItem(sku)

        return if (item == null || item.quantity < 2) null
        else Discount(sku, item.skuPrice * (item.quantity.units / 2))
    }
}

private fun Collection<PricedItem>.reduceToSingleItem(sku: Sku): PricedItem? =
    filter { it.sku == sku }
        .ifEmpty { null }
        ?.reduce { item1, item2 ->
            require(item1.skuPrice == item2.skuPrice)
            PricedItem(sku, item1.skuPrice, quantity = item1.quantity + item2.quantity)
        }

class PercentOff(private val sku: Sku, private val percent: Percent) : Deal {

    override fun discountFor(items: Collection<PricedItem>): Discount? =
        items.filter { it.sku == sku }
            .map { Discount(sku, discountInEuros = it.skuPrice * it.quantity.units * percent.toFraction()) }
            .ifEmpty { null }
            ?.reduce { d1, d2 -> Discount(sku, discountInEuros = d1.discountInEuros + d2.discountInEuros) }
}

@JvmInline
value class Percent(val value: Int) {
    init {
        require(value in (0..100)) { "Percentage must be in between 0% and 100%; was $value" }
    }
}

fun Percent.toFraction() = value / 100.0
