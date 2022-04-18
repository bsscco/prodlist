package com.example.prodlist.prodlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun CategoryTabs(
    categories: List<ProductListContract.State.Category>,
    onCategoryClicked: (String) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
    ) {
        items(
            items = categories,
            key = { category -> category.categoryKey }
        ) { category ->
            Category(
                category = category,
                onClicked = { onCategoryClicked(category.categoryKey) }
            )
        }
    }
}

@Composable
private fun Category(
    category: ProductListContract.State.Category,
    onClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .fillMaxHeight()
            .clickable(onClick = onClicked)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = category.name,
            fontWeight = if (category.selected) FontWeight.Bold else FontWeight.Normal,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    CategoryTabs(
        categories = listOf(
            ProductListContract.State.Category(
                categoryKey = "1",
                name = "카테고리카테고리카테고리1",
                selected = false,
            ),
            ProductListContract.State.Category(
                categoryKey = "2",
                name = "카테고리카테고리카테고리2",
                selected = true,
            ),
            ProductListContract.State.Category(
                categoryKey = "3",
                name = "카테고리카테고리카테고리3",
                selected = false,
            ),
        ),
        onCategoryClicked = {},
    )
}