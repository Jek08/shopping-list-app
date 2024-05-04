package dev.jakapw.shoppinglist.data.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.jakapw.shoppinglist.data.persistence.dao.ProductDao
import dev.jakapw.shoppinglist.data.persistence.entity.ProductListItem

@Database(entities = [ProductListItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        private lateinit var instance: AppDatabase

        fun getInstance(applicationContext: Context): AppDatabase {
            if (::instance.isInitialized) {
                instance = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java,
                    "db_shopping_list"
                ).build()
            }
            return instance
        }
    }
}