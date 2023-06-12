package id.arvigo.arvigomitraapp.data.source.network.response.add_offer


import com.google.gson.annotations.SerializedName

data class DetailMarketplace(
    @SerializedName("address")
    val address: Any?,
    @SerializedName("clicked")
    val clicked: Int,
    @SerializedName("link")
    val link: Any?,
    @SerializedName("name")
    val name: String
)