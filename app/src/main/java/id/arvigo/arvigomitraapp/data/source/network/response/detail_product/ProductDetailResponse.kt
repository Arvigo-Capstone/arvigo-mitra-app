package id.arvigo.arvigomitraapp.data.source.network.response.detail_product


import com.google.gson.annotations.SerializedName

data class ProductDetailResponse(
        @SerializedName("data")
    val `data`: ProductDataItem,
        @SerializedName("message")
    val message: String
)