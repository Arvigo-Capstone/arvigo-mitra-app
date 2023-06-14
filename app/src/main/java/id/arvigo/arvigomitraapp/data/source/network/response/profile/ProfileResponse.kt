package id.arvigo.arvigomitraapp.data.source.network.response.profile


import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("data")
    val `data`: ProfileData,
    @SerializedName("message")
    val message: String
)