package id.arvigo.arvigomitraapp.ui.feature.register.api

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("store_name")
    val storeName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("password_confirmation")
    val passwordConfirmation: String,
    @SerializedName("street")
    val street: String,
    @SerializedName("province_id")
    val provinceId: Int,
    @SerializedName("city_id")
    val cityId: Int,
    @SerializedName("district_id")
    val districtId: Int,
    @SerializedName("subdistrict_id")
    val subDistrictId: Int,
    @SerializedName("postal_code_id")
    val postalCodeId: Int
)
