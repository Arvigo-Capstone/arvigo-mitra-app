package id.arvigo.arvigomitraapp.ui.feature.home.uistate

import id.arvigo.arvigomitraapp.data.source.network.response.home.Visitors

sealed class HomeUiState {
    class Success(val data: Visitors) : HomeUiState()
    class Failure(val error: Throwable) : HomeUiState()
    object Loading : HomeUiState()
    object Empty : HomeUiState()
}