package com.example.prodlist.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.example.prodlist.aosutil.navigation.LocalNavController
import com.example.prodlist.aosutil.toast.showToast
import com.example.prodlist.designsys.transition.FadeIn
import com.example.prodlist.navigation.Uris
import com.example.prodlist.prodlist.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun FavoriteList(
    isVisible: Boolean,
    state: FavoriteListContract.State,
    onEvent: (FavoriteListContract.Event) -> Unit,
    effect: Flow<FavoriteListContract.Effect>,
) {
    val context = LocalContext.current
    val navController = LocalNavController.current

    LaunchedEffect(true) {
        effect.collect { effect ->
            when (effect) {
                is FavoriteListContract.Effect.NavigateToProductDetail -> navController.navigate("${Uris.ProdList.PRODUCT_DETAIL}?key=${effect.productKey}".toUri())
                is FavoriteListContract.Effect.ShowErrorToast -> context.showToast(effect.message)
            }
        }
    }

    FadeIn(
        modifier = Modifier.fillMaxSize(),
        visible = isVisible,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBox(
                keyword = state.keyword,
                onKeywordChanged = { keyword -> onEvent(FavoriteListContract.Event.OnKeywordChanged(keyword)) },
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                items(
                    items = state.products,
                    key = { product -> product.productKey }
                ) { product ->
                    Product(
                        name = product.name,
                        price = product.price,
                        liked = product.liked,
                        onProductClicked = { onEvent(FavoriteListContract.Event.OnProductClicked(product.productKey)) },
                        onLikeClicked = { onEvent(FavoriteListContract.Event.OnLikeClicked(product.productKey)) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    FavoriteList(
        isVisible = true,
        state = FavoriteListContract.State(
            keyword = "",
            products = listOf(
                FavoriteListContract.State.Product(
                    productKey = "1",
                    name = "상품1",
                    price = 10000,
                    liked = false,
                ),
                FavoriteListContract.State.Product(
                    productKey = "2",
                    name = "상품2",
                    price = 10000,
                    liked = true,
                ),
                FavoriteListContract.State.Product(
                    productKey = "3",
                    name = "상품3",
                    price = 10000,
                    liked = true,
                ),
                FavoriteListContract.State.Product(
                    productKey = "4",
                    name = "상품4",
                    price = 10000,
                    liked = false,
                ),
            ),
        ),
        onEvent = {},
        effect = flowOf(),
    )
}