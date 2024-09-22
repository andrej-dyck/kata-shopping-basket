package ad.kata.shoppingbasket

import ad.kata.shoppingbasket.products.Sku
import ad.kata.shoppingbasket.sales.Euros
import ad.kata.shoppingbasket.sales.ItemForSale
import ad.kata.shoppingbasket.sales.emptyPriceList
import ad.kata.shoppingbasket.sales.priceForOrThrow
import ad.kata.shoppingbasket.sales.priceListFor
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

class PriceListTests {

    @Test
    fun `returns a price for known item`() {
        expectThat(
            priceListFor(ItemForSale(Sku("1"), price = Euros(2.5)))
        ) {
            get { priceFor(Sku("1")) }.isEqualTo(Euros(2.5))
        }
    }

    @Test
    fun `returns nothing for unknown items`() {
        expectThat(
            priceListFor(ItemForSale(Sku("1"), price = Euros(2.5)))
        ) {
            get { priceFor(Sku("2")) }.isNull()
        }
    }

    @Test
    fun `can throw for unknown items with convenience function`() {
        expectThrows<Exception> {
            emptyPriceList().priceForOrThrow(Sku("2"))
        }
    }
}
