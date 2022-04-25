package com.kenshi.shoppi.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class CartProduct
//CartHeader 가 CartProduct 를 상속받아서 CartProduct 타입이 됨
data class CartHeader(
    val brandName: String
): CartProduct()

@Entity(
    tableName = "cart_item"
)
data class CartItem(
    @PrimaryKey
    @ColumnInfo(name = "product_id")
    val productId: String,
    val label: String,
    val price: Int,
    @ColumnInfo(name = "band_name")
    val brandName: String,
    @ColumnInfo(name = "thumbnail_image_url")
    val thumbnailImageUrl: String,
    val type: String,
    val amount: Int
): CartProduct()