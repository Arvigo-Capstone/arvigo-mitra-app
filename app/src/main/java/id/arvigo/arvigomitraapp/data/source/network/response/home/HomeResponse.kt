package id.arvigo.arvigomitraapp.data.source.network.response.home


import com.google.gson.annotations.SerializedName

data class HomeResponse(
        @SerializedName("data")
    val `data`: HomeData,
        @SerializedName("message")
    val message: String
)