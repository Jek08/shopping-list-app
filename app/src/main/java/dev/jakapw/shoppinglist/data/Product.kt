package dev.jakapw.shoppinglist.data

import dev.jakapw.shoppinglist.data.persistence.entity.ProductListItem
import java.util.UUID

class Product(
    val productName: String,
    val price: Long
) {

    constructor(pli : ProductListItem) : this(
        productName = pli.productName,
        price = pli.price
    )

    fun toProductListItem(): ProductListItem {
        return ProductListItem(UUID.randomUUID().toString(), productName, price)
    }

    fun toProductListItem(id: UUID): ProductListItem {
        return ProductListItem(id.toString(), productName, price)
    }

    override fun toString(): String {
        return "Product(productName='$productName', price=$price)"
    }
}
