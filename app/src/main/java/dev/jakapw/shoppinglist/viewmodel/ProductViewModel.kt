package dev.jakapw.shoppinglist.viewmodel;

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import dev.jakapw.shoppinglist.data.Product
import dev.jakapw.shoppinglist.data.ProductListUIState
import dev.jakapw.shoppinglist.data.persistence.AppDatabase
import dev.jakapw.shoppinglist.data.persistence.dao.ProductDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

public class ProductViewModel() : ViewModel() {

    private val _productList: MutableStateFlow<MutableList<Product>> by lazy {
        MutableStateFlow(ProductListUIState().shoppingList)
    }
    lateinit var productDao: ProductDao
    lateinit var savedStateHandle: SavedStateHandle
    val productList: StateFlow<List<Product>> = _productList.asStateFlow()

    constructor(applicationContext: Context, savedStateHandle: SavedStateHandle) : this() {
        productDao = AppDatabase.getInstance(applicationContext).productDao()
        this.savedStateHandle = savedStateHandle
    }

    fun addProduct(product: Product) {
        val newProductList = _productList.value
        productDao.saveProduct(product.toProductListItem())
        newProductList.add(product)
    }

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                // get the application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                val savedStateHandle = extras.createSavedStateHandle()

                return ProductViewModel(
                    application.applicationContext,
                    savedStateHandle
                ) as T
            }
        }
    }
}
