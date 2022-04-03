package com.kenshi.shoppi.data.repository

import com.kenshi.shoppi.data.datasource.CategoryDataSourceImpl
import com.kenshi.shoppi.data.model.Category

//DataSource 에게 Data 를 요청
class CategoryRepository(
    private val dataSourceImpl: CategoryDataSourceImpl
) {
//    suspend fun getCategories(): List<Category> = withContext(Dispatchers.IO) {
//        dataSourceImpl.getCategories()
//    }
//    return dataSourceImpl.getCategories()
    // 위와 같은 구현을 retrofit 라이브러리 내부에서 이미 해주고 있음, 컨텍스트를 바꿔줄 필요 x
    //CoroutineScope 안에서만 실행을 강제하도록 suspend 키워드를 붙혀주기만 하면 됨 
    suspend fun getCategories(): List<Category>  {
        return dataSourceImpl.getCategories()
    }

}