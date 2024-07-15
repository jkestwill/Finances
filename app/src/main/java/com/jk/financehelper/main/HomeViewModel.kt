package com.jk.financehelper.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.jk.category_data.CategoryRepository
import com.jk.transaction.TransactionCategory
import com.jk.common_data.SearchParams
import com.jk.financehelper.domain.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private var categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    val count = MutableStateFlow<Int>(5)

    private var _transactionsFlow = MutableSharedFlow<List<Transaction>>(1)
    val transactionsFlow: SharedFlow<List<Transaction>> get() = _transactionsFlow

    private var _expensesFlow = MutableStateFlow(0.0)
    val expensesFLow: StateFlow<Double> get() = _expensesFlow

    private var _errorFlow = MutableStateFlow<Throwable?>(null)
    val errorFlow: SharedFlow<Throwable?> get() = _errorFlow

    // брать из конфига
    // при первом запуске спрашивается основная валюта
    val currentCurrency = MutableStateFlow<String>("BYN")

    val categoryFlow = categoryRepository.getList(SearchParams.getDefault()).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = PagingData.empty()
    )
    val transactionErrors = MutableSharedFlow<Throwable>(1)

    fun addCategory(category: com.jk.transaction.TransactionCategory) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.add(category)
        }
    }

    fun getIncomeSumByDatePeriod(dateStart: LocalDateTime, dateEnd: LocalDateTime) {
        viewModelScope.launch {}

    }

    fun getExpensesSumByDatePeriod(dateStart: LocalDateTime, dateEnd: LocalDateTime) {
        viewModelScope.launch {
            transactionRepository.getExpensesSum(dateStart, dateEnd, currentCurrency.value)
                .collect { resp ->
                    _expensesFlow.emit(resp)
                }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}

