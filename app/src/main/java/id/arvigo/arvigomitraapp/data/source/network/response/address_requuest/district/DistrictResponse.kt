package id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.district


import com.google.gson.annotations.SerializedName

data class DistrictResponse(
    @SerializedName("data")
    val `data`: List<DistrictItem>,
    @SerializedName("message")
    val message: String
)