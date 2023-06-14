package id.arvigo.arvigomitraapp.data.source.network.response.profile


import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("city")
    val city: String,
    @SerializedName("district")
    val district: String,
    @SerializedName("postal_code")
    val postalCode: Int,
    @SerializedName("province")
    val province: String,
    @SerializedName("street")
    val street: String,
    @SerializedName("sub_district")
    val subDistrict: String
)