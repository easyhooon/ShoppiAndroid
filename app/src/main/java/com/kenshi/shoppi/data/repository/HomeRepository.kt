package com.kenshi.shoppi.data.repository

import com.kenshi.shoppi.data.datasource.HomeDataSourceImpl
import com.kenshi.shoppi.data.model.HomeData

// Home 화면에서 보여질 데이터를 관리
class HomeRepository(
    private val dataSourceImpl: HomeDataSourceImpl
) {

    fun getHomeData(): HomeData? {
        return dataSourceImpl.getHomeData()
    }

}