package id.arvigo.arvigomitraapp.data.repository

import id.arvigo.arvigomitraapp.data.source.local.AuthPreferences
import id.arvigo.arvigomitraapp.data.source.network.ApiService
import kotlinx.coroutines.flow.flow

class ProductDetailRepository(
        private val authPreferences: AuthPreferences,
        private val apiService: ApiService,
) {


    fun getProductDetail(productId: String) = flow {
        val token = authPreferences.getAuthToken()
        emit(apiService.getProductDetail(
                token = "Bearer $token",
                id = productId,
        ).data
        )
    }

}