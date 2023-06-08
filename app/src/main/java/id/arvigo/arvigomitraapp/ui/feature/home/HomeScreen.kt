package id.arvigo.arvigomitraapp.ui.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

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
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Dashboard",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                ),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(48.dp))
            Text(text = "Total Pengunjung", style = MaterialTheme.typography.titleLarge)
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
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column() {
                    Text(text = "Link Toko Produk", style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray
                    ))
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Submit Link Toko Baru", style = MaterialTheme.typography.titleMedium.copy(
                        ))
                }
                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(12),
                ) {
                    Text(text = "Submit", style = MaterialTheme.typography.titleMedium.copy(color = Color.White, fontWeight = FontWeight.SemiBold))
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column() {
                    Text(text = "Promosi", style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray,
                    ))
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Tingkatkan Pengunjung", style = MaterialTheme.typography.titleMedium.copy(
                        ))
                }
                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(12),
                ) {
                    Text(text = "Harga", style = MaterialTheme.typography.titleMedium.copy(color = Color.White, fontWeight = FontWeight.SemiBold))
                }
            }
            Spacer(modifier = Modifier.height(48.dp))
            Text(text = "Total Pengunjung", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}