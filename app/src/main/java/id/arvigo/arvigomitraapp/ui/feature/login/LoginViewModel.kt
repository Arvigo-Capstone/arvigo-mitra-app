package id.arvigo.arvigomitraapp.ui.feature.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.arvigo.arvigobasecore.domain.model.TextFieldState
import id.arvigo.arvigobasecore.ui.common.UiEvents
import id.arvigo.arvigobasecore.ui.feature.login.model.AuthState
import id.arvigo.arvigomitraapp.data.source.local.AuthPreferences
import id.arvigo.arvigomitraapp.data.source.network.ApiService
import id.arvigo.arvigomitraapp.data.source.network.request.LoginRequest
import id.arvigo.arvigomitraapp.data.source.network.response.LoginResponse
import id.arvigo.arvigomitraapp.ui.navigation.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences,
    ) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginApiResults>()
    val loginResult: LiveData<LoginApiResults> = _loginResult
    private var _loginState  = mutableStateOf(AuthState())
    val loginState: State<AuthState> = _loginState

    private val  _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState

    fun setEmail(value:String){
        _emailState.value = emailState.value.copy(text = value)
    }

    private val _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState

    fun setPassword(value:String){
        _passwordState.value = passwordState.value.copy(text = value)
    }

    private val _navigateToHome = mutableStateOf(false)
    val navigateToHome: State<Boolean> = _navigateToHome

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn


    fun loginNew(email: String, password: String, role: String){
        viewModelScope.launch {
            try {
                val request = LoginRequest(email, password, role)

                apiService.login(request).enqueue(object: Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>,
                    ) {
                       if (response.isSuccessful) {
                           val responseBody = response.body()?.data
                           val userId = responseBody?.userId
                           val token = responseBody?.token

                           if (userId != null && token != null) {
                               viewModelScope.launch {
                                   authPreferences.saveAuthToken(token)
                                   authPreferences.saveAuthId(userId.toString())
                               }
                               // Update the login result with success
                               _loginResult.value = LoginApiResults.Success(userId, token)
                               viewModelScope.launch {
                                   _eventFlow.emit(
                                       UiEvents.NavigateEvent(Screen.Home.route)
                                   )
                               }
                               _navigateToHome.value = true
                           } else {
                               _loginResult.value = LoginApiResults.Error("Invalid response")
                               clearEmail()
                               clearPassword()
                           }
                       } else {
                           _loginResult.value = LoginApiResults.Error(response.message())
                           viewModelScope.launch {
                                 _eventFlow.emit(
                                      UiEvents.SnackbarEvent(response.message())
                                 )
                           }
                           clearEmail()
                           clearPassword()
                       }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        _loginResult.value = LoginApiResults.Error(t.localizedMessage ?: "Unknown error occurred")
                    }

                })

//                val response = apiService.login(request)
//                val userId = response.request().body.
//                val token = response.token
//
//                authPreferences.saveAuthToken(token)
//
//                // Update the login result with success
//                _loginResult.value = LoginApiResults.Success(userId, token)
//                _navigateToHome.value = true
            } catch (e: Exception) {
                // Update the login result with error
                _loginResult.value = LoginApiResults.Error(e.localizedMessage ?: "Unknown error occurred")
            }
        }
    }

    fun clearEmail() {
        setEmail("")
    }

    fun clearPassword() {
        setPassword("")
    }

//    private fun checkLogin() {
//        viewModelScope.launch {
//            val token = authPreferences.getAuthToken()
//            _isUserLoggedIn.value = !token.isNullOrEmpty()
//        }
//    }

//    fun loginUser(){
//        viewModelScope.launch {
//            _loginState.value = loginState.value.copy(isLoading = false)
//
//            val loginResult = loginUseCase(
//                email = emailState.value.text,
//                password = passwordState.value.text,
//                role = "mobile-app"
//            )
//
//            _loginState.value = loginState.value.copy(isLoading = false)
//
//            if (loginResult.emailError != null){
//                _emailState.value=emailState.value.copy(error = loginResult.emailError)
//            }
//            if (loginResult.passwordError != null){
//                _passwordState.value = passwordState.value.copy(error = loginResult.passwordError)
//            }
//
//            when(loginResult.result){
//                is Resource.Success->{
//                    _eventFlow.emit(
//                        UiEvents.NavigateEvent(Screen.Home.route)
//                    )
//                }
//                is Resource.Error->{
//                    UiEvents.SnackbarEvent(
//                        loginResult.result.message ?: "Error!"
//                    )
//                }
//                else -> {
//
//                }
//            }
//        }
//    }
}

sealed class LoginApiResults {
    data class Success(val userId: Int, val token: String) : LoginApiResults()
    data class Error(val errorMessage: String) : LoginApiResults()
}