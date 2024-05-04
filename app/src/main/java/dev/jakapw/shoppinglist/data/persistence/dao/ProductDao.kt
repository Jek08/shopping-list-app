package dev.jakapw.shoppinglist.data.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.jakapw.shoppinglist.data.persistence.entity.ProductListItem

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProduct(productListItem: ProductListItem)

    @Delete
    fun deleteProduct(productListItem: ProductListItem)

    @Query("SELECT * FROM product_list")
    fun getAll(): List<ProductListItem>

    @Query("SELECT * FROM product_list WHERE product_id = :productId")
    fun getProductInfo(productId: String): ProductListItem
}