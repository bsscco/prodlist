package com.example.prodlist.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.prodlist.favorite.FavoriteListViewModel
import com.example.prodlist.prodlist.ProductList
import com.example.prodlist.prodlist.ProductListContract
import com.example.prodlist.prodlist.ProductListViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen() {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val productListViewModel = hiltViewModel<ProductListViewModel>()
    val favoriteListViewModel = hiltViewModel<FavoriteListViewModel>()

    HomeScreen(
        homeState = homeViewModel.stateFlow.collectAsState().value,
        onHomeEvent = homeViewModel.eventHandler,
        productListState = productListViewModel.stateFlow.collectAsState().value,
        onProductListEvent = productListViewModel.eventHandler,
        productListEffect = productListViewModel.effectFlow,
        favoriteListState = favoriteListViewModel.stateFlow.collectAsState().value,
        onFavoriteListEvent = favoriteListViewModel.eventHandler,
        favoriteListEffect = favoriteListViewModel.effectFlow,
    )
}

@Composable
private fun HomeScreen(
    homeState: HomeContract.State,
    onHomeEvent: (HomeContract.Event) -> Unit,
    productListState: ProductListContract.State,
    onProductListEvent: (ProductListContract.Event) -> Unit,
    productListEffect: Flow<ProductListContract.Effect>,
    favoriteListState: FavoriteListContract.State,
    onFavoriteListEvent: (FavoriteListContract.Event) -> Unit,
    favoriteListEffect: Flow<FavoriteListContract.Effect>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Tabs(
            selectedTab = homeState.selectedTab,
            onTabClicked = { tab -> onHomeEvent(HomeContract.Event.OnTabClicked(tab)) },
        )

        RowDivider()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            ProductList(
                isVisible = homeState.selectedTab == HomeContract.State.Tab.PRODUCT,
                state = productListState,
                onEvent = onProductListEvent,
                effect = productListEffect,
            )
            FavoriteList(
                isVisible = homeState.selectedTab == HomeContract.State.Tab.FAVORITE,
                state = favoriteListState,
                onEvent = onFavoriteListEvent,
                effect = favoriteListEffect,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ProdListTheme {
        HomeScreen(
            homeState = HomeContract.State(
                selectedTab = HomeContract.State.Tab.PRODUCT,
            ),
            onHomeEvent = {},
            productListState = ProductListContract.State(
                categories = emptyList(),
                products = emptyList(),
            ),
            onProductListEvent = {},
            productListEffect = flowOf(),
            favoriteListState = FavoriteListContract.State(
                keyword = "",
                products = emptyList(),
            ),
            onFavoriteListEvent = {},
            favoriteListEffect = flowOf(),
        )
    }
}