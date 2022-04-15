package com.kenshi.shoppi.data.model

import com.google.gson.annotations.SerializedName

data class Description (
    val id: String,
    @SerializedName("image_url")
    val imageUrl: String
)