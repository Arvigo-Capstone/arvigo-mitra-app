package id.arvigo.arvigomitraapp.data.source.network.response.home


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("clicked")
    val clicked: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("status")
    val status: String
)