package id.arvigo.arvigomitraapp.ui.navigation

const val PRODUCT_ID = "product_id"

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Splash : Screen("splash")
    object ProductDetail : Screen("product_detail/{$PRODUCT_ID}") {
        fun createRoute(productId: Int) = "product_detail/$productId"
    }
}
