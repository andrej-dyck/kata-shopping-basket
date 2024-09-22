package ad.kata.shoppingbasket

import ad.kata.shoppingbasket.products.Sku
import ad.kata.shoppingbasket.sales.Euros
import ad.kata.shoppingbasket.sales.NoPrice
import ad.kata.shoppingbasket.sales.PriceList
import ad.kata.shoppingbasket.sales.PricedItem
import ad.kata.shoppingbasket.sales.emptyPriceList
import ad.kata.shoppingbasket.sales.tryPriceItem
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

class PriceListTests {

    @Test
    fun `returns a price for known item`() {
        expectThat(
            PriceList(Sku("1") to Euros(2.5))
        ) {
            get { priceFor(Sku("1")) }.isEqualTo(Euros(2.5))
        }
    }

    @Test
    fun `returns nothing for unknown items`() {
        expectThat(
            PriceList(Sku("1") to Euros(2.5))
        ) {
            get { priceFor(Sku("2")) }.isNull()
        }
    }

    @Test
    fun `can price an item (sku + quantity)`() {
        expectThat(
            PriceList(Sku("1") to Euros(2.5))
        ) {
            get { tryPriceItem(Sku("1"), Quantity(2)) }.isEqualTo(
                PricedItem(Sku("1"), skuPrice = Euros(2.5), Quantity(2))
            )
        }
    }

    @Test
    fun `priced items have an item total`() {
        expectThat(
            PricedItem(Sku("1"), skuPrice = Euros(2.5), Quantity(2))
        ) {
            get { itemTotal }.isEqualTo(Euros(5.0))
        }
    }

    @Test
    fun `throw then pricing an item fails`() {
        expectThrows<NoPrice> {
            emptyPriceList().tryPriceItem(Sku("2"))
        }
    }
}
