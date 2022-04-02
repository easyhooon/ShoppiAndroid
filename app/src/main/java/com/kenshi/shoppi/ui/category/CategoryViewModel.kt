package com.kenshi.shoppi.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenshi.shoppi.data.model.Category
import com.kenshi.shoppi.data.repository.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryRepository: CategoryRepository
): ViewModel() {

    init {
        loadCategory()
    }

    private val _items = MutableLiveData<List<Category>>()
    val items: LiveData<List<Category>> = _items

    private fun loadCategory() = viewModelScope.launch {
        //뷰모델은 CategoryFragment에서 생성이 되기 때문에
        // 이렇게만 설정해두면 UI 쓰레드에서 실행됨
        // UI 쓰레드가 아닌 별도의 쓰레드에서 실행되도록 전환하는 단계 필요
        //retrofit을 통신 함수이기 때문에 suspend 키워드만 붙혀주면 해결
        val categories = categoryRepository.getCategories()
        _items.value = categories
    }
}