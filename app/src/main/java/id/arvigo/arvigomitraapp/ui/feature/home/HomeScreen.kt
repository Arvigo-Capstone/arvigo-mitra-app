package id.arvigo.arvigomitraapp.ui.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.arvigo.arvigomitraapp.R
import id.arvigo.arvigomitraapp.data.source.network.response.home.Product
import id.arvigo.arvigomitraapp.ui.feature.home.uistate.HomeProductState
import id.arvigo.arvigomitraapp.ui.feature.home.uistate.HomeUiState
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    navController: NavController,
) {
    HomeScreenContent()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent() {

    val viewModel: HomeViewModel = getViewModel()

    Scaffold() {
        LazyColumn(
            modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 16.dp)
        ) {
            item {
                Text(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    text = "Dashboard",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(48.dp))
                Text(text = "Total Pengunjung", style = MaterialTheme.typography.titleMedium)
                val response = viewModel.response.value

                when (response) {
                    is HomeUiState.Loading -> {
                        CircularProgressIndicator()
                    }
                    is HomeUiState.Success -> {
                        Row(
                                modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp),
                                horizontalArrangement = Arrangement.SpaceAround,
                        ) {
                            Card(
                                    modifier = Modifier
                                            .width(120.dp)
                                            .height(67.dp)
                            ) {
                                Column(
                                        modifier = Modifier
                                                .padding(12.dp)
                                ) {
                                    Text(text = "Hari ini", style = MaterialTheme.typography.titleMedium)
                                    Text(text = response.data.today.toString(), style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary,
                                    ))
                                }
                            }
                            Card(
                                    modifier = Modifier
                                            .width(120.dp)
                                            .height(67.dp)
                            ) {
                                Column(
                                        modifier = Modifier
                                                .padding(12.dp)
                                ) {
                                    Text(text = "Bulan ini", style = MaterialTheme.typography.titleMedium)
                                    Text(text = response.data.thisMonth.toString(), style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary,
                                    ))
                                }
                            }
                            Card(
                                    modifier = Modifier
                                            .width(120.dp)
                                            .height(67.dp)
                            ) {
                                Column(
                                        modifier = Modifier
                                                .padding(12.dp)
                                ) {
                                    Text(text = "Bulan lalu", style = MaterialTheme.typography.titleMedium)
                                    Text(text = response.data.lastMonth.toString(), style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary,
                                    ))
                                }
                            }
                        }
                    }
                    is HomeUiState.Failure -> {
                        Text(text = response.error.message.toString(), style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                        ))
                    }

                    else -> {}
                }
                Spacer(modifier = Modifier.height(48.dp))
                Card() {
                    Row(
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column() {
                            Text(text = "Link Toko Produk", style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Gray
                            ))
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Submit Link Toko Baru", style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.SemiBold,
                                ))
                        }
                        Button(
                            onClick = { /*TODO*/ },
                            shape = RoundedCornerShape(12),
                            modifier = Modifier
                                .width(120.dp),
                        ) {
                            Text(text = "Submit", style = MaterialTheme.typography.titleMedium.copy(color = Color.White, fontWeight = FontWeight.SemiBold))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
                Card() {
                    Row(
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column() {
                            Text(text = "Promosi", style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Gray,
                            ))
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Tingkatkan Pengunjung", style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.SemiBold,
                                ))
                        }
                        Button(
                            onClick = { /*TODO*/ },
                            shape = RoundedCornerShape(12),
                            modifier = Modifier
                                .width(120.dp),
                        ) {
                            Text(text = "Harga", style = MaterialTheme.typography.titleMedium.copy(color = Color.White, fontWeight = FontWeight.SemiBold))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(48.dp))
                val responseProduct = viewModel.responseProduct.value
                if (responseProduct is HomeProductState.Success) {
                    if (responseProduct.data.isNotEmpty()) {
                        Text(text = "Total Pengunjung", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(16.dp))
                        responseProduct.data.forEach {
                            HomeProductCard(
                                product = it,
                                onClick = { /*TODO*/ },
                            )
                        }
                    } else {
                        Text(text = "Belum ada produk yang ditambahkan.", style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                        ))
                    }
                }

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeProductCard(
        product: Product,
        onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(bottom = 12.dp)
    ) {
        Row() {
            Image(
                painter = painterResource(id = R.drawable.img_logo),
                contentDescription = "",
                modifier = Modifier
                        .width(160.dp)
                        .height(160.dp),

            )
            AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                            .data(product.image)
                            .crossfade(true)
                            .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.img_logo),
                    modifier = Modifier
                            .width(160.dp)
                            .height(160.dp),
            )
            Column(
                modifier = Modifier
                        .height(160.dp)
                        .padding(12.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Rp. ${product.price}", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = product.status, style = MaterialTheme.typography.titleMedium.copy(
                    color = if (product.status == "APPROVED") {
                        Color.Green
                    } else if(product.status == "WAITING LIST") {
                        Color.Yellow
                    } else {
                        Color.Red
                    }
                ))
            }
        }
    }
}