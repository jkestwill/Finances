package com.jk.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.jk.category_common_ui.toUI
import com.jk.category_common_ui.CategoryUI
import com.jk.category_data.CategoryRepository
import com.jk.category_data.CategorySortBy
import com.jk.common_ui.toState
import com.jk.common_data.SearchParams
import com.jk.common_ui.State
import com.jk.transaction_common_data.TransactionPreview
import com.jk.transaction_data.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

// вывести пагинацию в compose
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private var _categoryList: StateFlow<State<CategoryUI>> =
        MutableStateFlow<State<CategoryUI>>(State.None)
    val categoryList: StateFlow<State<CategoryUI>> get() = _categoryList

    private var _transactionPagingList =
        MutableStateFlow<State<PagingData<TransactionPreview>>>(State.None)
    private val transactionPagingList: Flow<State<PagingData<TransactionPreview>>> get() = _transactionPagingList

    companion object {
        const val NAME_LENGTH_VISIBILITY_THRESHOLD = 20
    }

    fun getById(categoryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.getById(categoryId)
        }
    }


    fun getAllTransactionsPreviewByCategoryId(
        categoryId: String,
        q: String,
        sortBy: String,
        isAsc: Boolean
    ) {
        viewModelScope.launch {
            _transactionPagingList.emitAll(
                transactionRepository.getTransactionPreviewByCategoryId(
                    categoryId,
                    q,
                    sortBy,
                    isAsc
                ).map {
                    it.toState()
                }.stateIn(
                    viewModelScope,
                    started = SharingStarted.Lazily,
                    initialValue = State.None
                )
            )
        }

    }

    fun getAll(q: String, sortBy: CategorySortBy, isAsc: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.getList(SearchParams(q, sortBy.fieldName, isAsc)).map {
                it.map { category ->
                    category.toUI().copy(
                        name = if (category.name.length == NAME_LENGTH_VISIBILITY_THRESHOLD) category.name.substring(
                            0..NAME_LENGTH_VISIBILITY_THRESHOLD
                        ) else category.name
                    )
                }
            }
        }
    }
}
