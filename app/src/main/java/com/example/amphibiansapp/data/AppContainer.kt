package com.example.amphibiansapp.data

import com.example.amphibiansapp.network.AmphibiansApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val amphbianRepository:AmphbianRepository
}
class DefaultAppContainer : AppContainer{

    private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"
    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()
    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService : AmphibiansApiService by lazy {
        retrofit.create(AmphibiansApiService::class.java)
    }
    /**
     * DI implementation for amphibians repository
     */
    override val amphbianRepository: AmphbianRepository by lazy {
        NetworkAmphibiansRepository(retrofitService)
    }

}