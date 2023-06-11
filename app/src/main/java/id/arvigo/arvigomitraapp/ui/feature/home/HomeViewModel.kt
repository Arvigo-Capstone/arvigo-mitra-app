package id.arvigo.arvigomitraapp.ui.feature.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.arvigo.arvigomitraapp.data.repository.HomeRepository
import id.arvigo.arvigomitraapp.ui.feature.home.uistate.HomeProductState
import id.arvigo.arvigomitraapp.ui.feature.home.uistate.HomeUiState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
        private val homeRepository: HomeRepository,
) : ViewModel() {


    val response: MutableState<HomeUiState> = mutableStateOf(HomeUiState.Empty)
    val responseProduct: MutableState<HomeProductState> = mutableStateOf(HomeProductState.Empty)

    init {
        getHome()
    }

    fun getHome() = viewModelScope.launch {
        homeRepository.getHome()
                .onStart {
                    response.value = HomeUiState.Loading
                    responseProduct.value = HomeProductState.Loading
                }
                .catch {
                    response.value = HomeUiState.Failure(it)
                    responseProduct.value = HomeProductState.Failure(it)
                }.collect {
                    response.value = HomeUiState.Success(it.visitors)
                    responseProduct.value = HomeProductState.Success(it.products ?: emptyList())
                }
    }


}