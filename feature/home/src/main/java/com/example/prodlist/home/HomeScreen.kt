package com.example.prodlist.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.prodlist.designsys.divider.RowDivider
import com.example.prodlist.designsys.theme.ProdListTheme
import com.example.prodlist.favorite.FavoriteList
import com.example.prodlist.favorite.FavoriteListContract
import com.example.prodlist.prodlist.ProductList
import com.example.prodlist.prodlist.ProductListContract

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()

    HomeScreen(
        state = viewModel.stateFlow.collectAsState().value,
        onEvent = viewModel.eventHandler,
    ) { state ->
        ProductList(isVisible = (state.selectedTab == HomeContract.State.Tab.PRODUCT))
        FavoriteList(isVisible = (state.selectedTab == HomeContract.State.Tab.FAVORITE))
    }
}

@Composable
private fun HomeScreen(
    state: HomeContract.State,
    onEvent: (HomeContract.Event) -> Unit,
    tabsContent: @Composable BoxScope.(HomeContract.State) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Tabs(
            selectedTab = state.selectedTab,
            onTabClicked = { tab -> onEvent(HomeContract.Event.OnTabClicked(tab)) },
        )

        RowDivider()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            tabsContent(state)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ProdListTheme {
        HomeScreen(
            state = HomeContract.State(
                selectedTab = HomeContract.State.Tab.PRODUCT,
            ),
            onEvent = {},
        ) { state ->
            ProductList(
                isVisible = (state.selectedTab == HomeContract.State.Tab.PRODUCT),
                state = ProductListContract.State(
                    categories = listOf(
                        ProductListContract.State.Category(
                            categoryKey = "1",
                            name = "카테1",
                            selected = false,
                        ),
                        ProductListContract.State.Category(
                            categoryKey = "2",
                            name = "카테고2",
                            selected = true,
                        ),
                    ),
                    products = listOf(
                        ProductListContract.State.Product(
                            productKey = "1",
                            name = "상품1",
                            price = 10000,
                            liked = false,
                        ),
                        ProductListContract.State.Product(
                            productKey = "2",
                            name = "상품2",
                            price = 10000,
                            liked = true,
                        ),
                    ),
                ),
                onEvent = {},
            )
            FavoriteList(
                isVisible = (state.selectedTab == HomeContract.State.Tab.FAVORITE),
                state = FavoriteListContract.State(
                    keyword = "",
                    products = emptyList(),
                ),
                onEvent = {},
            )
        }
    }
}