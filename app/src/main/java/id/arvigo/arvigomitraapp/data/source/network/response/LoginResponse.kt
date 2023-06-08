package id.arvigo.arvigomitraapp.data.source.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: LoginResult,
)


data class LoginResult(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("token")
    val token: String,
)
