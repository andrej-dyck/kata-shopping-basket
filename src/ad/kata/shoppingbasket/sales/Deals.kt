package ad.kata.shoppingbasket.sales

import ad.kata.shoppingbasket.compareTo
import ad.kata.shoppingbasket.products.Sku

interface Deal {
    fun discountFor(items: Map<Sku, PricedItem>): Discount?
}

data class Discount(val sku: Sku, val reduction: Euros)

// convenience functions
internal fun pricedItemsOf(vararg items: PricedItem) =
    items.associateBy { it.sku }

// existing deals
class Buy1Get1Free(private val sku: Sku) : Deal {

    override fun discountFor(items: Map<Sku, PricedItem>): Discount? {
        val eligibleItem = items[sku]?.takeIf { it.quantity >= 2 }
            ?: return noDiscount

        return Discount(eligibleItem.sku, reduction = eligibleItem.skuPrice * (eligibleItem.quantity.units / 2))
    }
}

class PercentOff(private val sku: Sku, private val percent: Percent) : Deal {

    override fun discountFor(items: Map<Sku, PricedItem>): Discount? {
        val eligibleItem = items[sku]
            ?: return noDiscount

        return Discount(eligibleItem.sku, reduction = eligibleItem.itemTotal * percent.toFraction())
    }
}

private val noDiscount: Discount? = null

@JvmInline
value class Percent(val value: Int) {
    init {
        require(value in (0..100)) { "Percentage must be in between 0% and 100%; was $value" }
    }
}

fun Percent.toFraction() = value / 100.0
