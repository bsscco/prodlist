package com.example.prodlist.prodlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prodlist.designsys.theme.ProdListTheme

@Composable
fun Product(
    name: String,
    price: Int,
    liked: Boolean,
    onProductClicked: () -> Unit,
    onLikeClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(onClick = onProductClicked),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(Modifier.width(16.dp))

        Text(text = "${name}\n${price}원")

        Spacer(Modifier.weight(1f))

        Box(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                    onClick = onLikeClicked,
                )
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                imageVector = if (liked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LikedPreview() {
    ProdListTheme {
        Product(
            name = "이름",
            price = 10000,
            liked = true,
            onProductClicked = {},
            onLikeClicked = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UnlikedPreview() {
    ProdListTheme {
        Product(
            name = "이름",
            price = 10000,
            liked = false,
            onProductClicked = {},
            onLikeClicked = {},
        )
    }
}