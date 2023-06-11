package id.arvigo.arvigomitraapp.data.source.network.response.test


import com.google.gson.annotations.SerializedName
import id.arvigo.arvigomitraapp.data.source.network.response.home.Product

data class Data(
        @SerializedName("products")
    val products: List<Product>,
        @SerializedName("visitors")
    val visitors: Visitors
)