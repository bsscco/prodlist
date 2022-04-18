package com.example.prodlist.proddetail.noproduct

import com.example.prodlist.mvi.UiEffect
import com.example.prodlist.mvi.UiEvent
import com.example.prodlist.mvi.UiState

internal interface NoProductContract {

    sealed interface Event : UiEvent {
        object OnNeedShowModal : Event
        object OnOkClicked : Event
    }

    data class State(
        val showModal: Boolean,
    ) : UiState

    sealed interface Effect : UiEffect {
        object NavigateToBack : Effect
    }
}