package com.jk.transaction

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.jk.common_ui.FinanceHelperTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TransactionScreen(categoryListId: List<String>?) {
    Scaffold(containerColor = FinanceHelperTheme.colors.primaryBackground, topBar = {
        Row() {
            Text("Create Transaction", style = FinanceHelperTheme.typography.h2)
        }
    }) {
        TransactionInfoSection(
            modifier = Modifier.padding(
                top = it.calculateTopPadding(),
                bottom = it.calculateBottomPadding(),
                start = it.calculateStartPadding(LayoutDirection.Ltr),
                end = it.calculateEndPadding(layoutDirection = LayoutDirection.Rtl)
            )
        )
    }
}

/**
 * Transaction section contains base info about transaction f.e name amount currency etc.
 * */
@Composable
fun TransactionInfoSection(modifier: Modifier = Modifier) {
    val transactionName = rememberSaveable() {
        mutableStateOf("")
    }

    val amount = rememberSaveable() {
        mutableStateOf("")
    }
    val currency = rememberSaveable() {
        mutableStateOf("")
    }

    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        TransactionNameText(
            modifier = Modifier.weight(2f),
            transactionName.value,
            onValueChange = { transactionName.value = it },
            onError = {}
        )

        CurrencyAmountText(modifier = Modifier.weight(1f), value = amount.value, onValueChange = {
            amount.value = if (it.count { c -> c == '.' } <= 1) {
                it
            } else amount.value
        }) {

        }

    }


}