package id.arvigo.arvigomitraapp.data.repository

import id.arvigo.arvigomitraapp.data.source.local.AuthPreferences
import id.arvigo.arvigomitraapp.data.source.network.ApiService
import kotlinx.coroutines.flow.flow

class ProfileRepository(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences,
) {

    fun getProfile() = flow {
        val token = authPreferences.getAuthToken()
        val userID = authPreferences.getAuthId()
        emit(apiService.getProfile(
            token = "Bearer $token",
            userId = userID!!
        ).data)
    }

}