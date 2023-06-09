package id.arvigo.arvigomitraapp

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.arvigo.arvigomitraapp.ui.feature.LoginScreen
import id.arvigo.arvigomitraapp.ui.feature.add_offer.AddOfferScreen
import id.arvigo.arvigomitraapp.ui.feature.add_offer.initial_page.InitialPageScreen
import id.arvigo.arvigomitraapp.ui.feature.home.HomeScreen
import id.arvigo.arvigomitraapp.ui.feature.product_detail.ProductDetailScreen
import id.arvigo.arvigomitraapp.ui.feature.profile.ProfileScreen
import id.arvigo.arvigomitraapp.ui.feature.register.RegisterScreen
import id.arvigo.arvigomitraapp.ui.feature.splash.SplashScreen
import id.arvigo.arvigomitraapp.ui.feature.subcription.SubscriptionPayment
import id.arvigo.arvigomitraapp.ui.feature.subcription.SubscriptionScreen
import id.arvigo.arvigomitraapp.ui.feature.subcription.pick_product.PickProductScreen
import id.arvigo.arvigomitraapp.ui.navigation.NavigationItem
import id.arvigo.arvigomitraapp.ui.navigation.PRODUCT_ID
import id.arvigo.arvigomitraapp.ui.navigation.Screen
import id.arvigo.arvigomitraapp.ui.navigation.UNIQUE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetMitraApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            val excludedRoutes = listOf(
                Screen.Login.route,
                Screen.Register.route,
                Screen.Splash.route,
                    Screen.ProductDetail.route,
                    Screen.AddOffer.route,
                Screen.InitialProduct.route,
                Screen.Subscription.route,
                Screen.Payment.route,
                Screen.PickProduct.route,
            )
            if (currentRoute !in excludedRoutes) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(it)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(
                    navController = navController,
                )
            }
//            composable(Screen.Onboarding.route) {
//                OnboardingScreen(
//                    navController = navController,
//                )
//            }
            composable(Screen.Home.route) {
                HomeScreen(
                    navController = navController,
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    navController = navController
                )
            }
            //auth
            composable(Screen.Login.route) {
                LoginScreen(
                    navController = navController,
                )
            }
            composable(Screen.Register.route) {
                RegisterScreen(
                    navController = navController,
                )
            }
            //product detail
            composable(
                    route = Screen.ProductDetail.route,
                    arguments = listOf(
                            navArgument(PRODUCT_ID) {
                                type = NavType.IntType
                            }
                    )
            ) {
                Log.d("Args", it.arguments?.getInt(PRODUCT_ID).toString())
                val productId = it.arguments?.getInt(PRODUCT_ID).toString()
                ProductDetailScreen(
                        navController = navController,
                        productId = productId,
                )
            }
            composable(
                route = Screen.AddOffer.route,
                arguments = listOf(
                    navArgument(PRODUCT_ID) {
                        type = NavType.IntType
                    }
                )
                ) {
                Log.d("Args", it.arguments?.getInt(PRODUCT_ID).toString())
                val productId = it.arguments?.getInt(PRODUCT_ID)
                AddOfferScreen(
                        navController = navController,
                        initialId = productId,
                )
            }
            composable(
                route = Screen.InitialProduct.route,
                arguments = listOf(
                    navArgument(PRODUCT_ID) {
                        type = NavType.StringType
                    }
                )
            ) {
                Log.d("Args", it.arguments?.getString(PRODUCT_ID).toString())
                val productId = it.arguments?.getString(PRODUCT_ID)
                InitialPageScreen(
                    navController = navController,
                    id = productId ?: "1",
                )
            }
            composable(
                route = Screen.Subscription.route,
                ) {
                SubscriptionScreen(
                    navController = navController,
                )
            }
            composable(
                route = Screen.Payment.route,
                arguments = listOf(
                    navArgument(UNIQUE) {
                        type = NavType.IntType
                    },
                    navArgument(PRODUCT_ID) {
                        type = NavType.IntType
                    }
                )
            ) {
                Log.d("Args", it.arguments?.getString(UNIQUE).toString())
                val uniq = it.arguments?.getInt(UNIQUE)
                val productId = it.arguments?.getInt(PRODUCT_ID)
                SubscriptionPayment(
                    navController = navController,
                    uniqueCode = uniq!!,
                    productId = productId!!,
                )
            }
            composable(Screen.PickProduct.route) {
                PickProductScreen(
                    navController = navController,
                )
            }

        }
    }
}



@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = "Home",
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title ="Profile",
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )
        NavigationBar() {
            navigationItems.map { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}