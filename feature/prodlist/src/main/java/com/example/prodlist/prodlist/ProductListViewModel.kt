package com.example.prodlist.prodlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prodlist.domain.category.Category
import com.example.prodlist.domain.category.LoadCategoriesUseCase
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val savedState: SavedStateHandle,
    private val loadCategoriesUseCase: LoadCategoriesUseCase,
    private val loadProductsUseCase: LoadProductsUseCase,
    private val setLikeProductUseCase: SetLikeProductUseCase,
    private val eventLogHelper: EventLogHelper,
    private val crashReportHelper: CrashReportHelper,
) : ViewModel() {

    private val selectedCategoryKeyFlow = MutableStateFlow(savedState.getSelectedCategoryKey())
    private var categories = emptyList<Category>()
    private var products = emptyList<Product>()

    private val reducer = MviReducer<
            ProductListContract.Event,
            ProductListContract.State,
            ProductListContract.Effect,
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

    private fun SavedStateHandle.getSelectedCategoryKey(): String {
        return get<String>(SELECTED_CATEGORY) ?: ""
    }

    private fun createInitialState() = ProductListContract.State(
        categories = emptyList(),
        products = emptyList(),
    )

    private fun loadState() {
        viewModelScope.launch {
            categories = loadCategoriesUseCase(Unit).successOrThrow()
            selectCategory(categoryKey = categories.firstOrNull()?.key ?: "")

            @OptIn(ExperimentalCoroutinesApi::class)
            selectedCategoryKeyFlow
                .flatMapLatest { categoryKey -> loadProductsUseCase(LoadProductsUseCase.Params.FilterByCategory(categoryKey)) }
                .onSuccess { products ->
                    this@ProductListViewModel.products = products
                    reducer.setState { copy(products = products.toState(defaultDispatcher)) }
                }
                .onErrorOrCatch { error -> handleLoadingError(error) }
                .launchIn(viewModelScope)
        }
    }

    private fun selectCategory(categoryKey: String) {
        selectedCategoryKeyFlow.value = categoryKey
        savedState[SELECTED_CATEGORY] = categoryKey
        reducer.setState {
            val newCategories = this@ProductListViewModel.categories.toState(defaultDispatcher, selectedKey = categoryKey)
            copy(categories = newCategories)
        }
    }

    private fun handleLoadingError(error: Throwable) {
        crashReportHelper.logAndReport(error)
        reducer.setEffect(ProductListContract.Effect.ShowErrorToast(error.message!!))
    }

    private fun handleEvent(event: ProductListContract.Event) {
        when (event) {
            is ProductListContract.Event.OnCategoryClicked -> handleCategoryClicked(event.categoryKey)
            is ProductListContract.Event.OnProductClicked -> handleProductClicked(event.productKey)
            is ProductListContract.Event.OnLikeClicked -> handleLikeClicked(event.productKey)
        }
    }

    private fun handleCategoryClicked(categoryKey: String) {
        eventLogHelper.logEvent("clickCategory", mapOf("categoryKey" to categoryKey))
        selectCategory(categoryKey)
    }

    private fun handleProductClicked(productKey: String) {
        eventLogHelper.logEvent("clickProduct", mapOf("productKey" to productKey))
        reducer.setEffect(ProductListContract.Effect.NavigateToProductDetail(productKey))
    }

    private fun handleLikeClicked(productKey: String) {
        eventLogHelper.logEvent("clickLike", mapOf("productKey" to productKey))

        viewModelScope.launch {
            try {
                val newLike = products.getLikedByKey(productKey).not()
                setLikeProductUseCase(SetLikeProductUseCase.Params(productKey, newLike)).successOrThrow()
            } catch (error: Exception) {
                handleLikeError(error)
            }
        }
    }

    private suspend fun List<Product>.getLikedByKey(productKey: String): Boolean {
        return withContext(defaultDispatcher) {
            first { it.key == productKey }.liked
        }
    }

    private fun handleLikeError(error: Exception) {
        crashReportHelper.logAndReport(error)
        reducer.setEffect(ProductListContract.Effect.ShowErrorToast(error.message!!))
    }

    companion object {
        private const val SELECTED_CATEGORY = "selectedCategory"
    }
}