package com.example.amphibiansapp.network

import com.example.amphibiansapp.model.Ambhibian
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET



interface AmphibiansApiService {

    @GET("amphibians")
    suspend fun getAllAmphibians() : List<Ambhibian>

}
