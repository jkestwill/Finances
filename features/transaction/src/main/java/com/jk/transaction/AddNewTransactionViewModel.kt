package com.jk.transaction

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.jk.category_common_ui.CategoryUI
import com.jk.category_common_ui.toUI
import com.jk.category_data.CategoryRepository
import com.jk.common_data.Dispatchers
import com.jk.common_data.LoggerTags
import com.jk.common_data.SearchParams
import com.jk.common_data.map
import com.jk.common_goods_data.Goods
import com.jk.common_ui.State
import com.jk.common_ui.map
import com.jk.common_ui.toState
import com.jk.goods.GoodsRepository
import com.jk.goods_common_ui.GoodsUI
import com.jk.goods_common_ui.toUI
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
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.logging.Logger
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class AddNewTransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val currencyRepository: CurrencyRepository,
    private val categoryRepository: CategoryRepository,
    private val goodsRepository: GoodsRepository,
    @Named(LoggerTags.ADD_NEW_TRANSACTION) private val logger: Logger?,
    private val dispatchers: Dispatchers
) : ViewModel() {

    private var _transactionState = MutableStateFlow<PagingData<TransactionUI>>(PagingData.empty())

    private var _categoryList = MutableStateFlow<State<List<CategoryUI>>>(State.None)
    val categoryList: StateFlow<State<List<CategoryUI>>> = _categoryList

    val editableCategoriesStateList = mutableStateListOf<CategoryUI>()

    // костыль чтобы сравнивать занчения приходящие из параметров composable функции TransactionScreen
    // для того чтобы после удаления категории при поровороте экрана или его обновлении и не приходили удаленные категории
    var prevCategoryIdList: List<String>? = null
    val transactionState: StateFlow<PagingData<TransactionUI>> get() = _transactionState
    val currencyListState: StateFlow<State<List<CurrencyUI>>> = currencyRepository.getCurrencyList()
        .map {
            it.map { list ->
                list.map { currency ->
                    currency.toUI()
                }
            }.toState()
        }.onEach {
            if (it is State.Success)
                logger?.info("${it.data}")

        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily, State.None
        )

    val allGoodsFlow =
        goodsRepository.getAllFromDatabase(searchParams = SearchParams.getDefault())
            .map { request ->
                request.map { goods ->
                    goods.toUI()
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily, PagingData.empty()
            )

    val newGoodsBuilder = TransactionUI.Builder

    fun getCategoryListById(idList: List<String>) {
        viewModelScope.launch(dispatchers.io) {
            _categoryList.emitAll(
                categoryRepository.getByCategoryListId(idList)
                    .map { req -> req.map { list -> list.map { cat -> cat.toUI() } }.toState() }
                    .onEach { state ->
                        if (state is State.Success)
                            state.map { list ->
                                editableCategoriesStateList.clear()
                                editableCategoriesStateList.addAll(list)
                            }
                    }
            )
        }
    }


    fun addTransaction(transactionUI: TransactionUI) {
        viewModelScope.launch(dispatchers.io) {
            transactionRepository.addTransaction(transactionUI.toTransaction())
        }
    }

}