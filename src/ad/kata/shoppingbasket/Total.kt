package ad.kata.shoppingbasket

import ad.kata.shoppingbasket.sales.Deal
import ad.kata.shoppingbasket.sales.Discount
import ad.kata.shoppingbasket.sales.Euros
import ad.kata.shoppingbasket.sales.PriceList
import ad.kata.shoppingbasket.sales.PricedItem
import ad.kata.shoppingbasket.sales.minus
import ad.kata.shoppingbasket.sales.sumOf
import ad.kata.shoppingbasket.sales.tryPriceItem

fun Basket.total(priceList: PriceList, activeDeals: Collection<Deal> = emptyList()): Euros {
    val pricedItems = basketItems().tryPriceItems(priceList)
    val discounts = pricedItems.eligibleDiscounts(activeDeals)

    return pricedItems.sumOf { it.itemTotal } - discounts.sumOf { it.reduction }
}

private fun Iterable<BasketItem>.tryPriceItems(priceList: PriceList) =
    this.map { priceList.tryPriceItem(it.sku, it.quantity) }

private fun Iterable<PricedItem>.eligibleDiscounts(deals: Collection<Deal>): Collection<Discount> {
    if (deals.isEmpty()) return emptyList()

    val pricedItems = this.associateBy { it.sku } // 👈 assumes priced items are mapped basketItems() with unique SKUs
    return deals.mapNotNull { it.discountFor(pricedItems) }
}
