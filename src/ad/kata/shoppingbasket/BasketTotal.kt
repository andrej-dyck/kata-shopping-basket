package ad.kata.shoppingbasket

import ad.kata.shoppingbasket.sales.Euros
import ad.kata.shoppingbasket.sales.PriceList
import ad.kata.shoppingbasket.sales.priceForOrThrow
import ad.kata.shoppingbasket.sales.sum
import ad.kata.shoppingbasket.sales.times

fun Basket.total(priceList: PriceList): Euros =
    basketItems()
        .associateWith { priceList.priceForOrThrow(it.sku) }
        .map { (item, price) -> price * item.quantity.units }
        .sum()
