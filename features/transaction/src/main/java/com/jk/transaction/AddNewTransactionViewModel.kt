package com.jk.transaction

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.jk.category_common_ui.CategoryUI
import com.jk.category_common_ui.CategoryUIMapper
import com.jk.category_data.CategoryRepository
import com.jk.common_data.Dispatchers
import com.jk.common_data.LoggerTags
import com.jk.common_data.SearchParams
import com.jk.common_data.map
import com.jk.common_ui.State
import com.jk.common_ui.map
import com.jk.common_ui.toState
import com.jk.goods.GoodsRepository
import com.jk.goods_common_ui.GoodsUI
import com.jk.goods_common_ui.toUI
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.toUI
import com.jk.money_data.CurrencyRepository
import com.jk.transaction.validator.TransactionValidator
import com.jk.transaction_common_ui.OperationUI
import com.jk.transaction_common_ui.ScheduleUI
import com.jk.transaction_common_ui.TransactionUI
import com.jk.transaction_common_ui.TransactionUiMapper
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
    private val dispatchers: Dispatchers,
    private val transactionValidator: TransactionValidator,
    private val transactionMapper: TransactionUiMapper,
    private val categoryMapper:CategoryUIMapper
) : ViewModel() {

    private var _transactionState = MutableStateFlow<PagingData<TransactionUI>>(PagingData.empty())
    val transactionState: StateFlow<PagingData<TransactionUI>> get() = _transactionState

    private var _categoryList = MutableStateFlow<State<List<CategoryUI>>>(State.None)
    val categoryList: StateFlow<State<List<CategoryUI>>> = _categoryList

    val editableCategoriesStateList = mutableStateListOf<CategoryUI>()

    // костыль чтобы сравнивать занчения приходящие из параметров composable функции TransactionScreen
    // для того чтобы после удаления категории при поровороте экрана или его обновлении не приходили удаленные категории
    var prevCategoryIdList: List<String>? = null

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
            .cachedIn(viewModelScope)
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily, PagingData.empty()
            )
    private var _incomingGoodsFlow = MutableStateFlow<State<List<GoodsUI>>>(State.None)
    val incomingGoodsFlow: StateFlow<State<List<GoodsUI>>> get() = _incomingGoodsFlow


    val newGoodsBuilderList = mutableStateListOf<GoodsUI.Builder>()
    val operationBuilder =
        MutableStateFlow<OperationUI.Builder>(OperationUI.Builder())

    val newTransactionState = MutableStateFlow(TransactionUI.Builder())

    val scheduleListBuilder = mutableStateListOf(ScheduleUI.Builder())

    fun getCategoryListById(idList: List<String>) {
        viewModelScope.launch(dispatchers.io) {
            _categoryList.emitAll(
                categoryRepository.getByCategoryListId(idList)
                    .map { req -> req.map { list -> list.map { cat -> categoryMapper.toCategoryUI(cat)} }.toState() }
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

    fun getGoodsListById(idList: List<String>) {
        viewModelScope.launch(dispatchers.io) {
            _incomingGoodsFlow.emitAll(goodsRepository.getByIdList(idList).map { apiRequest ->
                apiRequest.toState().map { goodsList -> goodsList.map { goods -> goods.toUI() } }
            })
        }
    }

    fun addTransaction(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch(dispatchers.io) {
            try {
                val transactionUI:TransactionUI.Builder = newTransactionState.value.setOperation(
                    operationBuilder.value
                        .setGoodsList(
                            newGoodsBuilderList.map { it.build() }
                        ).setCategoryList(editableCategoriesStateList)
                        .build()
                )
                transactionValidator.validate(newTransactionState.value)
                transactionRepository.addTransaction(transactionMapper.toTransaction(transactionUI.build()))
            } catch (e: IllegalArgumentException) {
                logger?.info(e.message)
                return@launch
            }

        }
    }


    @JvmName("schedule_list")
    private fun check(scheduleBuilderList: List<ScheduleUI.Builder>): List<ScheduleUI> {
        return scheduleBuilderList.onEach { check(it) }.map { it.build() }
    }

    @JvmName("schedule")
    private fun check(scheduleBuilder: ScheduleUI.Builder) {
        val preBuild = scheduleBuilder.build()
        when {
            preBuild.countLeft < 0 -> throw IllegalArgumentException("Schedule count can't be below zero")
            preBuild.repeatPeriodMillis?.compareTo(0L) == -1 -> throw IllegalArgumentException("Schedule repeatPeriodMillis can't be below zero")
        }
    }


}