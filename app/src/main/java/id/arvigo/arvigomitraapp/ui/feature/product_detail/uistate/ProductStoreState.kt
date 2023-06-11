package id.arvigo.arvigomitraapp.ui.feature.product_detail.uistate

import id.arvigo.arvigomitraapp.data.source.network.response.detail_product.Marketplace
import id.arvigo.arvigomitraapp.data.source.network.response.detail_product.ProductDataItem

sealed class ProductStoreState {
    class Success(val data: List<Marketplace>) : ProductStoreState()
    class Failure(val error: Throwable) : ProductStoreState()
    object Loading : ProductStoreState()
    object Empty : ProductStoreState()
}