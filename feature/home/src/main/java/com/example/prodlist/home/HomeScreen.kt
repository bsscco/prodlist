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
import com.example.prodlist.prodlist.ProductList

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()

    HomeScreen(
        state = viewModel.stateFlow.collectAsState().value,
        onEvent = viewModel.eventHandler,
    )
}

@Composable
private fun HomeScreen(
    state: HomeContract.State,
    onEvent: (HomeContract.Event) -> Unit,
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
            ProductList(isVisible = (state.selectedTab == HomeContract.State.Tab.PRODUCT))
            FavoriteList(isVisible = (state.selectedTab == HomeContract.State.Tab.FAVORITE))
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
        )
    }
}