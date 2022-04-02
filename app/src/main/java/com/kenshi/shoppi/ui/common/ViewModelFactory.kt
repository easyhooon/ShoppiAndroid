package com.kenshi.shoppi.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kenshi.shoppi.AssetLoader
import com.kenshi.shoppi.data.datasource.CategoryDataSource
import com.kenshi.shoppi.data.datasource.CategoryRemoteDataSource
import com.kenshi.shoppi.data.datasource.HomeAssetDataSource
import com.kenshi.shoppi.data.network.ApiClient
import com.kenshi.shoppi.data.repository.CategoryRepository
import com.kenshi.shoppi.data.repository.HomeRepository
import com.kenshi.shoppi.ui.category.CategoryViewModel
import com.kenshi.shoppi.ui.home.HomeViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    // T: Generic
    // <T : ViewModel> <-- Generic 의 제약사항을 의미, ViewModel 클래스의 서브클래스여야만 한다.
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //isAssignableFrom: Generic type 으로 전달받은 인자의 type 을 알 수 있음
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                //객체를 생성할때 의존 관계가 발생
                //이를 DI 라이브러리를 통해 해결할수 있지만 클래스간의 의존 관계를 설계할 수 있는 것 또한 중요!
                val repository = HomeRepository(HomeAssetDataSource(AssetLoader(context)))
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CategoryViewModel::class.java) -> {
                val repository = CategoryRepository(CategoryRemoteDataSource(ApiClient.create()))
                CategoryViewModel(repository) as T
            }
            else -> {
                //ViewModel 이 아닌 클래스에서 create 메소드를 호출하는 경우
                throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
            }
        }
    }
}