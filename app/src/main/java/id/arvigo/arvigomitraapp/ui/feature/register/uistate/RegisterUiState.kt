package id.arvigo.arvigomitraapp.ui.feature.register.uistate

import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.city.CityItem
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.provice.ProvinceItem

sealed class RegisterUiState {
    class SuccessGetProvice(val data: List<ProvinceItem>) : RegisterUiState()
    class SuccessGetCity(val data: List<CityItem>) : RegisterUiState()
    class SuccessGet(val data: List<CityItem>) : RegisterUiState()
    class Failure(val error: Throwable) : RegisterUiState()
    object Loading : RegisterUiState()
    object Empty : RegisterUiState()
}
