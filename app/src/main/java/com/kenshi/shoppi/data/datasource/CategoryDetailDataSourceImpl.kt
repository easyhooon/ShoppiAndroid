package com.kenshi.shoppi.data.datasource

import com.kenshi.shoppi.data.model.CategoryDetail
import com.kenshi.shoppi.data.network.ApiClient

class CategoryDetailDataSourceImpl(private val api: ApiClient): CategoryDetailDataSource {
    override suspend fun getCategoryDetail(): CategoryDetail {
        return api.getCategoryDetail()
    }
}