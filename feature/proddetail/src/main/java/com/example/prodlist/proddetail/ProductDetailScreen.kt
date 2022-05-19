package com.example.prodlist.proddetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.prodlist.aosutil.navigation.LocalNavController
import com.example.prodlist.aosutil.toast.showToast
import com.example.prodlist.designsys.divider.RowDivider
import com.example.prodlist.proddetail.noproduct.NoProductContract
import com.example.prodlist.proddetail.noproduct.NoProductModal
import com.example.prodlist.proddetail.noproduct.NoProductViewModel

@Composable
fun ProductDetailScreen(productKey: String) {
    val productDetailViewModel = hiltViewModel<ProductDetailViewModel>()
    val noProductViewModel = hiltViewModel<NoProductViewModel>()
    val context = LocalContext.current
    val navController = LocalNavController.current

    LaunchedEffect(true) {
        productDetailViewModel.initProductKey(productKey)
        productDetailViewModel.effectFlow.collect { effect ->
            when (effect) {
                is ProductDetailContract.Effect.NavigateToBack -> navController.popBackStack()
                is ProductDetailContract.Effect.ShowNoProductModal -> noProductViewModel.eventHandler(NoProductContract.Event.OnNeedShowModal)
                is ProductDetailContract.Effect.ShowErrorToast -> context.showToast(effect.message)
            }
        }
    }

    LaunchedEffect(true) {
        noProductViewModel.effectFlow.collect { effect ->
            when (effect) {
                is NoProductContract.Effect.NavigateToBack -> navController.popBackStack()
            }
        }
    }

    ProductDetailScreen(
        productDetailState = productDetailViewModel.stateFlow.collectAsState().value,
        onProductDetailEvent = productDetailViewModel.eventHandler,
        noProductState = noProductViewModel.stateFlow.collectAsState().value,
        onNoProductEvent = noProductViewModel.eventHandler,
    )
}

@Composable
private fun ProductDetailScreen(
    productDetailState: ProductDetailContract.State,
    onProductDetailEvent: (ProductDetailContract.Event) -> Unit,
    noProductState: NoProductContract.State,
    onNoProductEvent: (NoProductContract.Event) -> Unit,
) {
    ProductDetailScreen(productDetailState, onProductDetailEvent)

    NoProductModal(noProductState, onNoProductEvent)
}

@Composable
private fun ProductDetailScreen(
    state: ProductDetailContract.State,
    onEvent: (ProductDetailContract.Event) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        TopBar(
            productName = state.productName,
            onBackClicked = { onEvent(ProductDetailContract.Event.OnBackClicked) },
        )

        RowDivider()

        Content(
            price = state.price,
            liked = state.liked,
            onLikeClicked = { onEvent(ProductDetailContract.Event.OnLikeClicked) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ProductDetailScreen(
        state = ProductDetailContract.State(
            productName = "상품명",
            price = 10000,
            liked = true,
        ),
        onEvent = {},
    )
}