package id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.provice


import com.google.gson.annotations.SerializedName

data class ProvinceItem(
    @SerializedName("province_id")
    val provinceId: Int,
    @SerializedName("province_name")
    val provinceName: String
)