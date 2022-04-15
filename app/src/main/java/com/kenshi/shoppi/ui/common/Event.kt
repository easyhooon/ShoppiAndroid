package com.kenshi.shoppi.ui.common

import androidx.lifecycle.Observer

//architecture-samples/app/src/main/java/com/example/android/architecture/blueprints/todoapp/Event.kt

//데이터 타입은 무엇이든 가능하게 제네릭
class Event<T>(private val content: T) {
    //데이터가 소비되었는지 여부 확인

    private var hasBeenHandled = false

    // hasBeenHandled 가 false 일 때만 content 값을 반환
    // liveData 의 데이터가 변경이 되었음을 알림을 받을때마다
    // 이 메소드를 통해 실제로 이 데이터가 소비된적이 있는지 확인을 해야함
    // 이 과정을 모든 liveData Type 이 확인을 해야함
    // 이 반복되는 확인 과정을 대신 처리해주는 객체가 class EventObserver
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}

// observe 메소드를 호출을 할 때, 두번째 인자로 전달했던 것이 observer 를 구현한 객체
// 그 observer 구현한 객체를 만들어서 observe 메소드의 두번째 인자로 EventObserver 를 구현해서 전달
// 제네릭은 쌍으로 맞춰줘야
class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let {
            //아직 처리되지 않은 데이터를 전달
            onEventUnhandledContent(it)
        }
    }
}