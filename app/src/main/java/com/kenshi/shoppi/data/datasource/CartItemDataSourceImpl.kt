package com.kenshi.shoppi.data.datasource

import com.kenshi.shoppi.data.database.CartItemDao
import com.kenshi.shoppi.data.model.CartItem

class CartItemDataSourceImpl(
    private val dao: CartItemDao
) : CartItemDataSource {

    override suspend fun getCartItem(): List<CartItem> {
        return dao.load()
    }

    override suspend fun addCartItem(cartItem: CartItem) {
        dao.insert(cartItem)
    }
}