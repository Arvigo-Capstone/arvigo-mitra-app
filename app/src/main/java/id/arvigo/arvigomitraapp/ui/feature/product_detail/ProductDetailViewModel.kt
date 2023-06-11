package id.arvigo.arvigomitraapp.ui.feature.product_detail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.arvigo.arvigomitraapp.data.repository.ProductDetailRepository
import id.arvigo.arvigomitraapp.ui.feature.product_detail.uistate.ProductDetailState
import id.arvigo.arvigomitraapp.ui.feature.product_detail.uistate.ProductStoreState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ProductDetailViewModel(private val productDetailRepository: ProductDetailRepository) : ViewModel() {

    val response: MutableState<ProductDetailState> = mutableStateOf(ProductDetailState.Empty)
    val resMarketplace: MutableState<ProductStoreState> = mutableStateOf(ProductStoreState.Empty)

    fun getProductDetail(productId: String) = viewModelScope.launch {
        productDetailRepository.getProductDetail(productId = productId)
                .onStart {
                    response.value = ProductDetailState.Loading
                    resMarketplace.value = ProductStoreState.Loading
                }.catch {
                    response.value = ProductDetailState.Failure(it)
                    resMarketplace.value = ProductStoreState.Failure(it)
                }.collect {
                    response.value = ProductDetailState.Success(it)
                    resMarketplace.value = ProductStoreState.Success(it.marketplaces ?: emptyList())
                    Log.d("DETAIL PRODUCT SUCCESS", "get Product Detail")
                }
    }

}