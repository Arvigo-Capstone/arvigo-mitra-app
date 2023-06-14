package id.arvigo.arvigomitraapp.data.repository

import android.util.Log
import id.arvigo.arvigomitraapp.data.source.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRepository(private val apiService: ApiService) {

    fun getProvinces() = flow {
        try {
            emit(apiService.getProvinces().data)
            Log.d("Hit API Province", "get all province at repo ${apiService.getProvinces().data}")
        } catch (e: Exception) {
            Log.d("ERROR API Province", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    fun getCity(provice_id: Int) = flow {
        Log.d("Hit API City", "get all city")
        emit(apiService.getCity(
            id = provice_id
        ).data)
    }.flowOn(Dispatchers.IO)

    fun getDistrict(city_id: Int) = flow {
        Log.d("Hit API District", "get all disctrict")
        emit(apiService.getDistrict(
            id = city_id
        ).data)
    }.flowOn(Dispatchers.IO)

    fun getSubdisctrict(district_id: Int) = flow {
        Log.d("Hit API Subdistrict", "get all disctrict")
        emit(apiService.getSubdistrict(
            id = district_id
        ).data)
    }.flowOn(Dispatchers.IO)

    fun getPostalcode(subdistrict_id: Int) = flow {
        Log.d("Hit API Postal code", "get all postal code")
        emit(apiService.getPostalcode(
            id = subdistrict_id
        ).data)
    }.flowOn(Dispatchers.IO)

}