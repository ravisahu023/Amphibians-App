package com.example.amphibiansapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibiansapp.AmphibiansApplication
import com.example.amphibiansapp.data.AmphbianRepository
import com.example.amphibiansapp.data.NetworkAmphibiansRepository
import com.example.amphibiansapp.model.Ambhibian
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface AmphibianUiState{

    data class Success(val amphibians: List<Ambhibian>) : AmphibianUiState
    object Error : AmphibianUiState
    object Loading : AmphibianUiState
}

class AmphibiansViewModel(private val amphibiansRepository: AmphbianRepository) : ViewModel() {
    var amphibiansUiState: AmphibianUiState by mutableStateOf(AmphibianUiState.Loading)
        private set

    init {
        getAmphibians()
    }

    fun getAmphibians(){
        viewModelScope.launch {

            amphibiansUiState = try {
                val listResult = amphibiansRepository.getAllAmphibians()
                AmphibianUiState.Success(listResult)
            } catch (e : IOException){
                AmphibianUiState.Error
            } catch (e: HttpException){
                AmphibianUiState.Error
            }
        }

    }
    /**
     * Factory for [MarsViewModel] that takes [MarsPhotosRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AmphibiansApplication)
                val amphibiansRepository = application.container.amphbianRepository
                AmphibiansViewModel(amphibiansRepository = amphibiansRepository)
            }
        }
    }
}