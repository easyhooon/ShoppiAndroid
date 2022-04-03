package com.kenshi.shoppi.data.datasource

import android.util.Log
import com.google.gson.Gson
import com.kenshi.shoppi.AssetLoader
import com.kenshi.shoppi.data.model.HomeData

class HomeDataSourceImpl(private val assetLoader: AssetLoader) : HomeDataSource {

    private val gson = Gson()

    override fun getHomeData(): HomeData? {
        // context 를 전달해야하는 문제 -> 생성자에 AssetLoader 를 추가하는 것으로 해결
//        val homeJsonString = assetLoader.getJsonString("home.json")
//        return gson.fromJson(homeJsonString, HomeData::class.java)

        return assetLoader.getJsonString("home.json")?.let { homeJsonString ->
            gson.fromJson(homeJsonString, HomeData::class.java)
        }
    }
}