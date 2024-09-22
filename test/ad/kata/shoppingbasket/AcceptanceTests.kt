package ad.kata.shoppingbasket

import org.junit.jupiter.api.Test
import kotlin.test.Ignore

class AcceptanceTests {

    @Test
    @Ignore
    fun `10 percent off deal`() {
        // price list [["A0001", 12.99], ["A0002", 3.99], ...]
        // deals 10% off for A0001

        // basket = Basket.new
        // basket.scan("A0002")
        // basket.scan("A0001")
        // basket.scan("A0002")
        // basket.total
        // => 19.67
    }

    @Test
    @Ignore
    fun `buy 1 get 1 free`() {
        // price list [["A0001", 12.99], ["A0002", 3.99], ...]
        // deals Buy1Get1Free for A0002

        // basket = Basket.new
        // basket.scan("A0002")
        // basket.scan("A0001")
        // basket.scan("A0002")
        // basket.total
        // => 16.98
    }

    @Test
    @Ignore
    fun `multiple deals`() {
        // price list [["A0001", 12.99], ["A0002", 3.99], ...]
        // deals 10% off for A0001
        // deals Buy1Get1Free for A0002

        // basket = Basket.new
        // basket.scan("A0002")
        // basket.scan("A0001")
        // basket.scan("A0002")
        // basket.total
        // => 15.68
    }
}
