package id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.city


import com.google.gson.annotations.SerializedName

data class CityItem(
    @SerializedName("city_id")
    val cityId: Int,
    @SerializedName("city_name")
    val cityName: String
)