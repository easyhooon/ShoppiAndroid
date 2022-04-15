package com.kenshi.shoppi.data.network

object ServiceLocator {

    private var apiClient: ApiClient? = null

    // 처음에 한번만 호출, 그 이후 부터는 생성된 apiClient 를 호출 (재사용)
    // 이 구현을 Hilt 를 통해 의존성을 주입하는 것으로 변경 가능
    fun provideApiClient(): ApiClient {
        return apiClient ?: kotlin.run {
            ApiClient.create().also {
                apiClient = it
            }
        }
    }
}