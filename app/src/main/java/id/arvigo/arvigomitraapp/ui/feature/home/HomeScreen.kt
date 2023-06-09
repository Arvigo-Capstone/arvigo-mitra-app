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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.arvigo.arvigomitraapp.R

@Composable
fun HomeScreen(
    navController: NavController,
) {
    HomeScreenContent()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent() {
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
                            Text(text = "123", style = MaterialTheme.typography.titleLarge)
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
                            Text(text = "123", style = MaterialTheme.typography.titleLarge)
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
                            Text(text = "123", style = MaterialTheme.typography.titleLarge)
                        }
                    }
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
                Text(text = "Total Pengunjung", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))
                val index = 5
                for (i in 0..index) {
                    HomeProductCard()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeProductCard() {
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
            Column(
                modifier = Modifier
                    .height(160.dp)
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = "Nama Produk", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Rp. 100.000", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Approved", style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Green,
                ))
            }
        }
    }
}