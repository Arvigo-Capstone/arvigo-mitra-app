package id.arvigo.arvigomitraapp.data.repository

import id.arvigo.arvigomitraapp.data.source.local.AuthPreferences
import id.arvigo.arvigomitraapp.data.source.network.ApiService
import id.arvigo.arvigomitraapp.data.source.network.response.home.Product
import kotlinx.coroutines.flow.Flow
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

    fun deleteProduct(productId: String) : Flow<Boolean> = flow {
        val token = authPreferences.getAuthToken()
        val response = apiService.deleteProduct(
            token = "Bearer $token",
            id = productId,
        )

        if (response.data != null) {
            emit(false) // Deletion successful
        } else {
            emit(true) // Deletion failed
        }
    }

}