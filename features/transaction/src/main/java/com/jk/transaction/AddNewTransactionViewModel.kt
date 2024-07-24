package com.jk.transaction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.jk.common_ui.State
import com.jk.common_data.map
import com.jk.common_ui.toState
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.toUI
import com.jk.money_data.CurrencyRepository
import com.jk.transaction_common_ui.TransactionUI
import com.jk.transaction_common_ui.toTransaction
import com.jk.transaction_data.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewTransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val currencyRepository: CurrencyRepository
) : ViewModel() {
    companion object{
        private const val TAG = "AddNewTransactionViewMo"
    }
    private var _transactionState = MutableStateFlow<PagingData<TransactionUI>>(PagingData.empty())
    val transactionState: StateFlow<PagingData<TransactionUI>> get() = _transactionState
    val currencyListState: StateFlow<State<List<CurrencyUI>>> = currencyRepository.getCurrencyList()
        .map {
            it.map { list ->
                list.map { currency ->
                    currency.toUI()
                }
            }.toState()
        }.onEach {
            if(it is State.Success)
            Log.e(TAG, "${it.data}" )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily, State.None
        )

    fun getAllTransactions(q: String, sortBy: String, isAsc: Boolean) {
        viewModelScope.launch {

        }
    }

    fun addTransaction(transactionUI: TransactionUI) {
        viewModelScope.launch {
            transactionRepository.addTransaction(transactionUI.toTransaction())
        }
    }

}