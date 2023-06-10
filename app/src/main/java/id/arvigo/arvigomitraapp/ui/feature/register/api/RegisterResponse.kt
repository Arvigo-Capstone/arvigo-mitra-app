package id.arvigo.arvigomitraapp.ui.feature.register.api

import com.google.gson.annotations.SerializedName

data class RegisterResponse (
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: RegisterResult,
)

data class RegisterResult (
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("token")
    val token: String,
)