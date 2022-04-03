package com.kenshi.shoppi.data.repository

import com.kenshi.shoppi.data.datasource.CategoryDetailDataSourceImpl
import com.kenshi.shoppi.data.model.CategoryDetail

class CategoryDetailRepository(
    private val datasourceImpl: CategoryDetailDataSourceImpl
) {

    suspend fun getCategoryDetail(): CategoryDetail {
        return datasourceImpl.getCategoryDetail()
    }
}