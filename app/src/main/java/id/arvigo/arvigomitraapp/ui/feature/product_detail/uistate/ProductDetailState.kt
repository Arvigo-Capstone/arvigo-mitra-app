package id.arvigo.arvigomitraapp.ui.feature.product_detail.uistate

import id.arvigo.arvigomitraapp.data.source.network.response.detail_product.ProductDataItem
import id.arvigo.arvigomitraapp.data.source.network.response.home.Product
import id.arvigo.arvigomitraapp.ui.feature.home.uistate.HomeProductState

sealed class ProductDetailState {
    class Success(val data: ProductDataItem) : ProductDetailState()
    class Failure(val error: Throwable) : ProductDetailState()
    object Loading : ProductDetailState()
    object Empty : ProductDetailState()

}