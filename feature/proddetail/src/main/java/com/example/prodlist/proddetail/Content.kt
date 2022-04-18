package com.example.prodlist.proddetail

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

@Composable
internal fun Content(
    price: Int,
    liked: Boolean,
    onLikeClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(Modifier.width(16.dp))

        Text(text = "가격: ${price}원")

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
                modifier = Modifier.size(ButtonDefaults.IconSize * 2),
                imageVector = if (liked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LikedPreview() {
    Content(
        price = 10000,
        liked = true,
        onLikeClicked = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun UnlikedPreview() {
    Content(
        price = 10000,
        liked = false,
        onLikeClicked = {},
    )
}