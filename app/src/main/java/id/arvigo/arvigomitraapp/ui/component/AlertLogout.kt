package id.arvigo.arvigomitraapp.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun AlertLogout(
    openDialog : MutableState<Boolean>,
    onClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            Text(text = "Logout")
        },
        text = {
            Text(text = "Apakah kamu yakin bahwa kamu ingin logout?")
        },
        confirmButton = {
            TextButton(
                onClick = onClick
            ) {
                Text("Logout")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    openDialog.value = false
                }
            ) {
                Text("Tidak")
            }
        }
    )
}