package com.example.prodlist.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.prodlist.aosutil.navigation.LocalNavController
import com.example.prodlist.aosutil.toast.showToast
import com.example.prodlist.designsys.divider.RowDivider
import com.example.prodlist.designsys.theme.ProdListTheme
import com.example.prodlist.favorite.FavoriteList
import com.example.prodlist.favorite.FavoriteListContract
import com.example.prodlist.favorite.FavoriteListViewModel
import com.example.prodlist.navigation.Uris
import com.example.prodlist.prodlist.ProductList
import com.example.prodlist.prodlist.ProductListContract
import com.example.prodlist.prodlist.ProductListViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun HomeScreen() {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val productListViewModel = hiltViewModel<ProductListViewModel>()
    val favoriteListViewModel = hiltViewModel<FavoriteListViewModel>()
    val context = LocalContext.current
    val navController = LocalNavController.current

    LaunchedEffect(true) {
        productListViewModel.effectFlow.collect { effect ->
            when (effect) {
                is ProductListContract.Effect.NavigateToProductDetail -> navController.navigate("${Uris.ProdList.PRODUCT_DETAIL}?key=${effect.productKey}".toUri())
                is ProductListContract.Effect.ShowErrorToast -> context.showToast(effect.message)
            }
        }
    }

    LaunchedEffect(true) {
        favoriteListViewModel.effectFlow.collect { effect ->
            when (effect) {
                is FavoriteListContract.Effect.NavigateToProductDetail -> navController.navigate("${Uris.ProdList.PRODUCT_DETAIL}?key=${effect.productKey}".toUri())
                is FavoriteListContract.Effect.ShowErrorToast -> context.showToast(effect.message)
            }
        }
    }

    HomeScreen(
        homeState = homeViewModel.stateFlow.collectAsState().value,
        onHomeEvent = homeViewModel.eventHandler,
        productListState = productListViewModel.stateFlow.collectAsState().value,
        onProductListEvent = productListViewModel.eventHandler,
        favoriteListState = favoriteListViewModel.stateFlow.collectAsState().value,
        onFavoriteListEvent = favoriteListViewModel.eventHandler,
    )
}

@Composable
private fun HomeScreen(
    homeState: HomeContract.State,
    onHomeEvent: (HomeContract.Event) -> Unit,
    productListState: ProductListContract.State,
    onProductListEvent: (ProductListContract.Event) -> Unit,
    favoriteListState: FavoriteListContract.State,
    onFavoriteListEvent: (FavoriteListContract.Event) -> Unit,
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
            )
            FavoriteList(
                isVisible = homeState.selectedTab == HomeContract.State.Tab.FAVORITE,
                state = favoriteListState,
                onEvent = onFavoriteListEvent,
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
            favoriteListState = FavoriteListContract.State(
                keyword = "",
                products = emptyList(),
            ),
            onFavoriteListEvent = {},
        )
    }
}