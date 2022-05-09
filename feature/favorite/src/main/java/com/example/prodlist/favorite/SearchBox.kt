package com.example.prodlist.favorite

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.prodlist.designsys.theme.ProdListTheme

@Composable
internal fun SearchBox(
    keyword: String,
    onKeywordChanged: (String) -> Unit,
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        value = keyword,
        textStyle = LocalTextStyle.current.copy(color = Color.Black),
        onValueChange = { input -> onKeywordChanged(input) },
        placeholder = { Text("검색어를 입력해주세요.") },
        singleLine = true,
    )
}

@Preview(showBackground = true)
@Composable
private fun EmptyPreview() {
    ProdListTheme {
        SearchBox(
            keyword = "",
            onKeywordChanged = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FilledPreview() {
    ProdListTheme {
        SearchBox(
            keyword = "검색어",
            onKeywordChanged = {},
        )
    }
}