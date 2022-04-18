package com.example.prodlist.home

import androidx.annotation.Keep
import com.example.prodlist.mvi.UiEffect
import com.example.prodlist.mvi.UiEvent
import com.example.prodlist.mvi.UiState

internal interface HomeContract {

    sealed interface Event : UiEvent {
        data class OnTabClicked(val tab: State.Tab) : Event
    }

    data class State(
        val selectedTab: Tab,
    ) : UiState {

        @Keep
        enum class Tab { PRODUCT, FAVORITE }
    }

    object Effect : UiEffect
}