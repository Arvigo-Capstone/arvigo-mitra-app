package id.arvigo.arvigomitraapp.data.source.network.request


import com.google.gson.annotations.SerializedName

data class SubscriptionRequest(
    @SerializedName("bank")
    val bank: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("product_id")
    val productId: List<Int>,
    @SerializedName("unique_code")
    val uniqueCode: Int
)