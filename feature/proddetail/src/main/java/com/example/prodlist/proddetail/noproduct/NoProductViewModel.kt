package com.example.prodlist.proddetail.noproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prodlist.domain.product.IsProductExistUseCase
import com.example.prodlist.domain.product.LoadProductUseCase
import com.example.prodlist.domain.product.Product
import com.example.prodlist.domain.product.like.SetLikeProductUseCase
import com.example.prodlist.ktutil.log.CrashReportHelper
import com.example.prodlist.ktutil.log.EventLogHelper
import com.example.prodlist.mvi.MviReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class NoProductViewModel @Inject constructor(
    private val isProductExistUseCase: IsProductExistUseCase,
    private val loadProductUseCase: LoadProductUseCase,
    private val setLikeProductUseCase: SetLikeProductUseCase,
    private val eventLogHelper: EventLogHelper,
    private val crashReportHelper: CrashReportHelper,
) : ViewModel() {

    private lateinit var product: Product

    private val reducer = MviReducer<
            NoProductContract.Event,
            NoProductContract.State,
            NoProductContract.Effect,
            >(
        viewModelScope = viewModelScope,
        initialState = createInitialState(),
        handleEvent = ::handleEvent,
    )

    val eventHandler = reducer::setEvent
    val stateFlow = reducer.stateFlow
    val effectFlow = reducer.effectFlow

    private fun createInitialState() = NoProductContract.State(
        showModal = false,
    )

    private fun handleEvent(event: NoProductContract.Event) {
        when (event) {
            is NoProductContract.Event.OnNeedShowModal -> handleNeedShowModal()
            is NoProductContract.Event.OnOkClicked -> handleOkClicked()
        }
    }

    private fun handleNeedShowModal() {
        eventLogHelper.logEvent("showNoProductModal")
        reducer.setState { copy(showModal = true) }
    }

    private fun handleOkClicked() {
        eventLogHelper.logEvent("clickOk")
        reducer.setEffect(NoProductContract.Effect.NavigateToBack)
    }
}