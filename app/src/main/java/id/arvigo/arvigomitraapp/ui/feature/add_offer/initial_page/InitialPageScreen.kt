package id.arvigo.arvigomitraapp.ui.feature.add_offer.initial_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import id.arvigo.arvigomitraapp.ui.component.ProductItemCard
import id.arvigo.arvigomitraapp.ui.feature.add_offer.AddOfferViewModel
import id.arvigo.arvigomitraapp.ui.navigation.Screen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitialPageScreen(
    navController: NavController,
    id: String,
) {
    val viewModel: AddOfferViewModel = getViewModel()
    val lifecycle : Lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(key1 = Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.getInitialPrduct(id)
            }
        }
    }

    Scaffold(
        modifier = Modifier,
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = "Produk Dari Arvigo")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back",
                        )
                    }
                },
            )
        }
    ) {
        val response = viewModel.response.value

        when(response) {
            is InitialPageState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(180.dp),
                    state = LazyGridState(),
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .padding(it)
                ){
                    items(response.data){ data ->
                        val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 2)
                        ProductItemCard(
                            name = data.name ,
                            image = data.images[0],
                            brand = data.brandName,
                            itemSize = itemSize,
                            onClick = {
                                navController.navigate(Screen.AddOffer.createRoute(data.id)) {
                                    popUpTo(Screen.InitialProduct.route) {
                                        inclusive = true
                                    }
                                }
                            } )
                    }
                }
            }
            is InitialPageState.Failure -> {
                Text(text = response.error.message ?: "Unknown Error")
            }
            InitialPageState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center)
                )
            }
            InitialPageState.Empty -> {
                Text(text = "Empty Data")
            }
        }
    }
}