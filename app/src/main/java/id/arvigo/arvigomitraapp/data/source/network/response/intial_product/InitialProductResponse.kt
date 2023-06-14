package id.arvigo.arvigomitraapp.data.source.network.response.intial_product


import com.google.gson.annotations.SerializedName

data class InitialProductResponse(
    @SerializedName("data")
    val `data`: List<InitialData>,
    @SerializedName("message")
    val message: String
)