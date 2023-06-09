package id.arvigo.arvigomitraapp.ui.navigation

const val PRODUCT_ID = "product_id"
const val UNIQUE = "unique_id"

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Splash : Screen("splash")
    object ProductDetail : Screen("product_detail/{$PRODUCT_ID}") {
        fun createRoute(productId: Int) = "product_detail/$productId"
    }
    object AddOffer : Screen("add_offer/{$PRODUCT_ID}") {
        fun createRoute(productId: Int) = "add_offer/$productId"
    }
    object InitialProduct : Screen("initial_product/{$PRODUCT_ID}") {
        fun createRoute(productId: String) = "initial_product/$productId"
    }
    object Subscription : Screen("subscription}")
    object PickProduct : Screen("pick_product")

    object Payment : Screen("payment/{$UNIQUE}/{$PRODUCT_ID}") {
        fun createRoute(uniqueId: Int, productId: Int) = "payment/$uniqueId/$productId"
    }
}
