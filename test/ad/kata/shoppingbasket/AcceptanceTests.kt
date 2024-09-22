package ad.kata.shoppingbasket

import ad.kata.shoppingbasket.products.Sku
import ad.kata.shoppingbasket.sales.Buy1Get1Free
import ad.kata.shoppingbasket.sales.Euros
import ad.kata.shoppingbasket.sales.ItemForSale
import ad.kata.shoppingbasket.sales.Percent
import ad.kata.shoppingbasket.sales.PercentOff
import ad.kata.shoppingbasket.sales.priceListFor
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.test.Ignore

class AcceptanceTests {

    @Test
    @Ignore
    fun `10 percent off deal`() {
        val priceList = priceListFor(
            ItemForSale(Sku("A0001"), price = Euros(12.99)),
            ItemForSale(Sku("A0002"), price = Euros(3.99))
        )
        // deals
        listOf(
            PercentOff(Sku("A0001"), Percent(10)),
        )

        expectThat(
            Basket()
                .withItem(Sku("A0002"))
                .withItem(Sku("A0001"))
                .withItem(Sku("A0002"))
        ) {
            get { total(priceList) }.isEqualTo(Euros(19.67))
        }
    }

    @Test
    @Ignore
    fun `buy 1 get 1 free`() {
        val priceList = priceListFor(
            ItemForSale(Sku("A0001"), price = Euros(12.99)),
            ItemForSale(Sku("A0002"), price = Euros(3.99))
        )
        // deals
        listOf(
            Buy1Get1Free(Sku("A0002")),
        )

        expectThat(
            Basket()
                .withItem(Sku("A0002"))
                .withItem(Sku("A0001"))
                .withItem(Sku("A0002"))
        ) {
            get { total(priceList) }.isEqualTo(Euros(16.98))
        }
    }

    @Test
    @Ignore
    fun `multiple deals`() {
        val priceList = priceListFor(
            ItemForSale(Sku("A0001"), price = Euros(12.99)),
            ItemForSale(Sku("A0002"), price = Euros(3.99))
        )
        // deals
        listOf(
            PercentOff(Sku("A0001"), Percent(10)),
            Buy1Get1Free(Sku("A0002")),
        )

        expectThat(
            Basket()
                .withItem(Sku("A0002"))
                .withItem(Sku("A0001"))
                .withItem(Sku("A0002"))
        ) {
            get { total(priceList) }.isEqualTo(Euros(15.68))
        }
    }
}
