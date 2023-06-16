package id.arvigo.arvigomitraapp.ui.feature.add_offer

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.arvigo.arvigomitraapp.R
import id.arvigo.arvigomitraapp.ui.navigation.Screen
import id.arvigo.arvigomitraapp.utils.saveUrisToFiles
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.compose.getViewModel
import java.io.File


@Composable
fun AddOfferScreen(
        navController: NavController,
        initialId: Int? = 0
) {
    AddOfferContent(
        navController = navController,
        initialId = initialId
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOfferContent(
        navController: NavController,
        initialId: Int?
) {

    val viewModel: AddOfferViewModel = getViewModel()

    val setName = viewModel.nameSate.value
    val setPrice = viewModel.priceSate.value
    val setDesc = viewModel.descSate.value
    val setTokped = viewModel.tokped.value
    val setShopee = viewModel.shopee.value
    val setBlibli = viewModel.blibli.value
    val setOffline = viewModel.offline.value

    val isTokped = remember {
        mutableStateOf(false)
    }
    val isShopee = remember {
        mutableStateOf(false)
    }
    val isBlibli = remember {
        mutableStateOf(false)
    }
    val ifOffline = remember {
        mutableStateOf(false)
    }

    var cameraPermissionGranted by remember {
        mutableStateOf(false)
    }

    val imageBitmaps by remember { mutableStateOf<List<ImageBitmap?>>(emptyList()) }

    val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(), onResult = { isGranted ->
        if (isGranted) {
            Log.d("TAG" , "Permission $isGranted")
            cameraPermissionGranted = true
        }
    } )


    val context = LocalContext.current
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var imgFiles by remember { mutableStateOf<List<File>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()
    val isPostSuccess =  viewModel.isPostSuccess.value
    val isPostFailed =  viewModel.isPostFailed.value
    val (snackbarVisibleState, setSnackBarState) = remember { mutableStateOf(false) }
    val options = listOf("Kacamata", "Makeup")
    val optionMap = mapOf(
        "Kacamata" to 1,
        "Makeup" to 2
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    var selectedOptionValue by remember { mutableStateOf(optionMap[selectedOptionText]) }
    val buttonEnable = remember {
        mutableStateOf(false)
    }


    val launcherGallery = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents()
    ) { results ->
        if (results.isNotEmpty()) {
            imageUris = results
            imgFiles = saveUrisToFiles(context, results)
        }
    }


    Scaffold(
        modifier = Modifier,
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = "Penawaran")
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
                }
            )
        }
    ) {
        Column(
                modifier = Modifier
                        .padding(it)
        ) {
            LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3.8f)
            ) {
                item { 
                    Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = "Kategori Product")
                        // We want to react on tap/press on TextField to show menu
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded },
                        ) {
                            TextField(
                                // The `menuAnchor` modifier must be passed to the text field for correctness.
                                readOnly = true,
                                value = selectedOptionText,
                                onValueChange = {
                                    selectedOptionText = it
                                },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = Color.LightGray
                                ),
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                            ) {
                                options.forEach { selectionOption ->
                                    DropdownMenuItem(
                                        text = { Text(selectionOption) },
                                        onClick = {
                                            selectedOptionText = selectionOption
                                            selectedOptionValue = optionMap[selectionOption]
                                            expanded = false
                                        },
                                        contentPadding = PaddingValues(8.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(18.dp))
                        Text(text = "Pilih Produk yang Tersedia di Arvigo")
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(
                                onClick = {
                                          navController.navigate(Screen.InitialProduct.createRoute(selectedOptionValue.toString()))
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
                        val idproduct = viewModel.initProduct
                        if (initialId != 0) {
                            Text(text = "Produk Berhasil Ditambahkan", color = MaterialTheme.colorScheme.onPrimary)
                        }
                        Spacer(modifier = Modifier.height(18.dp))
                        Text(text = "Pilih Foto Produk Kamu")
                        OutlinedButton(
                                onClick = {
                                    if (!cameraPermissionGranted) {
                                        permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                                    } else {
                                        launcherGallery.launch("image/*")
                                    }
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

                        if (imgFiles.isNotEmpty()) {
                            val size = imgFiles.size
                            Text(text = "$size Foto Produk Berhasil Ditambahkan", color = MaterialTheme.colorScheme.onPrimary)
                        }

                        Spacer(modifier = Modifier.height(18.dp))
                        Text(text = "Nama Produk")
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = setName.text,
                                onValueChange = {
                                    viewModel.setName(it)
                                },
                                shape = RoundedCornerShape(10.dp),
                                placeholder = { androidx.compose.material.Text(text = "Masukan nama produk kamu", color =  Color.LightGray) },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                                        unfocusedBorderColor = Color.LightGray
                                ),
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "Harga")
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = setPrice.text,
                                onValueChange = {
                                    viewModel.setPrice(it)
                                },
                                shape = RoundedCornerShape(10.dp),
                                placeholder = { androidx.compose.material.Text(text = "Masukan harga produk", color =  Color.LightGray) },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                                        unfocusedBorderColor = Color.LightGray
                                ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "Deskripsi")
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = setDesc.text,
                                onValueChange = {
                                    viewModel.setDesc(it)
                                },
                                maxLines = 7,
                                singleLine = false,
                                shape = RoundedCornerShape(10.dp),
                                placeholder = { androidx.compose.material.Text(text = "Deskripsi dari produk kamu", color =  Color.LightGray) },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                                        unfocusedBorderColor = Color.LightGray
                                ),
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                        Text(text = "Platform Toko")
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier
                                .height(30.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(painter = painterResource(id = R.drawable.ic_tokped), contentDescription ="", modifier = Modifier.size(28.dp))
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = "Tokopedia")
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = setTokped.text,
                            onValueChange = {
                                viewModel.setTokped(it)
                                buttonEnable.value = true
                            },
                            maxLines = 7,
                            singleLine = false,
                            shape = RoundedCornerShape(10.dp),
                            placeholder = { androidx.compose.material.Text(text = "Link toko kamu", color =  Color.LightGray) },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = Color.LightGray
                            ),
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier
                                .height(30.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(painter = painterResource(id = R.drawable.ic_shopee), contentDescription ="", modifier = Modifier.size(28.dp))
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = "Shopee")
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = setShopee.text,
                            onValueChange = {
                                viewModel.setShopee(it)
                                buttonEnable.value = true
                            },
                            maxLines = 7,
                            singleLine = false,
                            shape = RoundedCornerShape(10.dp),
                            placeholder = { androidx.compose.material.Text(text = "Link toko kamu", color =  Color.LightGray) },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = Color.LightGray
                            ),
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier
                                .height(30.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(painter = painterResource(id = R.drawable.ic_blibli), contentDescription ="", modifier = Modifier.size(28.dp))
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = "Blibli")
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = setBlibli.text,
                            onValueChange = {
                                viewModel.setBlibli(it)
                            },
                            maxLines = 7,
                            singleLine = false,
                            shape = RoundedCornerShape(10.dp),
                            placeholder = { androidx.compose.material.Text(text = "Link toko kamu", color =  Color.LightGray) },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = Color.LightGray
                            ),
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier
                                .height(30.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription ="", modifier = Modifier.size(28.dp), tint = MaterialTheme.colorScheme.onPrimary)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = "Offline")
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = setOffline.text,
                            onValueChange = {
                                viewModel.setOffline(it)
                            },
                            maxLines = 7,
                            singleLine = false,
                            shape = RoundedCornerShape(10.dp),
                            placeholder = { androidx.compose.material.Text(text = "Link toko kamu", color =  Color.LightGray) },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = Color.LightGray
                            ),
                        )
                        if (isPostSuccess) {
                            Snackbar(
                                modifier = Modifier.padding(8.dp),
                                contentColor = Color.White,
                                containerColor = MaterialTheme.colorScheme.onPrimary,
                            ) { androidx.compose.material.Text(text = "Penawaran berhasil diajukan!", style = TextStyle(
                                color = Color.White,
                            )
                            ) }
                        }
                        if (isPostFailed) {
                            Snackbar(
                                modifier = Modifier.padding(8.dp),
                                contentColor = Color.White,
                                containerColor = MaterialTheme.colorScheme.error,
                            ) { androidx.compose.material.Text(text = "Pengajuan gagal ditambahkan, mohon isi data dengan benar, coba lagi ya!", style = TextStyle(
                                color = Color.White,
                            )
                            ) }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
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
                val openDeepAR = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartActivityForResult()
                ) {
                }
                Button(
                    enabled = buttonEnable.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        onClick = {
                            viewModel.addOffer(
                                    images = createMultipartParts(imgFiles),
                                    initialId = initialId!!,
                            )
                        },
                        shape = MaterialTheme.shapes.small,
                        colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                        )
                )
                {
                    val isLading = viewModel.isLoading.value
                   if (isLading) {
                       CircularProgressIndicator(color = Color.White)
                   } else {
                       Text(text = "Ajukan Penawaran", style = MaterialTheme.typography.titleMedium.copy(
                           color = Color.White,
                       ))
                   }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenu() {
    val options = listOf("Kacamata", "Makeup")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    // We want to react on tap/press on TextField to show menu
    ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
                // The `menuAnchor` modifier must be passed to the text field for correctness.
                readOnly = true,
                value = selectedOptionText,
                onValueChange = {},
                label = { Text("Label") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                        },
                        contentPadding = PaddingValues(8.dp)
                )
            }
        }
    }
}

fun createMultipartParts(files: List<File>): List<MultipartBody.Part> {
    val parts = mutableListOf<MultipartBody.Part>()

    files.forEach { file ->
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("images", file.name, requestFile)
        parts.add(part)
    }
    Log.d("Multipart", "createMultipartParts: $parts")
    return parts
}

