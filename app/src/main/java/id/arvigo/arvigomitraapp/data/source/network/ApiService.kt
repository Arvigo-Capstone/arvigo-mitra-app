package id.arvigo.arvigomitraapp.data.source.network

import id.arvigo.arvigomitraapp.data.source.network.request.LoginRequest
import id.arvigo.arvigomitraapp.data.source.network.response.AddOfferResponse
import id.arvigo.arvigomitraapp.data.source.network.response.LoginResponse
import id.arvigo.arvigomitraapp.data.source.network.response.add_offer.AddOfferRequest
import id.arvigo.arvigomitraapp.data.source.network.response.add_offer.DetailMarketplace
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.city.CityResponse
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.district.DistrictResponse
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.postal_code.PostalcodeResponse
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.provice.ProvincesResponse
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.subdistrict.SubdistrictResponse
import id.arvigo.arvigomitraapp.ui.feature.register.api.RegisterRequest
import id.arvigo.arvigomitraapp.ui.feature.register.api.RegisterResponse
import id.arvigo.arvigomitraapp.data.source.network.response.detail_product.DeleteResponse
import id.arvigo.arvigomitraapp.data.source.network.response.detail_product.ProductDetailResponse
import id.arvigo.arvigomitraapp.data.source.network.response.home.HomeResponse
import id.arvigo.arvigomitraapp.data.source.network.response.intial_product.InitialProductResponse
import id.arvigo.arvigomitraapp.data.source.network.response.profile.ProfileResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import java.lang.reflect.Array

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

    @GET("/v1/merchant-app/home")
    suspend fun getHome(
            @Header("Authorization") token: String,
    ) : HomeResponse

    @GET("/v1/merchant-app/product/{id}")
    suspend fun getProductDetail(
            @Header("Authorization") token: String,
            @Path("id") id: String
    ) : ProductDetailResponse

    @DELETE("/v1/products/{id}")
    suspend fun deleteProduct(
            @Header("Authorization") token: String,
            @Path("id") id: String
    ) : DeleteResponse

    @GET("/v1/products/initials/category/{id}")
    suspend fun getInitialProduct(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : InitialProductResponse

    @Multipart
    @POST("/v1/products/merchants")
    suspend fun addOffer(
            @Header("Authorization") token: String,
//            @Body request: AddOfferRequest,
//            @Part images: List<MultipartBody.Part>
            @Part("name") name: String,
            @Part("description") description: String,
            @Part("price") price: Int,
            @Part("merchant_id") merchantId: Int,
            @Part("product_id") productId: Int,
            @Part("detail_product_marketplaces") marketplaces: String,
            @Part images: List<MultipartBody.Part>
    ): Call<AddOfferResponse>

    @GET("/v1/users/{userId}")
    suspend fun getProfile(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): ProfileResponse
}