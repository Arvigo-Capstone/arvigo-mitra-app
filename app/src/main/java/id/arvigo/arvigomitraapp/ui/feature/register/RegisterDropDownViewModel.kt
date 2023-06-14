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

    private val _storeName = mutableStateOf(TextFieldState())
    val storeName: State<TextFieldState> = _storeName

    fun setStoreName(value:String){
        _storeName.value = storeName.value.copy(text = value)
    }

    private val _streetState = mutableStateOf(TextFieldState())
    val streetState: State<TextFieldState> = _streetState

    fun setStreet(value:String){
        _streetState.value = streetState.value.copy(text = value)
    }



    private val _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState

    fun setPassword(value:String){
        _passwordState.value = passwordState.value.copy(text = value)
    }

    private val _passwordConfirState = mutableStateOf(TextFieldState())
    val passwordConfirState: State<TextFieldState> = _passwordConfirState

    fun setPasswordConfir(value:String){
        _passwordConfirState.value = passwordConfirState.value.copy(text = value)
    }

     fun getProvinces() = viewModelScope.launch {
        authRepository.getProvinces()
            .onStart {
                response.value = RegisterUiState.Loading
            }
            .collect {
                response.value = RegisterUiState.SuccessGetProvice(it)
                Log.d("Hit API Provinces", "get Province at viewModel ${response.value}")
            }
    }

    fun getCities(provinceId: Int) = viewModelScope.launch {
        authRepository.getCity(provice_id = provinceId)
            .onStart {
                response.value = RegisterUiState.Loading
            }
            .collect {
                response.value = RegisterUiState.SuccessGetCity(it)
                Log.d("Hit API Cities", "get City at viewModel ${response.value}")
            }
    }

    fun getDistrict(cityId: Int) = viewModelScope.launch {
        authRepository.getDistrict(city_id = cityId)
            .onStart {
                response.value = RegisterUiState.Loading
            }
            .collect {
                response.value = RegisterUiState.SuccessGetDistrict(it)
                Log.d("Hit API District", "get District at viewModel ${response.value}")
            }
    }

    fun getSubdistrict(districtId: Int) = viewModelScope.launch {
        authRepository.getSubdisctrict(district_id = districtId)
            .onStart {
                response.value = RegisterUiState.Loading
            }
            .collect {
                response.value = RegisterUiState.SuccessGetSubdistrict(it)
                Log.d("Hit API Subdistrict", "get Subdistcrit at viewModel ${response.value}")
            }
    }

    fun getPostalCode(subdistrictId: Int) = viewModelScope.launch {
        authRepository.getPostalcode(subdistrict_id = subdistrictId)
            .onStart {
                response.value = RegisterUiState.Loading
            }
            .collect {
                response.value = RegisterUiState.SuccessGetPostalCode(it)
                Log.d("Hit API Subdistrict", "get Subdistcrit at viewModel ${response.value}")
            }
    }

}