package id.arvigo.arvigomitraapp.ui.feature.register

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import id.arvigo.arvigomitraapp.R
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.city.CityItem
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.district.DistrictItem
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.postal_code.PostalcodeItem
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.provice.ProvinceItem
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.subdistrict.SubdistrictItem
import id.arvigo.arvigomitraapp.ui.component.PrimaryButton
import id.arvigo.arvigomitraapp.ui.feature.LoginCheck
import id.arvigo.arvigomitraapp.ui.feature.login.LoginApiResults
import id.arvigo.arvigomitraapp.ui.feature.register.api.RegisterRequest
import id.arvigo.arvigomitraapp.ui.feature.register.uistate.RegisterUiState
import id.arvigo.arvigomitraapp.ui.navigation.Screen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterScreen(
    navController: NavController,
) {
    RegisterScreenContent(navController = navController)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenContent(
    viewModelRegister : RegisterViewModel = getViewModel(),
    viewModel: RegisterDropDownViewModel = getViewModel(),
    navController: NavController,
) {

    val buttonEnable = remember {
        mutableStateOf(false)
    }

    val nameState = remember {
        mutableStateOf(TextFieldValue())
    }
    val emailState = remember {
        mutableStateOf(TextFieldValue())
    }
    val streetState = remember {
        mutableStateOf(TextFieldValue())
    }
    val passwordState = remember {
        mutableStateOf(TextFieldValue())
    }
    val passwordConfirmState = remember {
        mutableStateOf(TextFieldValue())
    }
    var confirmPasswordState = remember {
        mutableStateOf(TextFieldValue())
    }
    val (snackbarVisibleState, setSnackBarState) = remember { mutableStateOf(false) }
    val options = remember {
        mutableStateOf<List<ProvinceItem>>(emptyList())
    }
    val optionsCity = remember {
        mutableStateOf<List<CityItem>>(emptyList())
    }
    val disctrict = remember {
        mutableStateOf<List<DistrictItem>>(emptyList())
    }
    val subdistrict = remember {
        mutableStateOf<List<SubdistrictItem>>(emptyList())
    }
    val postalCode = remember {
        mutableStateOf<List<PostalcodeItem>>(emptyList())
    }


    val showCity = remember {
        mutableStateOf(false)
    }
    val showDistrict = remember {
        mutableStateOf(false)
    }
    val showSubdistrict = remember {
        mutableStateOf(false)
    }
    val showPostalCode = remember {
        mutableStateOf(false)
    }

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionTextProvice = ""
    var selectedOptionIdProvince = remember {
        mutableStateOf(0)
    }
    var selectedOptionTextCity = ""
    var selectedOptionIdCity = remember {
        mutableStateOf(0)
    }
    var selectedOptionTextDistrict = ""
    var selectedOptionIdDistrict = remember {
        mutableStateOf(0)
    }
    var selectedOptionTextSubdistrict = ""
    var selectedOptionIdSubdistrict = remember {
        mutableStateOf(0)
    }
    var selectedOptionTextCode = ""
    var selectedOptionIdCode = remember {
        mutableStateOf(0)
    }
    val response = viewModel.response.value
    val loginResult by viewModelRegister.loginResult.observeAsState()
    val lifecycle : Lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(key1 = Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.getProvinces()
            }
        }
    }

    LaunchedEffect(key1 = loginResult) {
        when (loginResult) {
            is LoginApiResults.Success -> {
                val userId = (loginResult as LoginApiResults.Success).userId
                val token = (loginResult as LoginApiResults.Success).token
                // Handle successful login
                Log.d("LOGIN TAG SUCCESS", "LoginScreenContent: $userId, $token")
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Register.route) {
                        inclusive = true
                    }
                }
            }
            is LoginApiResults.Error -> {
                val errorMessage = (loginResult as LoginApiResults.Error).errorMessage
                // Handle login error
                Log.e("LOGIN TAG ERROR", "LoginScreenContent: $errorMessage", )
                setSnackBarState(!snackbarVisibleState)
            }
            else -> {
                // Initial state or loading state
            }
        }
    }

    Scaffold() {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (response) {
                is RegisterUiState.SuccessGetProvice -> {
                    options.value = response.data
                    Log.d("hit api province screen",
                        "launched effect ${response.data[0].provinceName}")
                }
                is RegisterUiState.SuccessGetCity -> {
                    optionsCity.value = response.data
                }
                is RegisterUiState.SuccessGetDistrict -> {
                    disctrict.value = response.data
                }
                is RegisterUiState.SuccessGetSubdistrict -> {
                    subdistrict.value = response.data
                }
                is RegisterUiState.SuccessGetPostalCode -> {
                    postalCode.value = response.data
                }
                is RegisterUiState.Failure -> {
                    setSnackBarState(true)
                }
                else -> {}
            }
            item {
                Image(
                    painter = painterResource(id = R.drawable.img_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .width(400.dp)
                        .height(100.dp)
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text(text = "Welcome to Arvigo", style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ))
                Spacer(modifier = Modifier.padding(6.dp))
                Text(text = "Sign In to Continue", style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray))
                Spacer(modifier = Modifier.padding(32.dp))
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = nameState.value,
                    leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "emailIcon", tint = Color.LightGray) },
                    onValueChange = {
                        nameState.value = it
                    },
                    shape = RoundedCornerShape(10.dp),
                    placeholder = { Text(text = "Nama Toko", color =  Color.LightGray) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.LightGray
                    ),
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = emailState.value,
                    leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon", tint = Color.LightGray) },
                    onValueChange = {
                        emailState.value = it
                    },
                    shape = RoundedCornerShape(10.dp),
                    placeholder = { Text(text = "Email", color =  Color.LightGray) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.LightGray
                    ),
                )

                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = streetState.value,
                    leadingIcon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = "emailIcon", tint = Color.LightGray) },
                    onValueChange = {
                        streetState.value = it
                    },
                    shape = RoundedCornerShape(10.dp),
                    placeholder = { Text(text = "Jalan", color =  Color.LightGray) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.LightGray
                    ),
                )
                Spacer(modifier = Modifier.padding(top = 12.dp))
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        value = selectedOptionTextProvice,
                        leadingIcon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = "passIcon", tint =  Color.LightGray) },
                        onValueChange = {

                        },
                        shape = RoundedCornerShape(10.dp),
                        placeholder = { Text(text = "Provinsi", color =  Color.LightGray) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.LightGray
                        ),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        Log.d("hit api province screen","1 ${options.value}")
                        options.value.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption.provinceName.toString()) },
                                onClick = {
                                    viewModel.getCities(selectionOption.provinceId)
                                    selectedOptionTextProvice = selectionOption.provinceName.toString()
                                    selectedOptionIdProvince.value = selectionOption.provinceId
                                    expanded = false
                                    showCity.value = true
                                    Log.d("hit api province screen","onclick ${selectedOptionIdProvince.value}, $selectedOptionTextProvice")
                                },
                                contentPadding = PaddingValues(6.dp),
                            )
                        }
                    }
                }
                if (showCity.value) {
                    Spacer(modifier = Modifier.padding(top = 12.dp))
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                            value = selectedOptionTextCity,
                            leadingIcon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = "passIcon", tint =  Color.LightGray) },
                            onValueChange = {

                            },
                            shape = RoundedCornerShape(10.dp),
                            placeholder = { Text(text = "Kota", color =  Color.LightGray) },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = Color.LightGray
                            ),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            Log.d("hit api province screen","1 ${options.value}")
                            optionsCity.value.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption.cityName) },
                                    onClick = {
                                        viewModel.getDistrict(selectionOption.cityId)
                                        selectedOptionTextCity = selectionOption.cityName
                                        selectedOptionIdCity.value = selectionOption.cityId
                                        expanded = false
                                        showDistrict.value = true
                                        Log.d("hit api province screen","onclick $selectedOptionIdCity, $selectedOptionTextCity")
                                    },
                                    contentPadding = PaddingValues(6.dp),
                                )
                            }
                        }
                    }
                }
                if (showDistrict.value) {
                    Spacer(modifier = Modifier.padding(top = 12.dp))
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                            value = selectedOptionTextDistrict,
                            leadingIcon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = "passIcon", tint =  Color.LightGray) },
                            onValueChange = {

                            },
                            shape = RoundedCornerShape(10.dp),
                            placeholder = { Text(text = "Kecamatan", color =  Color.LightGray) },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = Color.LightGray
                            ),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            Log.d("hit api province screen","1 ${options.value}")
                            disctrict.value.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption.districtName) },
                                    onClick = {
                                        viewModel.getSubdistrict(selectionOption.districtId)
                                        selectedOptionTextDistrict = selectionOption.districtName
                                        selectedOptionIdDistrict.value = selectionOption.districtId
                                        expanded = false
                                        showSubdistrict.value = true
                                        //Log.d("hit api district screen","onclick $selectedOptionTextDistrict, $selectedOptionIdDistrict")
                                    },
                                    contentPadding = PaddingValues(6.dp),
                                )
                            }
                        }
                    }
                }
                if (showSubdistrict.value) {
                    Spacer(modifier = Modifier.padding(top = 12.dp))
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                            value = selectedOptionTextSubdistrict,
                            leadingIcon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = "passIcon", tint =  Color.LightGray) },
                            onValueChange = {

                            },
                            shape = RoundedCornerShape(10.dp),
                            placeholder = { Text(text = "Desa/Kelurahan", color =  Color.LightGray) },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = Color.LightGray
                            ),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            Log.d("hit api subdistrict screen","1 ${subdistrict.value}")
                            subdistrict.value.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption.subdistrictName) },
                                    onClick = {
                                        viewModel.getPostalCode(selectionOption.subdistrictId)
                                        selectedOptionTextSubdistrict = selectionOption.subdistrictName
                                        selectedOptionIdSubdistrict.value = selectionOption.subdistrictId
                                        expanded = false
                                        showPostalCode.value = true
                                        //Log.d("hit api subdistrict screen","onclick $selectedOptionTextSubdistrict, $selectedOptionIdSubdistrict")
                                    },
                                    contentPadding = PaddingValues(6.dp),
                                )
                            }
                        }
                    }
                }
                if (showPostalCode.value) {
                    Spacer(modifier = Modifier.padding(top = 12.dp))
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                            value = selectedOptionTextCode,
                            leadingIcon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = "passIcon", tint =  Color.LightGray) },
                            onValueChange = {

                            },
                            shape = RoundedCornerShape(10.dp),
                            placeholder = { Text(text = "Kode Pos", color =  Color.LightGray) },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = Color.LightGray
                            ),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            Log.d("hit api subdistrict screen","1 ${subdistrict.value}")
                            postalCode.value.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption.postalCode.toString()) },
                                    onClick = {
                                        selectedOptionTextCode = selectionOption.postalCode.toString()
                                        selectedOptionIdCode.value = selectionOption.postalCodeId
                                        expanded = false
                                        //Log.d("hit api code screen","onclick $selectedOptionTextCode, $selectedOptionIdCode")
                                    },
                                    contentPadding = PaddingValues(6.dp),
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(top = 12.dp))
                //     PasswordTextField(value = passwordState.text, onValueChange = {viewModel.setPassword(it)}, placeHolder = "Password")
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = passwordState.value,
                    leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "passIcon", tint =  Color.LightGray) },
                    onValueChange = {
                        passwordState.value = it
                    },
                    shape = RoundedCornerShape(10.dp),
                    placeholder = { Text(text = "Kata sandi", color =  Color.LightGray) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.padding(top = 12.dp))
                //     PasswordTextField(value = passwordState.text, onValueChange = {viewModel.setPassword(it)}, placeHolder = "Password")
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = passwordConfirmState.value,
                    leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "passIcon", tint =  Color.LightGray) },
                    onValueChange = {
                        passwordConfirmState.value = it
                        buttonEnable.value = true
                    },
                    shape = RoundedCornerShape(10.dp),
                    placeholder = { Text(text = "Konfirmasi kata sandi", color =  Color.LightGray) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )


                Spacer(modifier = Modifier.padding(32.dp))
                val context = LocalContext.current
                PrimaryButton(enable =  buttonEnable.value,title = "Register", onClick = {
                    val storeName = ""
                    val password = ""
                    val passwordConfirmation = ""
                    val street = ""
                    val registerRequest = RegisterRequest(
                        storeName = nameState.value.text,
                        email = emailState.value.text,
                        password = passwordState.value.text,
                        passwordConfirmation = passwordConfirmState.value.text,
                        street = streetState.value.text,
                        provinceId = selectedOptionIdProvince.value,
                        cityId = selectedOptionIdCity.value,
                        districtId = selectedOptionIdDistrict.value,
                        subDistrictId = selectedOptionIdSubdistrict.value,
                        postalCodeId = selectedOptionIdCode.value,
                    )
                    viewModelRegister.register(registerRequest)
                })
                Spacer(modifier = Modifier.padding(24.dp))
                if (snackbarVisibleState) {
                    Snackbar(
                        modifier = Modifier.padding(8.dp),
                        contentColor = Color.White,
                        containerColor = Color.Red,
                    ) { Text(text = "Registerasi gagal, silahkan coba lagi dengan data yang benar!", style = TextStyle(
                        color = Color.White,
                    )
                    ) }
                }
            }
            }
    }
}