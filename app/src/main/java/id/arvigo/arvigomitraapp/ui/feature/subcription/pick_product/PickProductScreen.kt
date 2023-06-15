package id.arvigo.arvigomitraapp.ui.feature.subcription.pick_product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.arvigo.arvigomitraapp.R
import id.arvigo.arvigomitraapp.ui.component.ProductItemCard
import id.arvigo.arvigomitraapp.ui.feature.home.HomeProductCard
import id.arvigo.arvigomitraapp.ui.feature.home.HomeViewModel
import id.arvigo.arvigomitraapp.ui.feature.home.uistate.HomeProductState
import id.arvigo.arvigomitraapp.ui.navigation.Screen
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickProductScreen(
    navController: NavController,
) {

    val viewModel: HomeViewModel = getViewModel()

    Scaffold {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(180.dp),
            state = LazyGridState(),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(it)
        ){

            val responseProduct = viewModel.responseProduct.value
            if (responseProduct is HomeProductState.Success) {
                if (responseProduct.data.isNotEmpty()) {
                    items(responseProduct.data){ data ->
                        val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 2)
                        if (data.status == "APPROVED") {
                            ProductItemCard(
                                name = data.name,
                                image = data.image,
                                brand = "from Arvigo",
                                itemSize = itemSize,
                                onClick = {
                                    var uniqueCode = (100 until 1000).random()
                                    navController.navigate(Screen.Payment.createRoute(uniqueCode,data.id)) {
                                        popUpTo(Screen.PickProduct.route) {
                                            inclusive = true
                                        }
                                    }
                                } )
                        }
                    }
                }

            }
        }
    }

}