package id.arvigo.arvigomitraapp.data.source.network.response.intial_product


import com.google.gson.annotations.SerializedName

data class InitialData(
    @SerializedName("brand_name")
    val brandName: String,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("is_subscription_active")
    val isSubscriptionActive: Boolean,
    @SerializedName("is_wishlisted")
    val isWishlisted: Boolean,
    @SerializedName("link_external")
    val linkExternal: String,
    @SerializedName("marketplaces")
    val marketplaces: Any,
    @SerializedName("name")
    val name: String,
    @SerializedName("recommendation_product")
    val recommendationProduct: Any,
    @SerializedName("rejected_note")
    val rejectedNote: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("tags")
    val tags: Any,
    @SerializedName("variants")
    val variants: List<Variant>
)