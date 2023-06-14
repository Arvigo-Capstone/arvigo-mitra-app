package id.arvigo.arvigomitraapp.data.source.network.response.profile


import com.google.gson.annotations.SerializedName

data class ProfileData(
    @SerializedName("address")
    val address: Address,
    @SerializedName("addresses_id")
    val addressesId: Int,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("date_of_birth")
    val dateOfBirth: Any,
    @SerializedName("email")
    val email: String,
    @SerializedName("face_shape")
    val faceShape: Any,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_complete_face_test")
    val isCompleteFaceTest: Boolean,
    @SerializedName("is_complete_personality_test")
    val isCompletePersonalityTest: Boolean,
    @SerializedName("is_subscription_active")
    val isSubscriptionActive: Boolean,
    @SerializedName("is_verified")
    val isVerified: Boolean,
    @SerializedName("merchant_id")
    val merchantId: Int,
    @SerializedName("personality")
    val personality: Personality,
    @SerializedName("place_of_birth")
    val placeOfBirth: String,
    @SerializedName("role_id")
    val roleId: Int,
    @SerializedName("role_name")
    val roleName: String
)