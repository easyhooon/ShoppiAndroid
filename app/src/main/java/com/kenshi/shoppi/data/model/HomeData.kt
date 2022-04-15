package com.kenshi.shoppi.data.model

import com.google.gson.annotations.SerializedName

//json 의 key값과 변수명을 갖게 맞춰야 함 (규칙), SerializedName 을 통해
data class HomeData(
    val title: Title,
    @SerializedName("top_banners")
    val topBanners: List<Banner>,
    val promotions: Promotion
)
