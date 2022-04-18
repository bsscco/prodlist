package com.example.prodlist.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prodlist.ktutil.log.EventLogHelper
import com.example.prodlist.mvi.MviReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val savedState: SavedStateHandle,
    private val eventLogHelper: EventLogHelper,
) : ViewModel() {

    private var selectedTab = savedState.getSelectedTab()

    private val reducer = MviReducer<
            HomeContract.Event,
            HomeContract.State,
            HomeContract.Effect,
            >(
        viewModelScope = viewModelScope,
        initialState = createInitialState(),
        handleEvent = ::handleEvent,
    )

    val eventHandler = reducer::setEvent
    val stateFlow = reducer.stateFlow

    private fun SavedStateHandle.getSelectedTab(): HomeContract.State.Tab {
        return get<HomeContract.State.Tab>(SELECTED_TAB) ?: HomeContract.State.Tab.PRODUCT
    }

    private fun createInitialState() = HomeContract.State(
        selectedTab = selectedTab,
    )

    private fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnTabClicked -> handleTabClicked(event.tab)
        }
    }

    private fun handleTabClicked(tab: HomeContract.State.Tab) {
        eventLogHelper.logEvent("clickTab", mapOf("tab" to tab.name))
        selectTab(tab)
    }

    private fun selectTab(tab: HomeContract.State.Tab) {
        selectedTab = tab
        savedState.set(SELECTED_TAB, tab)
        reducer.setState { copy(selectedTab = tab) }
    }

    companion object {
        private const val SELECTED_TAB = "selectedTab"
    }
}