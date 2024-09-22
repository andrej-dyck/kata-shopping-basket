package ad.kata.shoppingbasket

import ad.kata.shoppingbasket.products.Sku
import ad.kata.shoppingbasket.sales.Buy1Get1Free
import ad.kata.shoppingbasket.sales.Discount
import ad.kata.shoppingbasket.sales.Euros
import ad.kata.shoppingbasket.sales.PricedItem
import ad.kata.shoppingbasket.sales.pricedItemsOf
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

class Buy1Get1FreeTests {

    @Test
    fun `no discount when items do not contain the product`() {
        expectThat(
            Buy1Get1Free(Sku("1"))
        ) {
            get { discountFor(emptyMap()) }.isNull()
        }
    }

    @Test
    fun `no discount when product item has only quantity less then 2`() {
        val items = pricedItemsOf(PricedItem(Sku("1"), skuPrice = Euros(1.0)))

        expectThat(
            Buy1Get1Free(Sku("1"))
        ) {
            get { discountFor(items) }.isNull()
        }
    }

    @Test
    fun `returns discount of 1 product price if item's quantity is 2`() {
        val items = pricedItemsOf(PricedItem(Sku("1"), skuPrice = Euros(1.0), Quantity(2)))

        expectThat(
            Buy1Get1Free(Sku("1"))
        ) {
            get { discountFor(items) }.isEqualTo(
                Discount(Sku("1"), reduction = Euros(1.0))
            )
        }
    }

    @Test
    fun `returns discount of 1 product price if item's quantity is 3`() {
        val items = pricedItemsOf(PricedItem(Sku("1"), skuPrice = Euros(1.0), Quantity(3)))

        expectThat(
            Buy1Get1Free(Sku("1"))
        ) {
            get { discountFor(items) }.isEqualTo(
                Discount(Sku("1"), reduction = Euros(1.0))
            )
        }
    }

    @Test
    fun `returns discount of 2 products price if item's quantity is 4`() {
        val items = pricedItemsOf(PricedItem(Sku("1"), skuPrice = Euros(1.0), Quantity(4)))

        expectThat(
            Buy1Get1Free(Sku("1"))
        ) {
            get { discountFor(items) }.isEqualTo(
                Discount(Sku("1"), reduction = Euros(2.0))
            )
        }
    }

    @Test
    fun `returns discount of 2 products price if item's quantity is 5`() {
        val items = pricedItemsOf(PricedItem(Sku("1"), skuPrice = Euros(1.0), Quantity(5)))

        expectThat(
            Buy1Get1Free(Sku("1"))
        ) {
            get { discountFor(items) }.isEqualTo(
                Discount(Sku("1"), reduction = Euros(2.0))
            )
        }
    }
}
