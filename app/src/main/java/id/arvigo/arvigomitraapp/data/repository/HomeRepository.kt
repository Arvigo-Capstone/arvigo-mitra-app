package id.arvigo.arvigomitraapp.data.repository

import id.arvigo.arvigomitraapp.data.source.local.AuthPreferences
import id.arvigo.arvigomitraapp.data.source.network.ApiService
import kotlinx.coroutines.flow.flow

class HomeRepository(private val apiService: ApiService, private val authPreferences: AuthPreferences) {

    fun getHome() = flow {
        val token = authPreferences.getAuthToken()
        emit(apiService.getHome(
                token = "Bearer $token"
        ).data)
    }

}