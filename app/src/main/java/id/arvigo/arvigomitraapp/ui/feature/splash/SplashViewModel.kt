package id.arvigo.arvigomitraapp.ui.feature.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.arvigo.arvigobasecore.ui.feature.splash.uistate.InitialAuthState
import id.arvigo.arvigomitraapp.data.source.local.AuthPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val authPreferences: AuthPreferences,
) : ViewModel() {

    private val _authState = MutableLiveData<InitialAuthState>()
    val authState: LiveData<InitialAuthState> = _authState

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    private val _isOnbordingVisited = MutableStateFlow(false)
    val isOnboardingVisited: StateFlow<Boolean> = _isOnbordingVisited

    init {
        checkAuthToken()
        //observeOnboardingStatus()
    }

    fun checkAuthToken() {
        viewModelScope.launch {
            val authToken = authPreferences.getAuthToken()
            if (authToken != null) {
                _isUserLoggedIn.value = !authToken.isNullOrEmpty()
            }
        }
    }

    private fun observeOnboardingStatus() {
        viewModelScope.launch {
            authPreferences.onboardingStatusFlow.collect { status ->
                if (status) {
                    // User has completed onboarding
                    // Perform any necessary actions
                    _isOnbordingVisited.value = status
                } else {
                    // User has not completed onboarding
                    // Perform onboarding-related actions
                    _isOnbordingVisited.value = status
                }
            }
        }
    }

    fun saveOnboardingStatus(status: Boolean) {
        viewModelScope.launch {
            authPreferences.saveOnboardingState(status)
        }
    }

}