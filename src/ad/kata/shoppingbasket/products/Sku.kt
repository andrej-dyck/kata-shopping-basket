package ad.kata.shoppingbasket.products

/** Sku identifies a product */
@JvmInline
value class Sku(val value: String) {
    init {
        require(value.isNotBlank())
    }
}
