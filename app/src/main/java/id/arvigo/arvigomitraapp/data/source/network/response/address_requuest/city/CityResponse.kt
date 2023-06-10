package id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.city


import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("data")
    val `data`: List<CityItem>,
    @SerializedName("message")
    val message: String
)