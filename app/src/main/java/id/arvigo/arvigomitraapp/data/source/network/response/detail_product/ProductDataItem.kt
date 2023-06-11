package id.arvigo.arvigomitraapp.data.source.network.response.detail_product


import com.google.gson.annotations.SerializedName

data class ProductDataItem(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("marketplaces")
    val marketplaces: List<Marketplace>,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("subscription")
    val subscription: String
)