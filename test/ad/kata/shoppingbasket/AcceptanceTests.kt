package ad.kata.shoppingbasket

import ad.kata.shoppingbasket.products.Sku
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import kotlin.test.Ignore

class AcceptanceTests {

    @Test
    @Ignore
    fun `10 percent off deal`() {
        // price list [["A0001", 12.99], ["A0002", 3.99], ...]
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
        // price list [["A0001", 12.99], ["A0002", 3.99], ...]
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
        // price list [["A0001", 12.99], ["A0002", 3.99], ...]
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
