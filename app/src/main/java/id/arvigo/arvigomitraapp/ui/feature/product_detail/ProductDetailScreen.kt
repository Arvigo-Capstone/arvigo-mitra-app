package id.arvigo.arvigomitraapp.ui.feature.product_detail

import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import id.arvigo.arvigomitraapp.data.source.network.response.detail_product.Marketplace
import id.arvigo.arvigomitraapp.ui.component.ProductImageSlider
import id.arvigo.arvigomitraapp.ui.feature.home.HomeProductCard
import id.arvigo.arvigomitraapp.ui.feature.home.uistate.HomeProductState
import id.arvigo.arvigomitraapp.ui.feature.product_detail.uistate.ProductDetailState
import id.arvigo.arvigomitraapp.ui.feature.product_detail.uistate.ProductStoreState
import id.arvigo.arvigomitraapp.ui.navigation.Screen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import androidx.activity.compose.rememberLauncherForActivityResult as rememberLauncherForActivityResult1

const val APPROVED = "APPROVED"
const val REJECTED = "REJECTED"
const val WAITINGLIST = "WAITING LIST"
const val SUBCRIBED = "SUBCRIBED"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
        navController: NavController,
        productId: String,
) {

    val viewModel: ProductDetailViewModel = getViewModel()
    val lifecycle : Lifecycle = LocalLifecycleOwner.current.lifecycle
    val idState = remember { mutableStateOf("") }
    val openDialog = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                idState.value = productId
                viewModel.getProductDetail(productId = productId)
            }
        }
    }

    Scaffold(

            modifier = Modifier,
            topBar = {
                SmallTopAppBar(
                        title = {
                            Text(text = "Detail Penawaran")
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
                        actions = {
                            TextButton(onClick = { /*TODO*/ }) {
                                Text(text = "Edit")
                            }
                        }
                )
            }
    ) {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
        ) {

            val response = viewModel.response.value

            when(response) {
                is ProductDetailState.Loading -> {
                    CircularProgressIndicator(
                            modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentSize(align = Alignment.Center)
                    )
                }
                is ProductDetailState.Success -> {
                    LazyColumn(
                            modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(3.8f)
                    ) {
                        item {
                            ProductImageSlider(
                                    imageData = response.data.images,
                            )
                            Column(
                                    modifier = Modifier
                                            .padding(horizontal = 16.dp, vertical = 16.dp)
                                            .fillMaxWidth()
                            ) {
                                if (response.data.status == APPROVED) {
                                    OutlinedButton(
                                            modifier = Modifier
                                                    .width(160.dp)
                                                    .height(48.dp),
                                            onClick = {
                                                // TODO: ERROR WHEN CLICKED
                                                //navController.navigate(Screen.RecommendationStore.createRoute(idState.value))
                                            },
                                            shape = MaterialTheme.shapes.small,
                                            border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onPrimary),
                                    ) {
                                        Text(text = response.data.status, style = MaterialTheme.typography.titleMedium.copy(
                                                color = MaterialTheme.colorScheme.onPrimary,
                                        ))
                                    }
                                } else if (response.data.status == REJECTED) {
                                    OutlinedButton(
                                            modifier = Modifier
                                                    .width(160.dp)
                                                    .height(48.dp),
                                            onClick = {
                                                // TODO: ERROR WHEN CLICKED
                                                //navController.navigate(Screen.RecommendationStore.createRoute(idState.value))
                                            },
                                            shape = MaterialTheme.shapes.small,
                                            border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.error),
                                    ) {
                                        Text(text = response.data.status, style = MaterialTheme.typography.titleMedium.copy(
                                                color = MaterialTheme.colorScheme.error,
                                        ))
                                    }
                                } else if (response.data.status == SUBCRIBED) {
                                    Button(
                                            modifier = Modifier
                                                    .width(160.dp)
                                                    .height(48.dp),
                                            onClick = {
                                                // TODO: ERROR WHEN CLICKED
                                                //navController.navigate(Screen.RecommendationStore.createRoute(idState.value))
                                            },
                                            shape = MaterialTheme.shapes.small,
                                    ) {
                                        Text(text = response.data.status, style = MaterialTheme.typography.titleMedium.copy(
                                                color = MaterialTheme.colorScheme.primary,
                                        ))
                                    }
                                } else {
                                    OutlinedButton(
                                            modifier = Modifier
                                                    .width(160.dp)
                                                    .height(48.dp),
                                            onClick = {
                                                // TODO: ERROR WHEN CLICKED
                                                //navController.navigate(Screen.RecommendationStore.createRoute(idState.value))
                                            },
                                            shape = MaterialTheme.shapes.small,
                                            border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onSecondary),
                                    ) {
                                        Text(text = response.data.status, style = MaterialTheme.typography.titleMedium.copy(
                                                color =MaterialTheme.colorScheme.onSecondary,
                                        ))
                                    }
                                }

                                Row(
                                        modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(top = 12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(text = "Rp. ${response.data.price}", style = MaterialTheme.typography.headlineSmall.copy(
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.primary,
                                    ))
                                    IconButton(onClick = {

                                    }) {
                                        Icon(imageVector = Icons.Default.Share, contentDescription = "")
                                    }
                                }
                                Text(text = response.data.name, style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.SemiBold,
                                ))
                                Spacer(modifier = Modifier.height(32.dp))
                                Text(text = "Deskripsi", style = MaterialTheme.typography.titleLarge)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = response.data.description, style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                        item {
                            Column(
                                    modifier = Modifier
                                            .padding(16.dp)
                            ) {
                                Text(text = "Toko", style = MaterialTheme.typography.titleLarge)
                                Spacer(modifier = Modifier.height(8.dp))
                                val resMarket = viewModel.resMarketplace.value
                                if (resMarket is ProductStoreState.Success) {
                                    if (resMarket.data.isNotEmpty()) {
                                        resMarket.data.forEach {
                                            MarketplaceCard(marketplace = it, onClick = {} )
                                        }
                                    } else {
                                        Text(text = "Tidak ada marketplace yang tertaut.", style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                        ))
                                    }
                                }
                            }
                        }
                    }

                    Surface(
                            modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.3f)
                                    .padding(horizontal = 15.dp, vertical = 4.dp)
                    ) {
                        val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 2)
                        val context = LocalContext.current
                        val openDeepAR = rememberLauncherForActivityResult1(
                                contract = ActivityResultContracts.StartActivityForResult()
                        ) {
                        }
                        Button(
                                modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                onClick = {
                                    openDialog.value = true
                                },
                                shape = MaterialTheme.shapes.small,
                                colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.error,
                                )
                        )
                        {
                            Text(text = "Hapus", style = MaterialTheme.typography.titleMedium.copy(
                                    color = Color.White,
                            ))
                        }
                    }

                    if (openDialog.value) {
                        AlertDialog(
                                onDismissRequest = {
                                    openDialog.value = false
                                },
                                title = {
                                    Text(text = "Hapus Penawaran")
                                },
                                text = {
                                    Text(text = "Apakah kamu yankin ingin menghapus penawaran ini?")
                                },
                                confirmButton = {
                                    TextButton(
                                            onClick = {
                                                openDialog.value = false
                                                viewModel.deleteProuduct(
                                                        productId = response.data.id.toString(),
                                                )
                                                navController.popBackStack()
                                            }
                                    ) {
                                        Text("Hapus")
                                    }
                                },
                                dismissButton = {
                                    TextButton(
                                            onClick = {
                                                openDialog.value = false
                                            }
                                    ) {
                                        Text("Batal")
                                    }
                                }
                        )
                    }
                }
                is ProductDetailState.Failure -> {
                    Text(text = response.error.message ?: "Unknown Error")
                }
                ProductDetailState.Empty -> {
                    Box(
                            modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(3.8f)
                    ) {
                        Text(text = "Empty", modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }

}

@Composable
fun MarketplaceCard(
        marketplace: Marketplace,
        onClick: () -> Unit,
) {
    Row(
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
                horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimary,
            )
            Text(text = marketplace.name, style = MaterialTheme.typography.titleMedium)
        }
        Row(
                horizontalArrangement = Arrangement.Start
        ) {
            Text(text = marketplace.clicked.toString())
            Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "",
            )
        }
    }
}

