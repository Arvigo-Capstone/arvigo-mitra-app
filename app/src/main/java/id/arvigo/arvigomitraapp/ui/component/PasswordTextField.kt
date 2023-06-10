package id.arvigo.arvigomitraapp.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import id.arvigo.arvigomitraapp.R

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String
) {
    var showError by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }
    val isPasswordValid = value.length >= 6 // Minimum password length requirement
    var passwordVisible by remember { mutableStateOf(false) }

    if (!isPasswordValid && value.isNotEmpty()) {
        showError = true
        errorText = "Password must be at least 6 characters long"
    } else {
        showError = false
        errorText = ""
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.LightGray
        ),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        placeholder = { Text(text = placeHolder) },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        shape = RoundedCornerShape(10.dp),
        isError = showError,
        trailingIcon = {
            IconButton(
                onClick = { passwordVisible = !passwordVisible }
            ) {
                val visibilityIcon = if (passwordVisible) {
                    painterResource(R.drawable.visibility_on)
                } else {
                    painterResource(R.drawable.visibility_off)
                }
                Icon(
                    painter = visibilityIcon,
                    contentDescription = "Toggle Password Visibility",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    )

    if (showError) {
        Text(
            text = "Password harus memiliki panjang diatas 6 karakter.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}

@Composable
fun RePasswordTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    PasswordTextField(value = value, onValueChange = onValueChange, placeHolder = "Tulis kembali password anda.")
}