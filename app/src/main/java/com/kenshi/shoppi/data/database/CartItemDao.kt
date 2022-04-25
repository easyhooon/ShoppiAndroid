package com.kenshi.shoppi.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kenshi.shoppi.data.model.CartItem

@Dao
interface CartItemDao {

    @Query("SELECT * FROM cart_item")
    suspend fun load(): List<CartItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItem: CartItem)
}