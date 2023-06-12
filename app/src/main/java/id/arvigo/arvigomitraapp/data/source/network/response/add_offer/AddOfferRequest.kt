package id.arvigo.arvigomitraapp.data.source.network.response.add_offer


import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import java.io.File

data class AddOfferRequest(
        @SerializedName("description")
    val description: String,
        @SerializedName("images")
    val images: List<File>,
        @SerializedName("detail_product_marketplaces")
    val marketplaces: ArrayList<TestMarketplaceItem>,
        @SerializedName("name")
    val name: String,
        @SerializedName("price")
    val price: Int,
        @SerializedName("merchant_id")
    val merchantId: Int,
        @SerializedName("product_id")
    val productId: Int,
)