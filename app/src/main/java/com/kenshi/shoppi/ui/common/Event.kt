package com.kenshi.shoppi.ui.common

import androidx.lifecycle.Observer

//데이터 타입은 무엇이든 가능하게 제네릭
class Event<T>(private val content: T) {
    //데이터가 소비되었는지 여부 확인
    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}

//제네릭은 쌍으로 맞춰줘야
class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let {
            //아직 처리되지 않은 데이터를 전달
            onEventUnhandledContent(it)
        }
    }
}