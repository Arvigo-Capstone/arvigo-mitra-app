package id.arvigo.arvigomitraapp.data.source.network.response.add_offer


import com.google.gson.annotations.SerializedName

data class TestMarketplaceItem(
    @SerializedName("addresses_id")
    val addressesId: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("marketplace_id")
    val marketplaceId: Int
)