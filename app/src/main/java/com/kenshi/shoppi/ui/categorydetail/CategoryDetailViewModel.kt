package com.kenshi.shoppi.ui.categorydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenshi.shoppi.data.model.Promotion
import com.kenshi.shoppi.data.model.TopSelling
import com.kenshi.shoppi.data.repository.CategoryDetailRepository
import kotlinx.coroutines.launch

class CategoryDetailViewModel(
    private val categoryDetailRepository: CategoryDetailRepository
) : ViewModel() {
    private val _topSelling = MutableLiveData<TopSelling>()
    val topSelling: LiveData<TopSelling> = _topSelling

    private val _promotions = MutableLiveData<Promotion>()
    val promotions: LiveData<Promotion> = _promotions

    init {
        loadCategoryDetail()
    }

    private fun loadCategoryDetail() = viewModelScope.launch {
        val categoryDetail = categoryDetailRepository.getCategoryDetail()
        _topSelling.value = categoryDetail.topSelling
        _promotions.value = categoryDetail.promotions
    }
}