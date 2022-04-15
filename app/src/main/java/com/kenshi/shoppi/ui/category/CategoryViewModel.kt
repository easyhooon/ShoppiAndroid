package com.kenshi.shoppi.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenshi.shoppi.data.model.Category
import com.kenshi.shoppi.data.repository.CategoryRepository
import com.kenshi.shoppi.ui.common.Event
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryRepository: CategoryRepository
): ViewModel() {
    private val _items = MutableLiveData<List<Category>>()
    val items: LiveData<List<Category>> = _items

    //category 목록에서 categoryItem 이 선택되었는지 정보를 저장하는 데이터
    private val _openCategoryEvent = MutableLiveData<Event<Category>>()
    val openCategoryEvent: LiveData<Event<Category>> = _openCategoryEvent

    init {
        loadCategory()
    }

    // 카테고리 정보를 저장 하기 위해서 category 를 parameter 로 받음
    // 사용자가 categoryItem 을 선택을 하면 _openCategoryEvent 값을 업데에트
    // view에서 메소드를 호출 -> databinding layout에 viewModel을 바인딩
    // recyclerview에 아이템을 클릭하면 detail 로 이동하는 함수이므로
    // itemCategory layout에 viewModel 을 바인딩 -> adapter 에 viewModel 을 생성자로 전달해줘야함
    fun openCategoryDetail(category: Category) {
        _openCategoryEvent.value = Event(category)
    }

    private fun loadCategory() = viewModelScope.launch {
        //뷰모델은 CategoryFragment에서 생성이 되기 때문에
        // 이렇게만 설정해두면 UI 쓰레드에서 실행됨
        // UI 쓰레드가 아닌 별도의 쓰레드에서 실행되도록 전환하는 단계 필요
        //retrofit을 통신 함수이기 때문에 suspend 키워드만 붙혀주면 해결
        val categories = categoryRepository.getCategories()
        _items.value = categories
    }
}