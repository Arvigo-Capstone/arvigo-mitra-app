package id.arvigo.arvigomitraapp.data.repository

import android.util.Log
import id.arvigo.arvigomitraapp.data.source.local.AuthPreferences
import id.arvigo.arvigomitraapp.data.source.network.ApiService
import id.arvigo.arvigomitraapp.data.source.network.response.add_offer.AddOfferRequest
import kotlinx.coroutines.flow.flow

class AddOfferRepository(
        private val authPreferences: AuthPreferences,
        private val apiService: ApiService,
) {

    fun getInitialProduct(id: String) = flow {
        val token = authPreferences.getAuthToken()
        emit(apiService.getInitialProduct(
                token = "Bearer $token",
                id = id
        ).data)
    }

}