package com.kenshi.shoppi.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kenshi.shoppi.AssetLoader
import com.kenshi.shoppi.data.datasource.CategoryDataSourceImpl
import com.kenshi.shoppi.data.datasource.CategoryDetailDataSourceImpl
import com.kenshi.shoppi.data.datasource.HomeDataSourceImpl
import com.kenshi.shoppi.data.datasource.ProductDetailDataSourceImpl
import com.kenshi.shoppi.data.network.ApiClient
import com.kenshi.shoppi.data.network.ServiceLocator
import com.kenshi.shoppi.data.repository.CategoryDetailRepository
import com.kenshi.shoppi.data.repository.CategoryRepository
import com.kenshi.shoppi.data.repository.HomeRepository
import com.kenshi.shoppi.data.repository.ProductDetailRepository
import com.kenshi.shoppi.ui.category.CategoryViewModel
import com.kenshi.shoppi.ui.categorydetail.CategoryDetailViewModel
import com.kenshi.shoppi.ui.home.HomeViewModel
import com.kenshi.shoppi.ui.productdetail.ProductDetailViewModel
import java.lang.IllegalArgumentException

// DI(Hilt) 의 필요성ㅇㅇ Hilt 를 쓰면 별도로 생성자를 넣어주기 위한 ViewModelFactory 를 만들 필요가 없나?
// Hilt 를 통해 객체를 주입받으면 되니 (객체 주입 단계 생략)
//
// create 메소드에서 필요한 viewModel 을 생성해서 반환하면 됨
class ViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    // T: Generic
    // <T : ViewModel> <-- Generic 의 제약사항을 의미, ViewModel 클래스의 서브클래스여야만 한다.
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //isAssignableFrom: Generic type 으로 전달받은 인자의 type 을 알 수 있음
        return when {
            //HomeViewModel type 인지를 검사
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                //객체를 생성할때 의존 관계가 발생
                //이를 DI 라이브러리를 통해 해결할수 있지만 클래스간의 의존 관계를 설계할 수 있는 것 또한 중요!
                val repository = HomeRepository(HomeDataSourceImpl(AssetLoader(context)))
                //함수의 반환형을 제네릭으로 해주어야함 as : classCasting
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CategoryViewModel::class.java) -> {
//                val repository = ProductDetailRepository(ProductDetailDataSourceImpl(ApiClient.create()))
                val repository = CategoryRepository(CategoryDataSourceImpl(ServiceLocator.provideApiClient()))
                CategoryViewModel(repository) as T
            }

            modelClass.isAssignableFrom(CategoryDetailViewModel::class.java) -> {
//                val repository = ProductDetailRepository(ProductDetailDataSourceImpl(ApiClient.create()))
                val repository = CategoryDetailRepository(CategoryDetailDataSourceImpl(ServiceLocator.provideApiClient()))
                CategoryDetailViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ProductDetailViewModel::class.java) -> {
//                val repository = ProductDetailRepository(ProductDetailDataSourceImpl(ApiClient.create()))
                val repository = ProductDetailRepository(ProductDetailDataSourceImpl(ServiceLocator.provideApiClient()))
                ProductDetailViewModel(repository) as T
            }

            else -> {
                //ViewModel 이 아닌 클래스에서 create 메소드를 호출하는 경우
                throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
            }
        }
    }
}