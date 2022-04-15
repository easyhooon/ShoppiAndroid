package com.kenshi.shoppi.data.datasource

import com.kenshi.shoppi.data.model.Product
import com.kenshi.shoppi.data.network.ApiClient

interface ProductDetailDataSource {

    suspend fun getProductDetail(productId: String): Product

}