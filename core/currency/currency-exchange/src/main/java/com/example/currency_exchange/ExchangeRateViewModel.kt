package com.example.currency_exchange

import androidx.lifecycle.ViewModel
import com.jk.common_data.ApiRequest

import com.jk.exchange_rate_data.ExchangeRate
import com.jk.exchange_rate_data.NBRBRepository
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

    private var _exchangeRateState = MutableStateFlow<State>(State.None)
    val exchangeRateState: StateFlow<State> get() = _exchangeRateState

    fun getBynToCurrencyExchange(currency: String) {
        viewModelScope.launch {
            _exchangeRateState.emitAll(
                exchangeRateNBRBRepository.getLast(currency = currency)
                    .map {
                        it.toState()
                    }
                    .stateIn(scope= viewModelScope, started = SharingStarted.Lazily, initialValue = State.None)
            )
        }
    }


    fun forceUpdate(currency: String){
         exchangeRateNBRBRepository.fetchLatest(currency)
    }
    override fun onCleared() {
        job.cancel()
    }
}

fun ApiRequest<ExchangeRate>.toState(): State {
    return when (this) {
        is ApiRequest.Success -> {
            State.Success(checkNotNull(data))
        }

        is ApiRequest.Error -> {
            State.Error(data = data, message = error?.message ?: "")
        }

        is ApiRequest.Loading -> {
            State.Loading(data)
        }
    }
}

sealed class State {
    data object None : State()
    class Success(val data: ExchangeRate) : State()
    class Loading(val data: ExchangeRate?) : State()
    class Error(val data: ExchangeRate?, val message: String) : State()

}