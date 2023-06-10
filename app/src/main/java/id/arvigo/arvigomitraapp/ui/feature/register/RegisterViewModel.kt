package id.arvigo.arvigomitraapp.ui.feature.register

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import id.arvigo.arvigomitraapp.data.source.network.ApiService
import id.arvigo.arvigomitraapp.ui.feature.register.api.RegisterRequest
import id.arvigo.arvigomitraapp.ui.feature.register.api.RegisterResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(
    private val apiService: ApiService
) : ViewModel() {
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading
    private val _responseMessage = MutableStateFlow<String?>(null)
    val responseMessage: StateFlow<String?> = _responseMessage

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
                    if (registerResponse != null) {
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
                    }
                } else {
                    // Registration failed
                    val errorMessage = response.errorBody()?.string()
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