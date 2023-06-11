package id.arvigo.arvigomitraapp.data.source.network

import id.arvigo.arvigomitraapp.data.source.network.request.LoginRequest
import id.arvigo.arvigomitraapp.data.source.network.response.LoginResponse
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.city.CityResponse
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.district.DistrictResponse
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.postal_code.PostalcodeResponse
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.provice.ProviceResponse
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.subdistrict.SubdistrictResponse
import id.arvigo.arvigomitraapp.data.source.network.response.detail_product.DeleteResponse
import id.arvigo.arvigomitraapp.data.source.network.response.detail_product.ProductDetailResponse
import id.arvigo.arvigomitraapp.data.source.network.response.home.HomeResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("/v1/auth/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("/v1/auth/register-partner")
    fun register(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @GET("/v1/location/provinces")
    fun getProvice() : ProviceResponse

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

    @GET("/v1/merchant-app/home")
    suspend fun getHome(
            @Header("Authorization") token: String,
    ) : HomeResponse

    @GET("/v1/merchant-app/product/{id}")
    suspend fun getProductDetail(
            @Header("Authorization") token: String,
            @Path("id") id: String
    ) : ProductDetailResponse

    @GET("/v1/products/{id}")
    suspend fun deleteProduct(
            @Header("Authorization") token: String,
            @Path("id") id: String
    ) : DeleteResponse
}