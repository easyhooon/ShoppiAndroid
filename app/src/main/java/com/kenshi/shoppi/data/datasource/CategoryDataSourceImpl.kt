package com.kenshi.shoppi.data.datasource

import com.kenshi.shoppi.data.model.Category
import com.kenshi.shoppi.data.network.ApiClient

//CategoryDataSource 구현체
class CategoryDataSourceImpl(private val apiClient: ApiClient): CategoryDataSource {
    override suspend fun getCategories(): List<Category> {
        return apiClient.getCategories()
    }
}