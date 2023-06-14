package id.arvigo.arvigomitraapp.ui.feature.add_offer

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonParser
import id.arvigo.arvigobasecore.domain.model.TextFieldState
import id.arvigo.arvigomitraapp.data.repository.AddOfferRepository
import id.arvigo.arvigomitraapp.data.source.local.AuthPreferences
import id.arvigo.arvigomitraapp.data.source.network.ApiService
import id.arvigo.arvigomitraapp.data.source.network.response.AddOfferResponse
import id.arvigo.arvigomitraapp.data.source.network.response.add_offer.AddOfferRequest
import id.arvigo.arvigomitraapp.data.source.network.response.add_offer.DetailMarketplace
import id.arvigo.arvigomitraapp.data.source.network.response.add_offer.TestMarketplaceItem
import id.arvigo.arvigomitraapp.ui.feature.add_offer.initial_page.InitialPageState
import id.arvigo.arvigomitraapp.ui.feature.home.uistate.HomeUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.ext.clearQuotes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.File
import kotlin.text.toInt

class AddOfferViewModel(
        private val addOfferRepository: AddOfferRepository,
        private val apiService: ApiService,
        private val authPreferences: AuthPreferences,
) : ViewModel() {

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

    private val _shopee = mutableStateOf(TextFieldState())
    val shopee: State<TextFieldState> = _shopee
    fun setShopee(value: String) {
        _shopee.value = _shopee.value.copy(text = value)
    }

    private val _blibli = mutableStateOf(TextFieldState())
    val blibli: State<TextFieldState> = _blibli
    fun setBlibli(value: String) {
        _blibli.value = _blibli.value.copy(text = value)
    }

    private val _offline = mutableStateOf(TextFieldState())
    val offline: State<TextFieldState> = _offline
    fun setOffline(value: String) {
        _offline.value = _offline.value.copy(text = value)
    }

    var idProduct = mutableStateOf(0)
    var initProduct = 0
    fun setIdProduct(value: Int) {
        idProduct.value = value
        initProduct = value
        Log.d("See ID Product", "See ID Product ${idProduct.value}")
    }

    val isLoading = mutableStateOf(false)
    val isPostSuccess = mutableStateOf(false)
    val isPostFailed = mutableStateOf(false)


    val response: MutableState<InitialPageState> = mutableStateOf(InitialPageState.Empty)


    fun addOffer(images: List<MultipartBody.Part>, initialId: Int) {
        viewModelScope.launch {
8
        try {
            isLoading.value = true
            val token = authPreferences.getAuthToken()
            val merchantId = authPreferences.getAuthId()
            val arrRes = ArrayList<TestMarketplaceItem>()

            val arrayResponse = listOf(
                    DetailMarketplace(2, merchantId!!.toInt(), _tokped.value.text),
                    DetailMarketplace(2, merchantId.toInt(), _shopee.value.text),
                    DetailMarketplace(2, merchantId.toInt(), _blibli.value.text),
                    DetailMarketplace(2, merchantId.toInt(), _offline.value.text),
            )
            val json = Gson().toJson(arrayResponse)
            val cleanedJson = json.replace("\\\\", "")

            Log.d("See Array", "See array ${Gson().toJson(arrayResponse)}")
            Log.d("See Replace Array", "See array $cleanedJson")
            Log.d("See Replace ArrayList", "See array $arrayResponse")
            Log.d("See Initial Product ID", "See initial $idProduct")
            apiService.addOffer(
                    token = "Bearer $token",
                    name = _nameState.value.text,
                    price = _priceState.value.text.toInt(),
                    description = _descState.value.text,
                    images = images,
                    marketplaces = cleanedJson,
                    merchantId = merchantId.toInt(),
                    productId = initialId,
            ).enqueue(object : Callback<AddOfferResponse>{
                override fun onResponse(call: Call<AddOfferResponse>, response: Response<AddOfferResponse>) {
                    if (response.isSuccessful) {
                        Log.d("Add Offer form ViewModel", "Add Offer ${response.body().toString()}")
                        Log.d("SUCCESS", "SUCCESS")
                        isLoading.value = false
                        isPostSuccess.value = true
                    } else{
                        Log.d("Add Offer form ViewModel", "Add Offer ${response.body().toString()}")
                        Log.d("FAILED", "FAILED ${response.body().toString()}")
                        isLoading.value = false
                        isPostFailed.value = true
                    }
                }

                override fun onFailure(call: Call<AddOfferResponse>, t: Throwable) {
                    Log.d("FAILED", "FAIL ${t.message.toString()}")
                    isLoading.value = false
                    isPostFailed.value = true
                }

            })
        } catch (e: HttpException) {
            Log.d("FAILED", "Add Offer ${e.message}")
            isLoading.value = false
        }
    }}

    fun createMultipartParts(files: List<File>): List<MultipartBody.Part> {
        val parts = mutableListOf<MultipartBody.Part>()

        files.forEach { file ->
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData("image", file.name, requestFile)
            parts.add(part)
        }

        return parts
    }

    fun getInitialPrduct(id: String) = viewModelScope.launch {
        addOfferRepository.getInitialProduct(id = id)
            .onStart {
                response.value = InitialPageState.Loading
            }
            .catch {
                response.value = InitialPageState.Failure(it)
            }
            .collect {
                response.value = InitialPageState.Success(it)
            }
    }

    fun parseMarketplaceData(): ArrayList<TestMarketplaceItem> {
        val marketplaceItems = ArrayList<TestMarketplaceItem>()

        val tokped = _tokped.value.text

        // Create a TestMarketplaceItem object for name
        val nameMarketplaceItem = TestMarketplaceItem(
                addressesId = 2, // Set the addressesId value as needed
                link = tokped, // Use the value from the name state
                marketplaceId = 2 // Set the marketplaceId value as needed
        )
        marketplaceItems.add(nameMarketplaceItem)

        // Create a TestMarketplaceItem object for price
        val priceMarketplaceItem = TestMarketplaceItem(
                addressesId = 2, // Set the addressesId value as needed
                link = tokped, // Use the value from the price state
                marketplaceId = 2 // Set the marketplaceId value as needed
        )
        marketplaceItems.add(priceMarketplaceItem)

        // Create a TestMarketplaceItem object for desc
        val descMarketplaceItem = TestMarketplaceItem(
                addressesId = 4, // Set the addressesId value as needed
                link = tokped, // Use the value from the desc state
                marketplaceId = 2// Set the marketplaceId value as needed
        )
        marketplaceItems.add(descMarketplaceItem)

        // Create a TestMarketplaceItem object for tokped
        val tokpedMarketplaceItem = TestMarketplaceItem(
                addressesId = 1, // Set the addressesId value as needed
                link = tokped, // Use the value from the tokped state
                marketplaceId = 2 // Set the marketplaceId value as needed
        )
        marketplaceItems.add(tokpedMarketplaceItem)

        return marketplaceItems
    }


}