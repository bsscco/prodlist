package com.example.prodlist.favorite

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prodlist.domain.product.LoadProductsUseCase
import com.example.prodlist.domain.product.Product
import com.example.prodlist.domain.product.like.SetLikeProductUseCase
import com.example.prodlist.ktutil.coroutine.DefaultDispatcher
import com.example.prodlist.ktutil.flow.onErrorOrCatch
import com.example.prodlist.ktutil.flow.onSuccess
import com.example.prodlist.ktutil.flow.successOrThrow
import com.example.prodlist.ktutil.log.CrashReportHelper
import com.example.prodlist.ktutil.log.EventLogHelper
import com.example.prodlist.mvi.MviReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val savedState: SavedStateHandle,
    private val loadProductsUseCase: LoadProductsUseCase,
    private val setLikeProductUseCase: SetLikeProductUseCase,
    private val eventLogHelper: EventLogHelper,
    private val crashReportHelper: CrashReportHelper,
) : ViewModel() {

    private val keywordFlow = MutableStateFlow(savedState.getKeyword())
    private var products = emptyList<Product>()

    private val reducer = MviReducer<
            FavoriteListContract.Event,
            FavoriteListContract.State,
            FavoriteListContract.Effect,
            >(
        viewModelScope = viewModelScope,
        initialState = createInitialState(),
        handleEvent = ::handleEvent,
    )

    val eventHandler = reducer::setEvent
    val stateFlow = reducer.stateFlow
    val effectFlow = reducer.effectFlow

    init {
        loadState()
    }

    private fun SavedStateHandle.getKeyword(): String {
        return get<String>(KEYWORD) ?: ""
    }

    private fun createInitialState() = FavoriteListContract.State(
        keyword = keywordFlow.value,
        products = emptyList(),
    )

    private fun loadState() {
        @OptIn(ExperimentalCoroutinesApi::class)
        keywordFlow
            .flatMapLatest { keyword ->
                if (keyword.isBlank()) {
                    loadProductsUseCase(LoadProductsUseCase.Params.OnlyFavorite)
                } else {
                    loadProductsUseCase(LoadProductsUseCase.Params.OnlySearchedFavorite(keyword))
                }
            }
            .onSuccess { products ->
                this@FavoriteListViewModel.products = products
                reducer.setState { copy(products = products.toState(defaultDispatcher)) }
            }
            .onErrorOrCatch { error -> handleLoadingError(error) }
            .launchIn(viewModelScope)
    }

    private fun handleLoadingError(error: Throwable) {
        crashReportHelper.logAndReport(error)
        reducer.setEffect(FavoriteListContract.Effect.ShowErrorToast(error.message!!))
    }

    private fun handleEvent(event: FavoriteListContract.Event) {
        when (event) {
            is FavoriteListContract.Event.OnKeywordChanged -> handleKeywordChanged(event.keyword)
            is FavoriteListContract.Event.OnProductClicked -> handleProductClicked(event.productKey)
            is FavoriteListContract.Event.OnLikeClicked -> handleLikeClicked(event.productKey)
        }
    }

    private fun handleKeywordChanged(keyword: String) {
        keywordFlow.value = keyword
        savedState[KEYWORD] = keyword
    }

    private fun handleProductClicked(productKey: String) {
        eventLogHelper.logEvent("clickProduct", mapOf("productKey" to productKey))
        reducer.setEffect(FavoriteListContract.Effect.NavigateToProductDetail(productKey))
    }

    private fun handleLikeClicked(productKey: String) {
        eventLogHelper.logEvent("clickLike", mapOf("productKey" to productKey))

        viewModelScope.launch {
            try {
                delay(200L)
                setLikeProductUseCase(SetLikeProductUseCase.Params(productKey, false)).successOrThrow()
            } catch (error: Exception) {
                handleLikeError(error)
            }
        }
    }

    private fun handleLikeError(error: Exception) {
        crashReportHelper.logAndReport(error)
        reducer.setEffect(FavoriteListContract.Effect.ShowErrorToast(error.message!!))
    }

    companion object {
        private const val KEYWORD = "keyword"
    }
}