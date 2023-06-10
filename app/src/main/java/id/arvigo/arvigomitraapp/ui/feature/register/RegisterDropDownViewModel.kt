package id.arvigo.arvigomitraapp.ui.feature.register

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.arvigo.arvigobasecore.domain.model.TextFieldState
import id.arvigo.arvigomitraapp.data.repository.AuthRepository
import id.arvigo.arvigomitraapp.data.source.local.AuthPreferences
import id.arvigo.arvigomitraapp.data.source.network.ApiService
import id.arvigo.arvigomitraapp.ui.feature.register.uistate.RegisterUiState
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class RegisterDropDownViewModel(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences,
    private val authRepository: AuthRepository,
) : ViewModel() {

    val response: MutableState<RegisterUiState> = mutableStateOf(RegisterUiState.Empty)

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

    init {
        getProvinces()
    }

    private fun getProvinces() = viewModelScope.launch {
        authRepository.getProvinces()
            .onStart {
                response.value = RegisterUiState.Loading
            }
            .collect {
                response.value = RegisterUiState.SuccessGetProvice(it)
                Log.d("Hit API Provinces", "get Province at viewModel ${response.value}")
            }
    }

}