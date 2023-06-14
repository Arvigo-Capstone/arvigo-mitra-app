package id.arvigo.arvigomitraapp.data.source.network.response.detail_product


import com.google.gson.annotations.SerializedName

data class DeleteResponse(
    @SerializedName("data")
    val `data`: Any?,
    @SerializedName("message")
    val message: String
)