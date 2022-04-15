package com.kenshi.shoppi.ui.common


//View.OnClickListener interface 를 모방한 인터페이스
// onProductClick 이 호출되었을 때 처리해야하는 작업을 ProductClickListener 를 상속받은(구현하는) 클래스에서 정의
interface ProductClickListener {
    fun onProductClick(productId: String)
}