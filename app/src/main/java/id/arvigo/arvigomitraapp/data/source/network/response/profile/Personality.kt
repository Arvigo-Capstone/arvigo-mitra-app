package id.arvigo.arvigomitraapp.data.source.network.response.profile


import com.google.gson.annotations.SerializedName

data class Personality(
    @SerializedName("percentage_of_agreeable")
    val percentageOfAgreeable: Int,
    @SerializedName("percentage_of_conscientious")
    val percentageOfConscientious: Int,
    @SerializedName("percentage_of_extraversion")
    val percentageOfExtraversion: Int,
    @SerializedName("percentage_of_neurotic")
    val percentageOfNeurotic: Int,
    @SerializedName("percentage_of_openess")
    val percentageOfOpeness: Int
)