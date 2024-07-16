package com.example.currency_exchange

import androidx.lifecycle.ViewModel
import com.jk.common_data.map
import com.jk.common_ui.State
import com.jk.common_ui.toState
import com.jk.exchange_rate_data.NBRBRepository
import com.jk.money_common_ui.ExchangeRateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeRateViewModel @Inject constructor(
    private val exchangeRateNBRBRepository: NBRBRepository
) : ViewModel() {
    private val job = SupervisorJob()
    private val viewModelScope = CoroutineScope(job + Dispatchers.Default)

    private var _exchangeRateState = MutableStateFlow<State<ExchangeRateUI>>(State.None)
    val exchangeRateState: StateFlow<State<ExchangeRateUI>> get() = _exchangeRateState

    fun getBynToCurrencyExchange(currency: String) {
        viewModelScope.launch {
            _exchangeRateState.emitAll(
                exchangeRateNBRBRepository.getLast(currency = currency)
                    .map { apiRequest ->
                        apiRequest.map { exRate -> exRate.toExchangeRateUI() }.toState()
                    }
                    .stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.Lazily,
                        initialValue = State.None
                    )
            )
        }
    }


    fun forceUpdate(currency: String) {
        exchangeRateNBRBRepository.fetchLatest(currency)
    }

    override fun onCleared() {
        job.cancel()
    }
}

