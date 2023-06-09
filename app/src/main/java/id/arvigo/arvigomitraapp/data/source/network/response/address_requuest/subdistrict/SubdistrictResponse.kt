package id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.subdistrict


import com.google.gson.annotations.SerializedName

data class SubdistrictResponse(
    @SerializedName("data")
    val `data`: List<SubdistrictItem>,
    @SerializedName("message")
    val message: String
)