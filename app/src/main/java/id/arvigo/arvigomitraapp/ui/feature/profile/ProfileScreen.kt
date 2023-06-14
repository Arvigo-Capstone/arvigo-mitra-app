package id.arvigo.arvigomitraapp.ui.feature.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.arvigo.arvigomitraapp.R
import id.arvigo.arvigomitraapp.ui.component.AlertLogout
import id.arvigo.arvigomitraapp.ui.component.MenuRowItem
import id.arvigo.arvigomitraapp.ui.feature.profile.uistate.ProfileUiState
import id.arvigo.arvigomitraapp.ui.navigation.Screen
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
) {

    val viewModel: ProfileViewModel = getViewModel()

    Scaffold {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {
            val response = viewModel.response.value
            
            when (response) {
                is ProfileUiState.Success -> {
                    item { 
                        Column {
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
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data("")
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        alignment = Alignment.Center,
                                        modifier = Modifier.clip(CircleShape)
                                    )
                                }
                                Spacer(modifier = Modifier.padding(4.dp))
                                Column(
                                    modifier = Modifier.fillMaxWidth(1f),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = response.data.fullName,
                                        style = MaterialTheme.typography.titleMedium,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.padding(horizontal = 10.dp),
                                    )
                                    Spacer(modifier = Modifier.padding(top = 5.dp))
                                    Text(
                                        text = response.data.email,
                                        style = MaterialTheme.typography.titleSmall.copy(color = Color.Gray),
                                        modifier = Modifier.padding(horizontal = 10.dp)
                                    )
                                    Spacer(modifier = Modifier.padding(top = 5.dp))
                                    Text(
                                        text = "Link Toko",
                                        style = MaterialTheme.typography.titleSmall.copy(color = Color.Gray),
                                        modifier = Modifier.padding(horizontal = 10.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            ProfileRowItems(
                                navController = navController,
                                viewModel = viewModel,
                            )
                        }
                    }
                }
                is ProfileUiState.Loading -> {
                    
                }
                is ProfileUiState.Failure -> {
                    
                }
                else -> {}
                
            }
        }
    }

}

@Composable
fun ProfileRowItems(
    navController: NavController,
    viewModel: ProfileViewModel,
) {
    val openDialog = remember { mutableStateOf(false) }
    val unavailableDialog = remember { mutableStateOf(false) }
    MenuRowItem(name = "Tentang Aplikasi") {
        unavailableDialog.value = true
    }
    MenuRowItem(name = "Logout", onMenuClick = { openDialog.value = true })
    if (openDialog.value) {
        AlertLogout(openDialog = openDialog) {
            openDialog.value = false
            viewModel.logout()
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Profile.route) {
                    inclusive = true
                }
                popUpTo(Screen.Home.route) {
                    inclusive = true
                }
            }
        }
    }
}

