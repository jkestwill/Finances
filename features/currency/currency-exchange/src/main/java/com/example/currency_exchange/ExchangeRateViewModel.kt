package com.example.currency_exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyexchangeapi.ExchangeRateRequestParams
import com.jk.common_data.map
import com.jk.common_ui.State
import com.jk.common_ui.toState
import com.jk.exchange_rate_data.ApiRequestMergeStrategy
import com.jk.exchange_rate_data.CurrencyExchangeRepository
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
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ExchangeRateViewModel @Inject constructor(
    private val exchangeRateRepository:CurrencyExchangeRepository,
) : ViewModel() {
    private val job = SupervisorJob()

    private var _exchangeRateState = MutableStateFlow<State<ExchangeRateUI>>(State.None)
    val exchangeRateState: StateFlow<State<ExchangeRateUI>> get() = _exchangeRateState

    //todo exchangeService идет по дефолту в настройках
    fun getBynToCurrencyExchange(currencyIn: String, currencyOut:String, exchangeServiceName:String) {
        viewModelScope.launch(Dispatchers.IO) {
            _exchangeRateState.emitAll(
                exchangeRateRepository.getExchangeRate(
                    exchangeServiceName,
                    exchangeRateRequestParams = ExchangeRateRequestParams(currencyIn,currencyOut, LocalDateTime.now()),
                    mergeStrategy = ApiRequestMergeStrategy()
                )
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


//    fun forceUpdate(currency: String) {
//        exchangeRateNBRBRepository.fetchLatest(currency)
//    }

    override fun onCleared() {
        job.cancel()
    }
}

