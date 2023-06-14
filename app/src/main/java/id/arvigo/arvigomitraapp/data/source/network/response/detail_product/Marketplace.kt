package id.arvigo.arvigomitraapp.data.source.network.response.detail_product


import com.google.gson.annotations.SerializedName

data class Marketplace(
    @SerializedName("address")
    val address: Any,
    @SerializedName("clicked")
    val clicked: Int,
    @SerializedName("link")
    val link: Any,
    @SerializedName("name")
    val name: String
)