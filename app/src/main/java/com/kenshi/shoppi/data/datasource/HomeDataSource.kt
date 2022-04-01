package com.kenshi.shoppi.data.datasource

import com.kenshi.shoppi.data.model.HomeData

interface HomeDataSource {

    fun getHomeData(): HomeData?

}