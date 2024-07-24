package com.jk.transaction

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.State
import com.jk.common_ui.composable.TextWithDropDownMenu
import com.jk.money_common_ui.CurrencyUI

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TransactionScreen(
    viewModel: AddNewTransactionViewModel,
    categoryListId: List<String>?,
    onBackClick: (() -> Unit)?
) {
    val currencyList = viewModel.currencyListState.collectAsState()
    Scaffold(containerColor = FinanceHelperTheme.colors.primaryBackground, topBar = {
        Row(modifier = Modifier.padding(FinanceHelperTheme.shape.padding)) {
            if (onBackClick != null)
                IconButton(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = onBackClick
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "ic_back"
                    )
                }
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = "Create Transaction",
                style = FinanceHelperTheme.typography.label
            )
        }
    }) {
        Column(
            modifier = Modifier.padding(
                top = it.calculateTopPadding(),
                bottom = it.calculateBottomPadding(),
                start = it.calculateStartPadding(LayoutDirection.Ltr),
                end = it.calculateEndPadding(LayoutDirection.Rtl)
            )
        ) {
            TransactionInfoSection(
                modifier = Modifier.padding(
                    start = 10.dp, end = 10.dp
                ), currencyList.value
            )
        }
    }
}

/**
 * Transaction section contains base info about transaction f.e name amount currency etc.
 * */
@Composable
fun TransactionInfoSection(modifier: Modifier = Modifier, currencyList: State<List<CurrencyUI>>) {
    val transactionName = rememberSaveable() {
        mutableStateOf("")
    }
    val amount = rememberSaveable() {
        mutableStateOf("")
    }
    val currency = rememberSaveable() {
        mutableStateOf("")
    }
    Column(modifier=modifier,verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(text = "General", style = FinanceHelperTheme.typography.h2)
        Row( horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            TransactionNameText(
                modifier = Modifier.weight(2f),
                transactionName.value,
                onValueChange = { transactionName.value = it },
                onError = {}
            )

            CurrencyAmountText(
                modifier = Modifier.weight(1f),
                value = amount.value,
                onValueChange = {
                    amount.value = if (it.count { c -> c == '.' } <= 1) {
                        it
                    } else amount.value
                }) {
            }

            CurrencyDropDownMenu(
                modifier = Modifier
                    .weight(1f),
                currencyListState = currencyList,
                placeholderText = "Currency",
                color = FinanceHelperTheme.colors.defaultButtonColor
            ) {
                currency.value = it
                Log.e("TAG", "TransactionInfoSection:${it} ")
            }
        }
    }
}
@Composable
fun CategorySection(modifier: Modifier,){

}

@Composable
fun CurrencyDropDownMenu(
    modifier: Modifier,
    currencyListState: State<List<CurrencyUI>>,
    color: Color,
    placeholderText: String,
    onClick: (String) -> Unit
) {
    val currencyList by remember(currencyListState) {
        derivedStateOf {
            when (currencyListState) {
                is State.Success -> {
                    currencyListState.data
                }

                else -> {
                    listOf<CurrencyUI>()
                }
            }
        }
    }
    TextWithDropDownMenu(
        modifier = modifier,
        list = currencyList.map { it.name },
        placeholderText = placeholderText,
        color = color, onClick = onClick
    )

}