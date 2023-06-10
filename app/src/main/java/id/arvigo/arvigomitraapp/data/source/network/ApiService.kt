package id.arvigo.arvigomitraapp.data.source.network

import id.arvigo.arvigomitraapp.data.source.network.request.LoginRequest
import id.arvigo.arvigomitraapp.data.source.network.response.LoginResponse
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.city.CityResponse
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.district.DistrictResponse
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.postal_code.PostalcodeResponse
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.provice.ProvincesResponse
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.subdistrict.SubdistrictResponse
import id.arvigo.arvigomitraapp.ui.feature.register.api.RegisterRequest
import id.arvigo.arvigomitraapp.ui.feature.register.api.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("/v1/auth/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("/v1/auth/register-partner")
    fun register(
        @Body request: RegisterRequest
    ): Call<RegisterResponse>

    @GET("/v1/location/provinces")
    suspend fun getProvinces() : ProvincesResponse

    @GET("/v1/location/cities?province_id={id}")
    fun getCity(
        @Path ("id") id : Int
    ) : CityResponse

    @GET("/v1/location/districts?city_id={id}")
    fun getDistrict(
        @Path ("id") id : Int
    ) : DistrictResponse

    @GET("/v1/location/subdistricts?district_id={id}")
    fun getSubdistrict(
        @Path ("id") id : Int
    ) : SubdistrictResponse

    @GET("/v1/location/postal_codes?subdistrict_id={id}")
    fun getPostalcode(
        @Path ("id") id : Int
    ) : PostalcodeResponse

}