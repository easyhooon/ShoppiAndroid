package com.kenshi.shoppi.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kenshi.shoppi.data.model.CartItem

@Database(entities = [CartItem::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun cartItemDao(): CartItemDao
}