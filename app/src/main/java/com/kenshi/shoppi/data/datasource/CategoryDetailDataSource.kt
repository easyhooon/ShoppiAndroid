package com.kenshi.shoppi.data.datasource

import com.kenshi.shoppi.data.model.CategoryDetail

interface CategoryDetailDataSource {

    suspend fun getCategoryDetail(): CategoryDetail
}