package com.kenshi.shoppi.ui.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.databinding.BindingConversion

//TextView의 background 색상을 변경할때
//Data 로 Color code 문자열을 받지만 실제로 background 에 할당을 할때는 이를 drawable 객체로 변환을 했어야 함
//이를 databinding Library에서도 지원을 함

//BindingConversion
//-> databinding 표현식에 할당된 값에 type을 변환하는 방법을 정의할 수 있음
@BindingConversion
fun convertToColorDrawable(color: String): Drawable {
    return ColorDrawable(Color.parseColor(color))
}