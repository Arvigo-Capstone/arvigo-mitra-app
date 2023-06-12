package id.arvigo.arvigomitraapp.data.source.network.response


import com.google.gson.annotations.SerializedName

data class AddOfferResponse(
    @SerializedName("data")
    val `data`: Any?,
    @SerializedName("message")
    val message: String
)