package id.arvigo.arvigomitraapp.ui.feature.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.arvigo.arvigomitraapp.data.repository.ProfileRepository
import id.arvigo.arvigomitraapp.data.source.local.AuthPreferences
import id.arvigo.arvigomitraapp.ui.feature.home.uistate.HomeProductState
import id.arvigo.arvigomitraapp.ui.feature.home.uistate.HomeUiState
import id.arvigo.arvigomitraapp.ui.feature.profile.uistate.ProfileUiState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val authPreferences: AuthPreferences,

) : ViewModel() {

    val response: MutableState<ProfileUiState> = mutableStateOf(ProfileUiState.Empty)

    init {
        getProfile()
    }

    fun getProfile() = viewModelScope.launch {
        profileRepository.getProfile()
            .onStart {
                response.value = ProfileUiState.Loading
            }
            .catch {
                response.value = ProfileUiState.Failure(it)
            }.collect {
                response.value = ProfileUiState.Success(it)
            }
    }

    fun logout() {
        viewModelScope.launch {
            authPreferences.clearAuthToken()
        }
    }
}