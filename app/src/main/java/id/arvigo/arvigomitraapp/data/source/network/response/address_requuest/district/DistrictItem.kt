package id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.district


import com.google.gson.annotations.SerializedName

data class DistrictItem(
    @SerializedName("district_id")
    val districtId: Int,
    @SerializedName("district_name")
    val districtName: String
)