package id.arvigo.arvigomitraapp.ui.feature.register.uistate

import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.city.CityItem
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.district.DistrictItem
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.postal_code.PostalcodeItem
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.provice.ProvinceItem
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.subdistrict.SubdistrictItem

sealed class RegisterUiState {
    class SuccessGetProvice(val data: List<ProvinceItem>) : RegisterUiState()
    class SuccessGetCity(val data: List<CityItem>) : RegisterUiState()
    class SuccessGetDistrict(val data: List<DistrictItem>) : RegisterUiState()
    class SuccessGetSubdistrict(val data: List<SubdistrictItem>) : RegisterUiState()
    class SuccessGetPostalCode(val data: List<PostalcodeItem>) : RegisterUiState()
    class Failure(val error: Throwable) : RegisterUiState()
    object Loading : RegisterUiState()
    object Empty : RegisterUiState()
}
