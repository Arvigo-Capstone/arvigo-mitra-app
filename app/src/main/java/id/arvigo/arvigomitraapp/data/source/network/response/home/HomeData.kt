package id.arvigo.arvigomitraapp.data.source.network.response.home


import com.google.gson.annotations.SerializedName

data class HomeData(
        @SerializedName("products")
        val products: List<Product>?,
        @SerializedName("visitors")
        val visitors: Visitors
)