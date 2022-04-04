package com.kenshi.shoppi.ui.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.kenshi.shoppi.GlideApp

//데이터 바인딩 라이브러리에서 제공하는 바인딩어댑터 이용해서, 커스텀 속성을 정의
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    //아직 binding에 title data가 할당되지 않은 상태에서
    // 이 메소드가 호출되어 title이 null이라 imageUrl이 null로 전달될 수 있음
    if(!imageUrl.isNullOrEmpty()) {
        GlideApp.with(view)
            .load(imageUrl)
            .centerCrop()
            .into(view)
    }
}

@BindingAdapter("circleImageUrl")
fun loadCircleImage(view: ImageView, imageUrl: String?) {
    if(!imageUrl.isNullOrEmpty()) {
        GlideApp.with(view)
            .load(imageUrl)
            .circleCrop()
            .into(view)
    }
}


