package com.kenshi.shoppi.data.repository

import com.kenshi.shoppi.data.datasource.ProductDetailDataSourceImpl
import com.kenshi.shoppi.data.model.Product

class ProductDetailRepository(
    private val dataSourceImpl: ProductDetailDataSourceImpl
) {

    suspend fun getProductDetail(productId: String): Product {
        return dataSourceImpl.getProductDetail(productId)
    }

}