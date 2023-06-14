package id.arvigo.arvigomitraapp.ui.feature.register

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.arvigo.arvigobasecore.ui.common.UiEvents
import id.arvigo.arvigobasecore.ui.feature.login.model.AuthState
import id.arvigo.arvigomitraapp.data.source.local.AuthPreferences
import id.arvigo.arvigomitraapp.data.source.network.ApiService
import id.arvigo.arvigomitraapp.ui.feature.login.LoginApiResults
import id.arvigo.arvigomitraapp.ui.feature.register.api.RegisterRequest
import id.arvigo.arvigomitraapp.ui.feature.register.api.RegisterResponse
import id.arvigo.arvigomitraapp.ui.navigation.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences,
) : ViewModel() {
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading
    private val _responseMessage = MutableStateFlow<String?>(null)
    val responseMessage: StateFlow<String?> = _responseMessage
    private val _loginResult = MutableLiveData<LoginApiResults>()
    val loginResult: LiveData<LoginApiResults> = _loginResult
    private var _loginState  = mutableStateOf(AuthState())
    val loginState: State<AuthState> = _loginState
    private val _navigateToHome = mutableStateOf(false)
    val navigateToHome: State<Boolean> = _navigateToHome
    private val  _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun register(request: RegisterRequest){
        _isLoading.value = true
        apiService.register(request).enqueue(object :
        Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val registerResponse = response.body()
                    val userId = registerResponse?.data?.userId
                    val token = registerResponse?.data?.token
                    if (registerResponse != null) {
                        viewModelScope.launch {
                            authPreferences.saveAuthToken(token.toString())
                            authPreferences.saveAuthId(userId.toString())
                        }
                        _loginResult.value = LoginApiResults.Success(userId!!, token.toString())
                        viewModelScope.launch {
                            _eventFlow.emit(
                                UiEvents.NavigateEvent(Screen.Home.route)
                            )
                        }
                        _navigateToHome.value = true
                        // Registration successful
                        val message = registerResponse.message
                        registerResponse.data
                        // Handle the success response accordingly
                        _responseMessage.value = "Registered: $message"
                        Log.d("neoTag", "Registered: $message")
                    } else {
                        // Response body is null
                        _responseMessage.value = "Response body is null"
                        Log.d("neoTag", "Response body is null")
                        _loginResult.value = LoginApiResults.Error("Invalid response")
                    }
                } else {
                    // Registration failed
                    val errorMessage = response.errorBody()?.string()
                    _loginResult.value = LoginApiResults.Error(errorMessage ?: "Unknown error")
                    // Handle the error response accordingly
                    _responseMessage.value = "Registration failed: $errorMessage"
                    Log.d("neoTag", "Registration failed: $errorMessage")
                }

            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                _responseMessage.value = "Registration failed: ${t.message}"
                Log.d("neoTag", "onFailure: $t")
            }
        })
    }

    fun clearResponseMessage() {
        _responseMessage.value = null
    }
}