package dev.jakapw.shoppinglist.data

data class ProductListUIState(
    val shoppingList: MutableList<Product> = mutableListOf(
        Product("Sabun Cair", 30000)
    )
)