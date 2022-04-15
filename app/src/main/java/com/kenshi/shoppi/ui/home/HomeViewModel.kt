package com.kenshi.shoppi.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kenshi.shoppi.data.model.Banner
import com.kenshi.shoppi.data.model.Category
import com.kenshi.shoppi.data.model.Promotion
import com.kenshi.shoppi.data.model.Title
import com.kenshi.shoppi.data.repository.HomeRepository
import com.kenshi.shoppi.ui.common.Event

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _title = MutableLiveData<Title>()
    val title: LiveData<Title> = _title

    private val _topBanners = MutableLiveData<List<Banner>>()
    val topBanners: LiveData<List<Banner>> = _topBanners

    private val _promotions = MutableLiveData<Promotion>()
    val promotions: LiveData<Promotion> = _promotions

    //Banner 가 선택되었는지 여부를 저장하는 변수
    private val _openProductDetailEvent = MutableLiveData<Event<String>>()
    val openProductDetailEvent: LiveData<Event<String>> = _openProductDetailEvent

    init {
        //viewModel 이 초기화 될때 호출되도록 (데이터를 요청)
        loadHomeData()
    }

    fun openProductDetail(productId: String) {
        _openProductDetailEvent.value = Event(productId)
    }

    private fun loadHomeData() {
        //Data Layer -> Repository 에 요청
        val homeData = homeRepository.getHomeData()
        homeData?.let { homeData ->
            _title.value = homeData.title
            _topBanners.value = homeData.topBanners
            _promotions.value = homeData.promotions
        }
    }

}