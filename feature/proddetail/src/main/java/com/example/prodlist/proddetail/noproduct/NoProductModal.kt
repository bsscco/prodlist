package com.example.prodlist.proddetail.noproduct

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.prodlist.designsys.modal.AlertModal

@Composable
internal fun NoProductModal(
    state: NoProductContract.State,
    onEvent: (NoProductContract.Event) -> Unit,
) {
    AlertModal(
        state = AlertModal.State(
            visible = state.showModal,
            content = "상품이 없습니다.",
            primaryButtonText = "확인",
        ),
        onEvent = { event ->
            when (event) {
                is AlertModal.Event.OnPrimaryClick -> onEvent(NoProductContract.Event.OnOkClicked)
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NoProductModal(
        state = NoProductContract.State(
            showModal = true,
        ),
        onEvent = {},
    )
}