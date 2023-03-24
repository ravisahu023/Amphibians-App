package com.example.amphibiansapp.data

import com.example.amphibiansapp.model.Ambhibian
import com.example.amphibiansapp.network.AmphibiansApiService

interface AmphbianRepository {
    suspend fun getAllAmphibians():List<Ambhibian>
}
class NetworkAmphibiansRepository(
    private val amphibiansApiService: AmphibiansApiService
) : AmphbianRepository{
    override suspend fun getAllAmphibians(): List<Ambhibian> = amphibiansApiService.getAllAmphibians()

}