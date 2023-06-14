package id.arvigo.arvigomitraapp.ui.component

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun CustomRowTwo(
    image: String,
    textOne: String,
    textTwo: String
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
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
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
                text = textOne,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 10.dp),
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))
            Text(
                text = textTwo,
                style = MaterialTheme.typography.titleSmall.copy(color = Color.Gray),
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}