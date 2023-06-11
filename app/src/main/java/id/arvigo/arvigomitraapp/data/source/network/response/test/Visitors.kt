package id.arvigo.arvigomitraapp.data.source.network.response.test


import com.google.gson.annotations.SerializedName

data class Visitors(
    @SerializedName("last_month")
    val lastMonth: Int,
    @SerializedName("this_month")
    val thisMonth: Int,
    @SerializedName("today")
    val today: Int
)