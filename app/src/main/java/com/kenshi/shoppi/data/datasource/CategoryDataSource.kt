package com.kenshi.shoppi.data.datasource

import com.kenshi.shoppi.data.model.Category

interface CategoryDataSource {

    suspend fun getCategories(): List<Category>
}