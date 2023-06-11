package id.arvigo.arvigomitraapp.data.source.network.response.test


import com.google.gson.annotations.SerializedName

data class TestResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String
)