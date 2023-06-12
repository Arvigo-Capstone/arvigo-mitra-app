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

    fun addOffer(request: AddOfferRequest) = flow {
        val token = authPreferences.getAuthToken()
        Log.d("Hit API Add Offer", "Add Offer ${request.toString()}")
        emit(apiService.addOffer(
                token = "Bearer $token",
                request = request
        ))
    }

}