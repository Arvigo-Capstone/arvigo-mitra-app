package id.arvigo.arvigomitraapp.ui.feature.profile.uistate

import id.arvigo.arvigomitraapp.data.source.network.response.home.Visitors
import id.arvigo.arvigomitraapp.data.source.network.response.profile.ProfileData

sealed class ProfileUiState {
    class Success(val data: ProfileData) : ProfileUiState()
    class Failure(val error: Throwable) : ProfileUiState()
    object Loading : ProfileUiState()
    object Empty : ProfileUiState()

}