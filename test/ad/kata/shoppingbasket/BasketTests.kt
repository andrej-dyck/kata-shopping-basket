package ad.kata.shoppingbasket

import ad.kata.shoppingbasket.products.Sku
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEmpty

class BasketTests {

    @Test
    fun `empty basket has no items`() {
        expectThat(Basket()) {
            get { basketItems() }.isEmpty()
        }
    }

    @Test
    fun `an item can be added by sku`() {
        expectThat(
            Basket().withItem(Sku("1"))
        ) {
            get { basketItems() }.containsExactly(
                BasketItem(Sku("1"))
            )
        }
    }

    @Test
    fun `an item can be added by sku with amount`() {
        expectThat(
            Basket().withItem(Sku("1"), Quantity(2))
        ) {
            get { basketItems() }.containsExactly(
                BasketItem(Sku("1"), Quantity(2))
            )
        }
    }

    @Test
    fun `preserves insert order of multiple items`() {
        expectThat(
            Basket()
                .withItem(Sku("1"), Quantity(2))
                .withItem(Sku("2"))
                .withItem(Sku("3"))
        ) {
            get { basketItems() }.containsExactly(
                BasketItem(Sku("1"), Quantity(2)),
                BasketItem(Sku("2")),
                BasketItem(Sku("3"))
            )
        }
    }

    @Test
    fun `aggregates items by sku`() {
        expectThat(
            Basket()
                .withItem(Sku("1"))
                .withItem(Sku("2"))
                .withItem(Sku("1"), Quantity(3))
                .withItem(Sku("3"))
                .withItem(Sku("2"), Quantity(2))
        ) {
            get { basketItems() }.containsExactly(
                BasketItem(Sku("1"), Quantity(4)),
                BasketItem(Sku("2"), Quantity(3)),
                BasketItem(Sku("3"))
            )
        }
    }
}
