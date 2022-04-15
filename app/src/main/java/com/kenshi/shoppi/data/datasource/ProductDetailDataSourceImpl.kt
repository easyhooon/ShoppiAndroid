package com.kenshi.shoppi.data.datasource

import com.kenshi.shoppi.data.model.Product
import com.kenshi.shoppi.data.network.ApiClient

class ProductDetailDataSourceImpl(
    private val api: ApiClient
) : ProductDetailDataSource {
    override suspend fun getProductDetail(productId: String): Product {
        return api.getProductDetail(productId)
    }

}