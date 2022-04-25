package com.kenshi.shoppi.data.repository

import com.kenshi.shoppi.data.datasource.CartItemDataSource
import com.kenshi.shoppi.data.model.CartItem
import com.kenshi.shoppi.data.model.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//dataSource 에게 data 를 요청
class CartRepository(
    private val localDataSource: CartItemDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getCartItems(): List<CartItem> {
        // 이 시점에 thread 를 변경
        // 이 getCartItems 라는 메소드는 뷰모델에서 호출됨
        // 뷰모델에서는 ui thread 로 메소드를 호출하게 됨 이후 data 를 받아와 ui 갱신처리까지
        // 데이터를 요청하는 이 부분만 io thread 로 변경
        return withContext(ioDispatcher) {
            localDataSource.getCartItem()
        }
    }

    suspend fun addCartItem(product: Product) {
        withContext(ioDispatcher) {
            val cartItem = CartItem(
                productId = product.productId,
                label = product.label,
                price = product.price,
                brandName = product.brandName ?: "",
                thumbnailImageUrl = product.thumbnailImageUrl,
                type = product.type ?: "",
                amount = 1
            )
            localDataSource.addCartItem(cartItem)
        }
    }
}