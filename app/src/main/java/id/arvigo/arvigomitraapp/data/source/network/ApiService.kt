package id.arvigo.arvigomitraapp.data.source.network

import id.arvigo.arvigomitraapp.data.source.network.request.LoginRequest
import id.arvigo.arvigomitraapp.data.source.network.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/v1/auth/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

}