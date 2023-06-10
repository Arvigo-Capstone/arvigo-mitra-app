package id.arvigo.arvigomitraapp.ui.feature.register

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.arvigo.arvigomitraapp.R
import id.arvigo.arvigomitraapp.data.source.network.response.address_requuest.provice.ProvinceItem
import id.arvigo.arvigomitraapp.ui.component.PrimaryButton
import id.arvigo.arvigomitraapp.ui.feature.LoginCheck
import id.arvigo.arvigomitraapp.ui.feature.register.api.RegisterRequest
import id.arvigo.arvigomitraapp.ui.feature.register.uistate.RegisterUiState
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

    val emailState by remember { mutableStateOf("") }
    val passwordState by remember { mutableStateOf("") }
    var confirmPasswordState by remember { mutableStateOf("") }
    val (snackbarVisibleState, setSnackBarState) = remember { mutableStateOf(false) }
    val options = remember {
        mutableStateOf<List<ProvinceItem>>(emptyList())
    }
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText = ""
    var selectedOptionId = 0
    val response = viewModel.response.value

    Scaffold() {
        Column(
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
                is RegisterUiState.Failure -> {
                    setSnackBarState(true)
                }
                else -> {}
            }
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
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = emailState.toString(),
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon", tint = Color.LightGray) },
                onValueChange = {
                    viewModel.setEmail(it)
                },
                shape = RoundedCornerShape(10.dp),
                placeholder = { Text(text = "Your Email", color =  Color.LightGray) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.LightGray
                ),
            )
            Spacer(modifier = Modifier.padding(top = 12.dp))
       //     PasswordTextField(value = passwordState.text, onValueChange = {viewModel.setPassword(it)}, placeHolder = "Password")
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = passwordState,
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "passIcon", tint =  Color.LightGray) },
                onValueChange = {
                    viewModel.setPassword(it)
                },
                shape = RoundedCornerShape(10.dp),
                placeholder = { Text(text = "Password", color =  Color.LightGray) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.LightGray
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.padding(top = 12.dp))
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    value = selectedOptionText,
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
                            text = { Text(selectionOption.provinceName) },
                            onClick = {
                                selectedOptionText = selectionOption.provinceName
                                selectedOptionId = selectionOption.provinceId
                                expanded = false
                                Log.d("hit api province screen","onclick $selectedOptionId, $selectedOptionText")
                            },
                            contentPadding = PaddingValues(6.dp),
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(60.dp))
            val context = LocalContext.current
            PrimaryButton(title = "Register", onClick = {
                val storeName = ""
                val password = ""
                val passwordConfirmation = ""
                val street = ""
                val cityId = 123
                val districtId = 234
                val subDistrictId = 345
                val postalCodeId = 567
                val registerRequest = RegisterRequest(
                    storeName = storeName,
                    email = emailState,
                    password = password,
                    passwordConfirmation = passwordConfirmation,
                    street = street,
                    provinceId = selectedOptionId,
                    cityId = cityId,
                    districtId = districtId,
                    subDistrictId = subDistrictId,
                    postalCodeId = postalCodeId,
                )
                viewModelRegister.register(registerRequest)
            })
            Spacer(modifier = Modifier.padding(24.dp))
            LoginCheck(
                navController = navController,
            )
            if (snackbarVisibleState) {
                Snackbar(
                    modifier = Modifier.padding(8.dp),
                    contentColor = Color.White,
                    containerColor = Color.Red,
                ) { Text(text = "Login Failed, please try again with correct email and password!", style = TextStyle(
                    color = Color.White,
                )
                ) }
            }
        }
    }
}