package ad.kata.shoppingbasket

import ad.kata.shoppingbasket.products.Sku
import ad.kata.shoppingbasket.sales.Buy1Get1Free
import ad.kata.shoppingbasket.sales.Euros
import ad.kata.shoppingbasket.sales.Percent
import ad.kata.shoppingbasket.sales.PercentOff
import ad.kata.shoppingbasket.sales.PriceList
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class AcceptanceTests {

    @Test
    fun `10 percent off deal`() {
        val priceList = PriceList(
            Sku("A0001") to Euros(12.99),
            Sku("A0002") to Euros(3.99)
        )
        val deals = listOf(
            PercentOff(Sku("A0001"), Percent(10)),
        )

        expectThat(
            Basket()
                .withItem(Sku("A0002"))
                .withItem(Sku("A0001"))
                .withItem(Sku("A0002"))
        ) {
            get { total(priceList, deals) }.isEqualTo(Euros(19.67))
        }
    }

    @Test
    fun `buy 1 get 1 free`() {
        val priceList = PriceList(
            Sku("A0001") to Euros(12.99),
            Sku("A0002") to Euros(3.99)
        )
        val deals = listOf(
            Buy1Get1Free(Sku("A0002")),
        )

        expectThat(
            Basket()
                .withItem(Sku("A0002"))
                .withItem(Sku("A0001"))
                .withItem(Sku("A0002"))
        ) {
            get { total(priceList, deals) }.isEqualTo(Euros(16.98))
        }
    }

    @Test
    fun `multiple deals`() {
        val priceList = PriceList(
            Sku("A0001") to Euros(12.99),
            Sku("A0002") to Euros(3.99)
        )
        val deals = listOf(
            PercentOff(Sku("A0001"), Percent(10)),
            Buy1Get1Free(Sku("A0002")),
        )

        expectThat(
            Basket()
                .withItem(Sku("A0002"))
                .withItem(Sku("A0001"))
                .withItem(Sku("A0002"))
        ) {
            get { total(priceList, deals) }.isEqualTo(Euros(15.68))
        }
    }
}
