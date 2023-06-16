package id.arvigo.arvigomitraapp.ui.feature.subcription

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.arvigo.arvigomitraapp.R
import id.arvigo.arvigomitraapp.data.source.network.request.SubscriptionRequest
import id.arvigo.arvigomitraapp.ui.component.PrimaryButton
import id.arvigo.arvigomitraapp.ui.navigation.Screen
import org.koin.androidx.compose.get

const val IMAGE_BCA = "https://marcopolis.net/images/stories/indonesia-report/2016/companies/Bank_Central_Asia.jpg"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionPayment(
    navController: NavController,
    uniqueCode: Int,
    productId: Int,
) {
    val viewModel: SubscriptionViewModel = get()
    val isLoading = viewModel.isLoading.value // Collect the isLoading state as a Compose state
    val responseMessage by viewModel.responseMessage.collectAsState()
    val context = LocalContext.current
    val isPostSuccess =  viewModel.isPostSuccess.value
    val isPostFailed =   viewModel.isPostFailed.value
    val isEnableButton = remember {
        mutableStateOf(false)
    }
    val listProductId = remember {
        mutableStateListOf<Int>()
    }

    Scaffold(
        modifier = Modifier,
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = "Langganan")
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
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "Pilih Produk Untuk Promosi")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = {
                            navController.navigate(Screen.PickProduct.route)
                        },
                        modifier = Modifier
                            .size(80.dp),
                        shape = MaterialTheme.shapes.large,
                        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "", modifier = Modifier
                                .size(40.dp))

                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Metode Pembayaran",
                        modifier = Modifier,
                    )
                    CustomRowOne(image = R.drawable.mandiri, textOne = "11000929443")
                    CustomDivider()
                    CustomRowOne(image = R.drawable.bca, textOne = "11000929443")
                    CustomDivider()
                    CustomRowOne(image = R.drawable.bni, textOne = "11000929443")
                    CustomDivider()
                    CustomRowOne(image = R.drawable.bri, textOne = "11000929443")
                    CustomDivider()
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Rangkuman Pembayaran",
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 10.dp),
                    )
                    CustomLineTwo(
                        textOne = "Premium",
                        textTwo = "Rp. 20.000",
                        arrangement = Arrangement.Top
                    )
                    CustomLineTwo(
                        textOne = "Kode Unik",
                        textTwo = uniqueCode.toString(),
                        arrangement = Arrangement.Top
                    )
                    CustomLineTwo(
                        textOne = "Total Harga",
                        textTwo = "Rp. 212.000",
                        arrangement = Arrangement.Bottom
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    if (isPostSuccess) {
                        Snackbar(
                            modifier = Modifier.padding(8.dp),
                            contentColor = Color.White,
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                        ) { androidx.compose.material.Text(text = "Pembayaran berhasil dilakukan!", style = TextStyle(
                            color = Color.White,
                        )
                        ) }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    PrimaryButton(
                        onClick = {
                            listProductId.add(productId)
                            val request = SubscriptionRequest(
                                price = 20000,
                                uniqueCode = uniqueCode,
                                message = viewModel.responseMessage.toString(),
                                bank = "BCA",
                                productId = listProductId
                            )
                            viewModel.subscribe(request)
                            Log.d("neo-tag", responseMessage.toString())
                        },
                        title = "Bayar",
                        enable = productId != 0,
                    )
                }
                }
        }
        //Show Toasts
        LaunchedEffect(responseMessage) {
            responseMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                viewModel.clearResponseMessage()
            }
        }
    }
}

@Composable
fun CustomLineTwo(
    textOne: String,
    textTwo: String,
    arrangement: Arrangement.Vertical
) {
    Column(
        verticalArrangement = arrangement
    ) {
        Text(
            text = textOne,
            style = MaterialTheme.typography.titleSmall.copy(color = Color.Gray),
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Text(
            text = textTwo,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 10.dp),
        )
        CustomDivider()
    }

}

@Composable
fun CustomDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 5.dp),
        color = Color.Gray
    )
}

@Composable
fun CustomRowOne(
    image: Int,
    textOne: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .wrapContentWidth(Alignment.Start),
    ) {
        Box(
            modifier = Modifier
                .width(80.dp)
                .padding(horizontal = 4.dp, vertical = 4.dp),
        ) {
            Image(painter = painterResource(id = image), contentDescription = "")
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Column(
            modifier = Modifier.fillMaxWidth(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = textOne,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 10.dp),
            )
        }
    }
}

