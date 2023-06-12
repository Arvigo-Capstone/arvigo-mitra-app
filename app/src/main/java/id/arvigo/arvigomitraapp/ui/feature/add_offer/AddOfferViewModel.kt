package id.arvigo.arvigomitraapp.ui.feature.add_offer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import id.arvigo.arvigobasecore.domain.model.TextFieldState
import id.arvigo.arvigomitraapp.data.repository.AddOfferRepository

class AddOfferViewModel(private val addOfferRepository: AddOfferRepository) : ViewModel() {

    private val _nameState = mutableStateOf(TextFieldState())
    val nameSate: State<TextFieldState> = _nameState
    fun setName(value: String) {
        _nameState.value = _nameState.value.copy(text = value)
    }

    private val _priceState = mutableStateOf(TextFieldState())
    val priceSate: State<TextFieldState> = _priceState
    fun setPrice(value: String) {
        _priceState.value = _priceState.value.copy(text = value)
    }

    private val _descState = mutableStateOf(TextFieldState())
    val descSate: State<TextFieldState> = _descState
    fun setDesc(value: String) {
        _descState.value = _descState.value.copy(text = value)
    }

    private val _tokped = mutableStateOf(TextFieldState())
    val tokped: State<TextFieldState> = _tokped
    fun setTokped(value: String) {
        _tokped.value = _tokped.value.copy(text = value)
    }

}