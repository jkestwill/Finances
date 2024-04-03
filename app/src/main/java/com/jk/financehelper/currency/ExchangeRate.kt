package com.jk.financehelper.currency

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.currency_exchange.ExchangeRateViewModel

@Composable
fun ExchangeRate(viewModel: ExchangeRateViewModel) {
    Button(onClick = { viewModel.getBynToCurrencyExchange("USD") }) {
        Text(text = "zxc")
    }

}