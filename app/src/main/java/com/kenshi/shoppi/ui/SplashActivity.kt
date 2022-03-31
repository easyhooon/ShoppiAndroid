package com.kenshi.shoppi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//layout 파일을 통해 layout 을 만드는게 아닌 drawable 파일로
//30이하의 버전에서 splash 화면을 만들기 위해 SplashActivity 를 필요로 함
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}