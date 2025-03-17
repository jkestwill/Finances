package com.jk.money_common_ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jk.common_ui.UIState
import com.jk.common_ui.composable.TextWithDropDownMenu


@Composable
fun CurrencyDropDownMenu(
    modifier: Modifier=Modifier,
    currencyListState: UIState<List<CurrencyUI>>,
    color: Color,
    placeholderText: String,
    onClick: (CurrencyUI) -> Unit
) {
    val currencyList = remember(currencyListState) {
        derivedStateOf {
            when (currencyListState) {
                is UIState.Success -> {
                    currencyListState.data
                }

                else -> {
                    listOf()
                }
            }
        }
    }

    TextWithDropDownMenu(
        modifier = modifier,
        list = currencyList.value,
        placeholderText = placeholderText,
        color = color,
        onClick = onClick
    )
}