package id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.provice


import com.google.gson.annotations.SerializedName

data class ProvincesResponse(
    @SerializedName("data")
    val `data`: List<ProvinceItem>,
    @SerializedName("message")
    val message: String
)