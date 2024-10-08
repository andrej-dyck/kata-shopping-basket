package ad.kata.shoppingbasket

import ad.kata.shoppingbasket.products.Sku
import ad.kata.shoppingbasket.sales.Discount
import ad.kata.shoppingbasket.sales.Euros
import ad.kata.shoppingbasket.sales.Percent
import ad.kata.shoppingbasket.sales.PercentOff
import ad.kata.shoppingbasket.sales.PricedItem
import ad.kata.shoppingbasket.sales.pricedItemsOf
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

class PercentOffTests {

    @Test
    fun `no discount when items do not contain the product`() {
        expectThat(
            PercentOff(Sku("1"), Percent(10))
        ) {
            get { discountFor(emptyMap()) }.isNull()
        }
    }

    @Test
    fun `zero discount when percentage is 0`() {
        val items = pricedItemsOf(PricedItem(Sku("1"), skuPrice = Euros(1.0)))

        expectThat(
            PercentOff(Sku("1"), Percent(0))
        ) {
            get { discountFor(items) }.isEqualTo(
                Discount(Sku("1"), reduction = Euros(0))
            )
        }
    }

    @Test
    fun `percent off discount accounts for quantity`() {
        val items = pricedItemsOf(PricedItem(Sku("1"), skuPrice = Euros(1.0), Quantity(5)))

        expectThat(
            PercentOff(Sku("1"), Percent(10))
        ) {
            get { discountFor(items) }.isEqualTo(
                Discount(Sku("1"), reduction = Euros(0.5))
            )
        }
    }

    @Test
    fun `10 percent off discount`() {
        val items = pricedItemsOf(PricedItem(Sku("1"), skuPrice = Euros(1.0)))

        expectThat(
            PercentOff(Sku("1"), Percent(10))
        ) {
            get { discountFor(items) }.isEqualTo(
                Discount(Sku("1"), reduction = Euros(0.1))
            )
        }
    }

    @Test
    fun `100 percent off discount`() {
        val items = pricedItemsOf(PricedItem(Sku("1"), skuPrice = Euros(1.0)))

        expectThat(
            PercentOff(Sku("1"), Percent(100))
        ) {
            get { discountFor(items) }.isEqualTo(
                Discount(Sku("1"), reduction = Euros(1.0))
            )
        }
    }
}
