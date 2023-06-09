package id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.subdistrict


import com.google.gson.annotations.SerializedName

data class SubdistrictItem(
    @SerializedName("subdistrict_id")
    val subdistrictId: Int,
    @SerializedName("subdistrict_name")
    val subdistrictName: String
)