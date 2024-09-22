package ad.kata.shoppingbasket

import ad.kata.shoppingbasket.products.Sku
import ad.kata.shoppingbasket.sales.Euros
import ad.kata.shoppingbasket.sales.ItemForSale
import ad.kata.shoppingbasket.sales.priceListFor
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import kotlin.test.Ignore

class AcceptanceTests {

    @Test
    @Ignore
    fun `10 percent off deal`() {
        priceListFor(
            ItemForSale(Sku("A0001"), price = Euros(12.99)),
            ItemForSale(Sku("A0002"), price = Euros(3.99))
        )
        // deals 10% off for A0001

        expectThat(
            Basket()
                .withItem(Sku("A0002"))
                .withItem(Sku("A0001"))
                .withItem(Sku("A0002"))
        )
        // basket.total
        // => 19.67
    }

    @Test
    @Ignore
    fun `buy 1 get 1 free`() {
        priceListFor(
            ItemForSale(Sku("A0001"), price = Euros(12.99)),
            ItemForSale(Sku("A0002"), price = Euros(3.99))
        )
        // deals Buy1Get1Free for A0002

        expectThat(
            Basket()
                .withItem(Sku("A0002"))
                .withItem(Sku("A0001"))
                .withItem(Sku("A0002"))
        )
        // basket.total
        // => 16.98
    }

    @Test
    @Ignore
    fun `multiple deals`() {
        priceListFor(
            ItemForSale(Sku("A0001"), price = Euros(12.99)),
            ItemForSale(Sku("A0002"), price = Euros(3.99))
        )
        // deals 10% off for A0001
        // deals Buy1Get1Free for A0002

        expectThat(
            Basket()
                .withItem(Sku("A0002"))
                .withItem(Sku("A0001"))
                .withItem(Sku("A0002"))
        )
        // basket.total
        // => 15.68
    }
}
