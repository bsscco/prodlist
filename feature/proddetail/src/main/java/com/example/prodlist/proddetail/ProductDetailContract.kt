package com.example.prodlist.proddetail

import com.example.prodlist.mvi.UiEffect
import com.example.prodlist.mvi.UiEvent
import com.example.prodlist.mvi.UiState

internal interface ProductDetailContract {

    sealed interface Event : UiEvent {
        object OnBackClicked : Event
        object OnLikeClicked : Event
    }

    data class State(
        val productName: String,
        val price: Int,
        val liked: Boolean,
    ) : UiState

    sealed interface Effect : UiEffect {
        object NavigateToBack : Effect
        object ShowNoProductModal : Effect
        data class ShowErrorToast(val message: String) : Effect
    }
}