package id.arvigo.arvigomitraapp.data.source.network.response.intial_product


import com.google.gson.annotations.SerializedName

data class Variant(
    @SerializedName("is_primary_variant")
    val isPrimaryVariant: Boolean,
    @SerializedName("link_ar")
    val linkAr: String,
    @SerializedName("name")
    val name: String
)