package com.example.webservisdatabase.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.webservisdatabase.model.Mahasiswa
import com.example.webservisdatabase.repository.MahasiswaRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailUiState {
    data class Success(val mahasiswa: Mahasiswa) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
    object Loading : DetailUiState()
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val mhs: MahasiswaRepository
) : ViewModel() {
    private val _nim: String = checkNotNull(savedStateHandle["nim"])

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState> = _detailUiState.asStateFlow()

    init {
        getMahasiswaByNim(_nim)
    }

    private fun getMahasiswaByNim(nim: String) {
        viewModelScope.launch {
            _detailUiState.value = DetailUiState.Loading
            _detailUiState.value = try {
                val mahasiswa = mhs.getMahasiswaById(nim)
                DetailUiState.Success(mahasiswa)
            } catch (e: IOException) {
                DetailUiState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetailUiState.Error("Terjadi kesalahan server")
            }
        }
    }
}

