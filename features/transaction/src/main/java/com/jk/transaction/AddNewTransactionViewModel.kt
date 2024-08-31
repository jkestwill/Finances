package com.jk.transaction

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.jk.category_common_ui.CategoryUI
import com.jk.category_common_ui.toUI
import com.jk.category_data.CategoryRepository
import com.jk.common_data.Dispatchers
import com.jk.common_data.LoggerTags
import com.jk.common_data.SearchParams
import com.jk.common_data.map
import com.jk.common_data.sha256
import com.jk.common_goods_data.Goods
import com.jk.common_ui.State
import com.jk.common_ui.map
import com.jk.common_ui.toState
import com.jk.goods.GoodsRepository
import com.jk.goods_common_ui.GoodsUI
import com.jk.goods_common_ui.toUI
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyUI
import com.jk.money_common_ui.toUI
import com.jk.money_data.CurrencyRepository
import com.jk.transaction_common_ui.OperationUI
import com.jk.transaction_common_ui.ScheduleUI
import com.jk.transaction_common_ui.TransactionUI
import com.jk.transaction_common_ui.toTransaction
import com.jk.transaction_data.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
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

    fun getGoodsListById(idList: List<String>) {
        viewModelScope.launch(dispatchers.io) {
            _incomingGoodsFlow.emitAll(goodsRepository.getByIdList(idList).map { apiRequest ->
                apiRequest.toState().map { goodsList -> goodsList.map { goods -> goods.toUI() } }
            })
        }
    }

    fun addTransaction(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch(dispatchers.io) {
            val transaction = try {
                checkAndBuild()
            } catch (e: IllegalArgumentException) {
                logger?.info(e.message)
                return@launch
            }
            transactionRepository.addTransaction(transaction.toTransaction())
                .onSuccess {
                    onSuccess()
                }
                .onFailure {
                    onFailure(it.message ?: "Add transaction: error")
                }

        }
    }

    private fun check(transactionUI: TransactionUI.Builder): TransactionUI {
        val preBuild = transactionUI.build()
        when {
            preBuild.type.name.isEmpty() -> {
                throw IllegalArgumentException("Transaction type must not be empty")
            }

            preBuild.id.isEmpty() -> {
                transactionUI.id("${preBuild.date}${preBuild.operation.name}${preBuild.operation.money.amount}".sha256())
            }
        }
        return transactionUI.build()
    }


    private fun checkAndBuild(): TransactionUI {
        check(newTransactionState.value)
        val operation = checkAndBuild(
            operationBuilder.value,
            newGoodsBuilderList,
            editableCategoriesStateList,
            scheduleListBuilder
        )
        return newTransactionState.value.setOperation(operation).build()
    }

    private fun checkAndBuild(
        operationBuilder: OperationUI.Builder,
        goodsBuilderList: List<GoodsUI.Builder>,
        categoryList: List<CategoryUI>,
        scheduleBuilder: List<ScheduleUI.Builder>
    ): OperationUI {
        val preBuild = operationBuilder.build()
        checkMoney(preBuild.money)
        val goodsList = check(goodsBuilderList)
        val scheduleList = check(scheduleBuilder)
        when {
            preBuild.id.isEmpty() -> {
                operationBuilder.id("${preBuild.categoryList.size}${preBuild.goodsList.size}${preBuild.name}${preBuild.scheduleList.size}".sha256())
            }

            preBuild.name.isEmpty() -> {
                throw IllegalArgumentException("Transaction name can't be empty")
            }
        }
        return operationBuilder
            .setGoodsList(goodsList)
            .setCategoryList(categoryList)
            //.setScheduleList(scheduleList)
            .build()
    }

    private fun checkMoney(moneyUI: MoneyUI) {
        when {
            moneyUI.amount < 0 -> {
                throw IllegalArgumentException("Money amount can't be below zero")
            }

            moneyUI.currency.name.isEmpty() -> {
                // provide default value in config
                throw IllegalArgumentException("Currency name can't be empty")
            }
        }
    }

    @JvmName("goods_list")
    private fun check(goodsBuilder: List<GoodsUI.Builder>): List<GoodsUI> =
        goodsBuilder.onEach { check(it) }.map { it.build() }

    @JvmName("goods")
    private fun check(goodsBuilder: GoodsUI.Builder) {
        val preBuild = goodsBuilder.build()
        checkMoney(preBuild.cost)
        when {
            preBuild.name.isEmpty() -> {
                throw IllegalArgumentException("Goods name can't be empty")
            }

            preBuild.amount < 0 -> {
                throw IllegalArgumentException("Goods count can't be empty")
            }

            preBuild.id.isEmpty() -> {
                goodsBuilder.id("${preBuild.name}${preBuild.cost}${preBuild.amount}".sha256())
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