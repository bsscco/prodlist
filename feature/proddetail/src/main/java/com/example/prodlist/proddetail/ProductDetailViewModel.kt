package com.example.prodlist.proddetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prodlist.domain.product.IsProductExistUseCase
import com.example.prodlist.domain.product.LoadProductUseCase
import com.example.prodlist.domain.product.Product
import com.example.prodlist.domain.product.like.SetLikeProductUseCase
import com.example.prodlist.ktutil.flow.onErrorOrCatch
import com.example.prodlist.ktutil.flow.onSuccess
import com.example.prodlist.ktutil.flow.successOrThrow
import com.example.prodlist.ktutil.log.CrashReportHelper
import com.example.prodlist.ktutil.log.EventLogHelper
import com.example.prodlist.mvi.MviReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProductDetailViewModel @Inject constructor(
    private val isProductExistUseCase: IsProductExistUseCase,
    private val loadProductUseCase: LoadProductUseCase,
    private val setLikeProductUseCase: SetLikeProductUseCase,
    private val eventLogHelper: EventLogHelper,
    private val crashReportHelper: CrashReportHelper,
) : ViewModel() {

    private lateinit var product: Product

    private val reducer = MviReducer<
            ProductDetailContract.Event,
            ProductDetailContract.State,
            ProductDetailContract.Effect,
            >(
        viewModelScope = viewModelScope,
        initialState = createInitialState(),
        handleEvent = ::handleEvent,
    )

    val eventHandler = reducer::setEvent
    val stateFlow = reducer.stateFlow
    val effectFlow = reducer.effectFlow

    private fun createInitialState() = ProductDetailContract.State(
        productName = "",
        price = 0,
        liked = false,
    )

    fun initProductKey(productKey: String) {
        loadState(productKey)
    }

    private fun loadState(productKey: String) {
        viewModelScope.launch {
            val exist = isProductExistUseCase(IsProductExistUseCase.Params(productKey)).successOrThrow()
            if (exist) {
                loadProductUseCase(LoadProductUseCase.Params(productKey))
                    .onSuccess { product ->
                        this@ProductDetailViewModel.product = product
                        reducer.setState {
                            copy(
                                productName = product.name,
                                price = product.price,
                                liked = product.liked,
                            )
                        }
                    }
                    .onErrorOrCatch { error -> handleLoadingError(error) }
                    .launchIn(viewModelScope)
            } else {
                reducer.setEffect(ProductDetailContract.Effect.ShowNoProductModal)
            }
        }
    }

    private fun handleLoadingError(error: Throwable) {
        crashReportHelper.logAndReport(error)
        reducer.setEffect(ProductDetailContract.Effect.ShowErrorToast(error.message!!))
    }

    private fun handleEvent(event: ProductDetailContract.Event) {
        when (event) {
            is ProductDetailContract.Event.OnBackClicked -> handleBackClicked()
            is ProductDetailContract.Event.OnLikeClicked -> handleLikeClicked()
        }
    }

    private fun handleBackClicked() {
        eventLogHelper.logEvent("clickBack")
        reducer.setEffect(ProductDetailContract.Effect.NavigateToBack)
    }

    private fun handleLikeClicked() {
        eventLogHelper.logEvent("clickLike", mapOf("productKey" to product.key))

        viewModelScope.launch {
            try {
                val newLike = product.liked.not()
                setLikeProductUseCase(SetLikeProductUseCase.Params(product.key, newLike)).successOrThrow()
            } catch (error: Exception) {
                handleLikeError(error)
            }
        }
    }

    private fun handleLikeError(error: Exception) {
        crashReportHelper.logAndReport(error)
        reducer.setEffect(ProductDetailContract.Effect.ShowErrorToast(error.message!!))
    }
}