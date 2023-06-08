package id.arvigo.arvigobasecore.ui.feature.splash.uistate

sealed class InitialAuthState {
    object Authenticated : InitialAuthState()
    object NotAuthenticated : InitialAuthState()
    object Loading : InitialAuthState()
}
