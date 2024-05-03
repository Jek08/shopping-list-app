package dev.jakapw.shoppinglist.viewmodel;

import androidx.lifecycle.ViewModel
import dev.jakapw.shoppinglist.data.ProductListUIState
import dev.jakapw.shoppinglist.data.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

public class ProductViewModel() : ViewModel() {

    private val _productList: MutableStateFlow<MutableList<Product>> by lazy {
        MutableStateFlow(ProductListUIState().shoppingList)
    }
    val productList: StateFlow<MutableList<Product>> = _productList.asStateFlow()

    fun addProduct(product: Product) {
        val newProductList = _productList.value
        newProductList.add(product)
    }
}
