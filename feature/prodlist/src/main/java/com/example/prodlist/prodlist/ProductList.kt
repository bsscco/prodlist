package com.example.prodlist.prodlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.prodlist.aosutil.navigation.LocalNavController
import com.example.prodlist.aosutil.toast.showToast
import com.example.prodlist.designsys.divider.RowDivider
import com.example.prodlist.designsys.transition.FadeIn
import com.example.prodlist.navigation.Uris

@Composable
fun ProductList(isVisible: Boolean) {
    val viewModel = hiltViewModel<ProductListViewModel>()
    val context = LocalContext.current
    val navController = LocalNavController.current

    LaunchedEffect(true) {
        viewModel.effectFlow.collect { effect ->
            when (effect) {
                is ProductListContract.Effect.NavigateToProductDetail -> navController.navigate("${Uris.ProdList.PRODUCT_DETAIL}?key=${effect.productKey}".toUri())
                is ProductListContract.Effect.ShowErrorToast -> context.showToast(effect.message)
            }
        }
    }

    ProductList(
        isVisible = isVisible,
        state = viewModel.stateFlow.collectAsState().value,
        onEvent = viewModel.eventHandler,
    )
}

@Composable
fun ProductList(
    isVisible: Boolean,
    state: ProductListContract.State,
    onEvent: (ProductListContract.Event) -> Unit,
) {
    FadeIn(
        modifier = Modifier.fillMaxSize(),
        visible = isVisible,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            CategoryTabs(
                categories = state.categories,
                onCategoryClicked = { categoryKey -> onEvent(ProductListContract.Event.OnCategoryClicked(categoryKey)) }
            )

            RowDivider()

            Products(
                products = state.products,
                onProductClicked = { productKey -> onEvent(ProductListContract.Event.OnProductClicked(productKey)) },
                onLikeClicked = { productKey -> onEvent(ProductListContract.Event.OnLikeClicked(productKey)) },
            )
        }
    }
}

@Composable
private fun ColumnScope.Products(
    products: List<ProductListContract.State.Product>,
    onProductClicked: (String) -> Unit,
    onLikeClicked: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
    ) {
        items(
            items = products,
            key = { item -> item.productKey }
        ) { item ->
            Product(
                name = item.name,
                price = item.price,
                liked = item.liked,
                onProductClicked = { onProductClicked(item.productKey) },
                onLikeClicked = { onLikeClicked(item.productKey) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ProductList(
        isVisible = true,
        state = ProductListContract.State(
            categories = listOf(
                ProductListContract.State.Category(
                    categoryKey = "1",
                    name = "????????????????????????????????????1",
                    selected = false,
                ),
                ProductListContract.State.Category(
                    categoryKey = "2",
                    name = "????????????????????????????????????2",
                    selected = true,
                ),
            ),
            products = listOf(
                ProductListContract.State.Product(
                    productKey = "1",
                    name = "??????1",
                    price = 10000,
                    liked = false,
                ),
                ProductListContract.State.Product(
                    productKey = "2",
                    name = "??????2",
                    price = 10000,
                    liked = true,
                ),
                ProductListContract.State.Product(
                    productKey = "3",
                    name = "??????3",
                    price = 10000,
                    liked = true,
                ),
                ProductListContract.State.Product(
                    productKey = "4",
                    name = "??????4",
                    price = 10000,
                    liked = false,
                ),
            ),
        ),
        onEvent = {},
    )
}