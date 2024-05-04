package dev.jakapw.shoppinglist

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.jakapw.shoppinglist.data.Product
import dev.jakapw.shoppinglist.data.persistence.AppDatabase
import dev.jakapw.shoppinglist.data.persistence.dao.ProductDao
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ProductDatabaseTest {

    private lateinit var productDao: ProductDao
    private lateinit var productDB: AppDatabase

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        productDB = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        productDao = productDB.productDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        productDB.close()
    }

    @Test
    fun afterSaveProduct_mustGetSavedProduct() {
        val product = Product("Detergen", 40000)
        productDao.saveProduct(product.toProductListItem())
        val savedProduct = productDao.getAll()
            .stream()
            .filter { it -> it.productName == product.productName }
            .findFirst()
            .get()
        Log.i("TestResult", "Saved Product: $savedProduct")
        Assert.assertEquals(product.productName, Product(savedProduct).productName)
    }
}