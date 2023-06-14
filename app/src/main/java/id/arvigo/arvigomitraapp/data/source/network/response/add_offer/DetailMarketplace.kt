package id.arvigo.arvigomitraapp.data.source.network.response.add_offer


import com.google.gson.annotations.SerializedName

data class DetailMarketplace(
    @SerializedName("addresses_id")
    val addressId: Any?,
    @SerializedName("marketplace_id")
    val marketplaceId: Any?,
    @SerializedName("link")
    val link: Any?,
)