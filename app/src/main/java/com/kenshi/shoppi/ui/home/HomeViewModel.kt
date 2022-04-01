package com.kenshi.shoppi.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kenshi.shoppi.data.model.Banner
import com.kenshi.shoppi.data.model.Title
import com.kenshi.shoppi.data.repository.HomeRepository

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _title = MutableLiveData<Title>()
    val title: LiveData<Title> = _title

    private val _topBanners = MutableLiveData<List<Banner>>()
    val topBanners: LiveData<List<Banner>> = _topBanners

    init {
        //viewModel 이 초기화 될때 호출되도록 (데이터를 요청)
        loadHomeData()
    }

    private fun loadHomeData() {
        //Data Layer -> Repository 에 요청
        val homeData = homeRepository.getHomeData()
        homeData?.let { homeData ->
            _title.value = homeData.title
            _topBanners.value = homeData.topBanners
        }
    }
}