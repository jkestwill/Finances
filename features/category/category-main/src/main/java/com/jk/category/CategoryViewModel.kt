package com.jk.category

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.jk.category_common_ui.CategoryUI
import com.jk.category_common_ui.CategoryUIMapper
import com.jk.category_data.CategoryRepository
import com.jk.category_data.CategorySortBy
import com.jk.common_ui.toState
import com.jk.common_data.SearchParams
import com.jk.common_ui.State
import com.jk.common_ui.map
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyUI
import com.jk.transaction_common_ui.OperationPreviewUI
import com.jk.transaction_common_ui.TransactionPreviewUI
import com.jk.transaction_data.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.random.Random

// вывести пагинацию в compose
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository,
    private val categoryUIMapper: CategoryUIMapper
) : ViewModel() {

    private var _categoryFlow =
        MutableStateFlow<State<CategoryUI>>(State.None)
    val categoryFlow: StateFlow<State<CategoryUI>> get() = _categoryFlow

    private var _transactionPagingList =
        MutableStateFlow<PagingData<TransactionPreviewUI>>(PagingData.empty())
    val transactionPagingList: Flow<PagingData<TransactionPreviewUI>> get() = _transactionPagingList

    companion object {
        const val NAME_LENGTH_VISIBILITY_THRESHOLD = 10
        val test = List(20) {
            TransactionPreviewUI(
                id = "qq",
                operation = OperationPreviewUI(
                    id = "zxc",
                    name = "qq",
                    money = MoneyUI(
                        id = "zxc2",
                        amount = 200.0,
                        currency = CurrencyUI(id = "qwe", name = "BYN")
                    ),
                ),
                date = LocalDateTime.of(
                    Random.nextInt(1988, 2023),
                    Random.nextInt(1, 12),
                    Random.nextInt(1, 28),
                    Random.nextInt(1, 24),
                    Random.nextInt(1, 59)
                ),
                type = "online"
            )
        }.sortedBy {
            it.date
        }

    }

    fun getById(categoryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _categoryFlow.emitAll(categoryRepository.getById(categoryId).map { apiRequest ->
                apiRequest.toState().map { category ->
                    categoryUIMapper.toCategoryUI(
                        category.copy(
                            name = limitString(
                                category.name,
                                limit = NAME_LENGTH_VISIBILITY_THRESHOLD
                            )
                        )
                    )
                }
            })
        }
    }


    private fun limitString(text: String, limit: Int): String {
        return if (text.length > limit)
            "${
                text.substring(0..limit)
            }..."
        else text
    }

    fun getAllTransactionsPreviewByCategoryId(
        categoryId: String,
        q: String = "",
        sortBy: String = "id",
        isAsc: Boolean = true
    ) {
        viewModelScope.launch {
//            _transactionPagingList.emitAll(
//                transactionRepository.getTransactionPreviewByCategoryId(
//                    categoryId,
//                    q,
//                    sortBy,
//                    isAsc
//                )
//                    .map { pagingData ->
//                        pagingData.map { transactionPreview ->
//                            transactionPreview.copy(
//                                operation = transactionPreview.operation.copy(
//                                    name = limitString(
//                                        transactionPreview.operation.name,
//                                        NAME_LENGTH_VISIBILITY_THRESHOLD
//                                    )
//                                )
//                            )
//                        }
//                    }.onEach {
//                        it.map {
//                            Log.e("TAG", "getAllTransactionsPreviewByCategoryId: ${it}")
//                            it
//                        }
//                    }
//            )
        }

    }

    fun getAll(q: String, sortBy: CategorySortBy, isAsc: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.getList(SearchParams(q, sortBy.fieldName, isAsc)).map {
                it.map { category ->
                    category.copy(
                        name = limitString(category.name, NAME_LENGTH_VISIBILITY_THRESHOLD)
                    )
                }
            }
        }
    }
}
