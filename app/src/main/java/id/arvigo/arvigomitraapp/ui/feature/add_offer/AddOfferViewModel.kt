package id.arvigo.arvigomitraapp.ui.feature.add_offer

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.arvigo.arvigobasecore.domain.model.TextFieldState
import id.arvigo.arvigomitraapp.data.repository.AddOfferRepository
import id.arvigo.arvigomitraapp.data.source.local.AuthPreferences
import id.arvigo.arvigomitraapp.data.source.network.ApiService
import id.arvigo.arvigomitraapp.data.source.network.response.AddOfferResponse
import id.arvigo.arvigomitraapp.data.source.network.response.add_offer.AddOfferRequest
import id.arvigo.arvigomitraapp.data.source.network.response.add_offer.DetailMarketplace
import id.arvigo.arvigomitraapp.data.source.network.response.add_offer.TestMarketplaceItem
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
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


    fun addOffer(images: List<File>) {
        viewModelScope.launch {

        try {

            val token = authPreferences.getAuthToken()
            val arrRes = ArrayList<TestMarketplaceItem>()
            arrRes.add(TestMarketplaceItem(1, _tokped.value.text, 2))
            arrRes.add(TestMarketplaceItem(2, _tokped.value.text, 2))

            val arrayResponse = listOf(
                    DetailMarketplace(1, 2, _tokped.value.text),
                    DetailMarketplace(2, 2, _tokped.value.text),
                    DetailMarketplace(2, 2, _tokped.value.text),
            )

            val request = AddOfferRequest(
                    description = "Ini adalah deskripsi",
                    name = "Ini adalah nama",
                    price = 10000,
                    images = images,
                    marketplaces = arrRes,
                    merchantId = 2,
                    productId = 2,
            )


            Log.d("See Array", "See array $arrRes")
            Log.d("Add Offer form ViewModel", "Add Offer ${request.toString()}")
            apiService.addOffer(
                    token = "Bearer $token",
                    request = request
            ).enqueue(object : Callback<AddOfferResponse>{
                override fun onResponse(call: Call<AddOfferResponse>, response: Response<AddOfferResponse>) {
                    if (response.isSuccessful) {
                        Log.d("Add Offer form ViewModel", "Add Offer ${response.body().toString()}")
                        Log.d("SUCCESS", "SUCCESS")
                    } else{
                        Log.d("Add Offer form ViewModel", "Add Offer ${response.body().toString()}")
                        Log.d("FAILED", "FAILED ${response.body().toString()}")
                    }
                }

                override fun onFailure(call: Call<AddOfferResponse>, t: Throwable) {
                    Log.d("FAILED", "Add Offer ${t.message.toString()}")
                }

            })
        } catch (e: HttpException) {
            Log.d("FAILED", "Add Offer ${e.message.toString()}")
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