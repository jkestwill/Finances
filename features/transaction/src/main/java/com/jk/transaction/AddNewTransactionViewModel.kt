package com.jk.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.jk.transaction_common_ui.TransactionUI
import com.jk.transaction_common_ui.toTransaction
import com.jk.transaction_data.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewTransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
):ViewModel() {
    private var _transactionState = MutableStateFlow<PagingData<TransactionUI>>(PagingData.empty())
    val transactionState: StateFlow<PagingData<TransactionUI>> get()= _transactionState


    fun getAllTransactions(q:String,sortBy:String,isAsc:Boolean){
        viewModelScope.launch {

        }
    }

    fun addTransaction(transactionUI: TransactionUI){
        viewModelScope.launch {
            transactionRepository.addTransaction(transactionUI.toTransaction())
        }
    }

}