package ad.kata.shoppingbasket

import ad.kata.shoppingbasket.products.Sku
import ad.kata.shoppingbasket.sales.Buy1Get1Free
import ad.kata.shoppingbasket.sales.Euros
import ad.kata.shoppingbasket.sales.PriceList
import ad.kata.shoppingbasket.sales.Percent
import ad.kata.shoppingbasket.sales.PercentOff
import ad.kata.shoppingbasket.sales.emptyPriceList
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo

class BasketTotalTests {

    @Test
    fun `is 0 for an empty basket`() {
        expectThat(
            Basket()
        ) {
            get { total(emptyPriceList()) }.isEqualTo(Euros(0))
        }
    }

    @Test
    fun `total looks up prices for items`() {
        val priceList = PriceList(
            Sku("1") to Euros(2.5)
        )

        expectThat(
            Basket().withItem(Sku("1"))
        ) {
            get { total(priceList) }.isEqualTo(Euros(2.5))
        }
    }

    @Test
    fun `item price is listing price times amount`() {
        val priceList = PriceList(
            Sku("1") to Euros(2.5)
        )

        expectThat(
            Basket().withItem(Sku("1"), Quantity(2))
        ) {
            get { total(priceList) }.isEqualTo(Euros(5.0))
        }
    }

    @Test
    fun `total sums up all priced items`() {
        val priceList = PriceList(
            Sku("1") to Euros(0.3),
            Sku("2") to Euros(0.2)
        )

        expectThat(
            Basket().withItem(Sku("1")).withItem(Sku("2"))
        ) {
            get { total(priceList) }.isEqualTo(Euros(0.5))
        }
    }

    @Test
    fun `total throws when no price is found`() {
        expectThrows<Exception> {
            Basket().withItem(Sku("1")).total(emptyPriceList())
        }
    }

    @Test
    fun `total applies given deal`() {
        val priceList = PriceList(
            Sku("1") to Euros(2.5)
        )
        val deals = listOf(
            PercentOff(Sku("1"), Percent(10))
        )

        expectThat(
            Basket().withItem(Sku("1"), Quantity(2))
        ) {
            get { total(priceList, deals) }.isEqualTo(Euros(4.5))
        }
    }

    @Test
    fun `total applies given multiple deals`() {
        val priceList = PriceList(
            Sku("1") to Euros(2.5),
            Sku("2") to Euros(4.2)
        )
        val deals = listOf(
            PercentOff(Sku("1"), Percent(10)),
            Buy1Get1Free(Sku("2"))
        )

        expectThat(
            Basket()
                .withItem(Sku("1"), Quantity(2))
                .withItem(Sku("2"), Quantity(2))
        ) {
            get { total(priceList, deals) }.isEqualTo(Euros(8.7))
        }
    }
}
