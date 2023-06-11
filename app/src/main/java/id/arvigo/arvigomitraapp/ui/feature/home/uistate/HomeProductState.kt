package id.arvigo.arvigomitraapp.ui.feature.home.uistate

import id.arvigo.arvigomitraapp.data.source.network.response.home.Product
import id.arvigo.arvigomitraapp.data.source.network.response.home.Visitors

sealed class HomeProductState {

    class Success(val data: List<Product>) : HomeProductState()
    class Failure(val error: Throwable) : HomeProductState()
    object Loading : HomeProductState()
    object Empty : HomeProductState()

}