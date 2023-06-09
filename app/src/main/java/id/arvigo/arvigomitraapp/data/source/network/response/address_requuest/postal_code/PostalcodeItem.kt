package id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.postal_code


import com.google.gson.annotations.SerializedName

data class PostalcodeItem(
    @SerializedName("postal_code")
    val postalCode: Int,
    @SerializedName("postal_code_id")
    val postalCodeId: Int
)