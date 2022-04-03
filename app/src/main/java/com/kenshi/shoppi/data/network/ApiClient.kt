package com.kenshi.shoppi.data.network

import com.kenshi.shoppi.BuildConfig
import com.kenshi.shoppi.data.model.Category
import com.kenshi.shoppi.data.model.CategoryDetail
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiClient {

    @GET("categories.json")
    suspend fun getCategories(): List<Category>

//    //확장성을 위한
//    @GET("{categoryId}.json")
//    suspend fun getCategoryDetail(@Path("categoryId") categoryId: String): CategoryDetail

    //확장성을 위한
    @GET("fashion_female.json")
    suspend fun getCategoryDetail(): CategoryDetail

    companion object {
        //Api Client 객체 생성 방법 정의
        fun create(): ApiClient {

            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }

            val client = OkHttpClient.Builder()
                //네트워크 응답이 도착할때 출력되는 메세지의 포맷을 변경할 수 있음
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.FIREBASE_BASE_URL)
                .client(client)
                //http 응답의 결과를 프로젝트에서 사용하는 객체로 변환하는 방법을 정의
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiClient::class.java)
        }
    }
}