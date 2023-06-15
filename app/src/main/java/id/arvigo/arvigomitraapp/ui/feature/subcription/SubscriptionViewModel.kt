package id.arvigo.arvigomitraapp.ui.feature.subcription

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.arvigo.arvigomitraapp.data.repository.HomeRepository
import id.arvigo.arvigomitraapp.data.source.local.AuthPreferences
import id.arvigo.arvigomitraapp.data.source.network.ApiService
import id.arvigo.arvigomitraapp.data.source.network.request.SubscriptionRequest
import id.arvigo.arvigomitraapp.data.source.network.response.SubscriptionResponse
import id.arvigo.arvigomitraapp.ui.feature.home.uistate.HomeProductState
import id.arvigo.arvigomitraapp.ui.feature.home.uistate.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubscriptionViewModel(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences,
) : ViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading
    private val _responseMessage = MutableStateFlow<String?>(null)
    val responseMessage: StateFlow<String?> = _responseMessage

    val responseProduct: MutableState<HomeProductState> = mutableStateOf(HomeProductState.Empty)

    val isPostSuccess = mutableStateOf(false)
    val isPostFailed = mutableStateOf(false)

    fun subscribe(request: SubscriptionRequest){
        viewModelScope.launch {
            _isLoading.value = true
            val token = authPreferences.getAuthToken()
           try {
               apiService.payment(token = "Bearer $token",request = request)
               _isLoading.value = false
               isPostSuccess.value = true
           } catch (e: Exception) {
               Log.e("Exception", e.toString())
                _isLoading.value = false
                isPostFailed.value = true
           }
            _isLoading.value = false
            isPostFailed.value = true

        }
    }

    fun clearResponseMessage() {
        _responseMessage.value = null
    }

}