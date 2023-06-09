package id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.postal_code


import com.google.gson.annotations.SerializedName

data class PostalcodeResponse(
    @SerializedName("data")
    val `data`: List<PostalcodeItem>,
    @SerializedName("message")
    val message: String
)