package com.kenshi.shoppi.data.datasource

import com.kenshi.shoppi.data.model.CartItem

interface CartItemDataSource {

    // database 에서 data 를 요청하는 것이기 때문에 시간이 오래걸릴 수 있음, main Thread 에서 실행하면 안됨
    // 코루틴 스코프에서 실행하도록 강제 하기 위해 suspend keyword
    suspend fun getCartItem(): List<CartItem>

    suspend fun addCartItem(cartItem: CartItem)
}