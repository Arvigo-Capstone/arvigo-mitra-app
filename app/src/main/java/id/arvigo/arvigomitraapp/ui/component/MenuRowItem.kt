package id.arvigo.arvigomitraapp.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MenuRowItem(
    name: String,
    onMenuClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f)
            )
            IconButton(
                onClick = onMenuClick
            ) {
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
            }
        }
        Divider(
            modifier = Modifier.padding(horizontal = 8.dp),
            color = Color.Gray
        )
    }
}