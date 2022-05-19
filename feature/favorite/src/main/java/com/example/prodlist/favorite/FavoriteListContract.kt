package com.example.prodlist.favorite

import com.example.prodlist.mvi.UiEffect
import com.example.prodlist.mvi.UiEvent
import com.example.prodlist.mvi.UiState

interface FavoriteListContract {

    sealed interface Event : UiEvent {
        data class OnKeywordChanged(val keyword: String) : Event
        data class OnProductClicked(val productKey: String) : Event
        data class OnLikeClicked(val productKey: String) : Event
    }

    data class State(
        val keyword: String,
        val products: List<Product>,
    ) : UiState {

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