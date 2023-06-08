package id.arvigo.arvigomitraapp.ui.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.arvigo.arvigomitraapp.R
import id.arvigo.arvigomitraapp.ui.navigation.Screen
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(
    navController: NavController,
) {
    val viewModel: SplashViewModel = getViewModel()
    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()
    val isOnboardingVisited by viewModel.isOnboardingVisited.collectAsState()

    LaunchedEffect(isUserLoggedIn) {

        delay(3000)

        if (isUserLoggedIn) {
            // User is logged in, navigate to the home screen
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }
            }
        } else {
            // User is not logged in, navigate to the login screen
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }
            }
        }

//        if (isOnboardingVisited){
//            if (isUserLoggedIn) {
//                // User is logged in, navigate to the home screen
//                navController.navigate(Screen.Home.route) {
//                    popUpTo(Screen.Splash.route) {
//                        inclusive = true
//                    }
//                }
//            } else {
//                // User is not logged in, navigate to the login screen
//                navController.navigate(Screen.Login.route) {
//                    popUpTo(Screen.Splash.route) {
//                        inclusive = true
//                    }
//                }
//            }
//        } else {
//            navController.navigate(Screen.Onboarding.route) {
//                popUpTo(Screen.Splash.route) {
//                    inclusive = true
//                }
//            }
//        }
//        navController.navigate(Screen.Home.route) {
//            popUpTo(Screen.Splash.route) {
//                inclusive = true
//            }
//        }



    }

    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            BrandLogo()
        }
    }


}

@Composable
fun BrandLogo() {
    Image(
        painter = painterResource(id = R.drawable.img_logo),
        contentDescription = null,
        modifier = Modifier
            .width(400.dp)
            .height(120.dp)
    )
}

@Preview
@Composable
fun SplashPrev() {
    BrandLogo()
}