package id.arvigo.arvigomitraapp.ui.feature.add_offer.initial_page

import id.arvigo.arvigomitraapp.data.source.network.response.home.Product
import id.arvigo.arvigomitraapp.data.source.network.response.intial_product.InitialData
import id.arvigo.arvigomitraapp.ui.feature.home.uistate.HomeProductState

sealed class InitialPageState {
    class Success(val data: List<InitialData>) : InitialPageState()
    class Failure(val error: Throwable) : InitialPageState()
    object Loading : InitialPageState()
    object Empty : InitialPageState()
}
