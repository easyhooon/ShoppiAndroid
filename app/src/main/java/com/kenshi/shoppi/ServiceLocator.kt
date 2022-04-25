package com.kenshi.shoppi

import android.content.Context
import androidx.room.Room
import com.kenshi.shoppi.data.database.AppDatabase
import com.kenshi.shoppi.data.datasource.CartItemDataSourceImpl
import com.kenshi.shoppi.data.network.ApiClient
import com.kenshi.shoppi.data.repository.CartRepository

object ServiceLocator {

    private var apiClient: ApiClient? = null
    private var database: AppDatabase? = null
    //두개의 뷰모델에서 사용하고 있는 repository 도 여기서 생성되도록
    private var cartRepository: CartRepository? = null


    // 처음에 한번만 호출, 그 이후 부터는 생성된 apiClient 를 호출 (재사용)
    // 이 구현을 Hilt 를 통해 의존성을 주입하는 것으로 변경 가능
    fun provideApiClient(): ApiClient {
        return apiClient ?: kotlin.run {
            ApiClient.create().also {
                apiClient = it
            }
        }
    }

    private fun provideDatabase(applicationContext: Context): AppDatabase {

        //생성된 적이 있으면 기존의 database 반환
        return database ?: kotlin.run {
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "shoppi-local"
            ).build().also {
                //database 에 instance 를 할당
                database = it
            }
        }
    }

    fun providerCartRepository(context: Context): CartRepository {
        return cartRepository ?: kotlin.run {
            val dao = provideDatabase(context.applicationContext).cartItemDao()
            CartRepository(CartItemDataSourceImpl(dao)).also {
                cartRepository  = it
            }
        }
    }
}