package com.example.prodlist.prodlist

import com.example.prodlist.mvi.UiEffect
import com.example.prodlist.mvi.UiEvent
import com.example.prodlist.mvi.UiState

interface ProductListContract {

    sealed interface Event : UiEvent {
        data class OnCategoryClicked(val categoryKey: String) : Event
        data class OnProductClicked(val productKey: String) : Event
        data class OnLikeClicked(val productKey: String) : Event
    }

    data class State(
        val categories: List<Category>,
        val products: List<Product>,
    ) : UiState {

        data class Category(
            val categoryKey: String,
            val name: String,
            val selected: Boolean,
        )

        data class Product(
            val productKey: String,
            val name: String,
            val price: Int,
            val liked: Boolean,
        )
    }

    sealed interface Effect : UiEffect {
        data class NavigateToProductDetail(val productKey: String) : Effect
        data class ShowErrorToast(val message: String) : Effect
    }
}