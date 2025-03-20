package com.jk.transaction

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.jk.category.CategoryUIMapper
import com.jk.category_common_ui.CategoryUI
import com.jk.category_data.CategoryRepository
import com.jk.common_data.DispatcherProvider
import com.jk.common_data.LoggerTags
import com.jk.common_data.SearchParams
import com.jk.common_data.map
import com.jk.common_ui.UIState
import com.jk.common_ui.map
import com.jk.common_ui.toState
import com.jk.goods.GoodsRepository
import com.jk.goods_common_ui.models.GoodsUI
import com.jk.goods_common_ui.GoodsUIMapper
import com.jk.goods_common_ui.models.GoodsPurchaseUI
import com.jk.money_account_data.MoneyAccountRepository
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
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
    private val transactionValidator: TransactionValidator,
    private val transactionMapper: TransactionUiMapper,
    private val categoryMapper: CategoryUIMapper,
    private val goodsMapper: GoodsUIMapper,
    private val moneyAccountRepository: MoneyAccountRepository,
    private val moneyAccountUIMapper: MoneyAccountUIMapper,
    private val transactionStringResourceExceptionHandler: TransactionStringResourceExceptionHandler,
    @Named(LoggerTags.ADD_NEW_TRANSACTION) private val logger: Logger?,
    private val dispatchers: DispatcherProvider,
) : ViewModel() {

    private var _transactionState = MutableStateFlow<PagingData<TransactionUI>>(PagingData.empty())
    val transactionState: StateFlow<PagingData<TransactionUI>> get() = _transactionState

    private var _categoryList = MutableStateFlow<UIState<List<CategoryUI>>>(UIState.None)
    val categoryList: StateFlow<UIState<List<CategoryUI>>> = _categoryList

    private val coroutineIOScope = CoroutineScope(dispatchers.io + SupervisorJob())

    private var _errorMessageStateFlow = MutableStateFlow("")
    val errorMessageStateFlow: StateFlow<String> get() = _errorMessageStateFlow

    val moneyAccountList = moneyAccountRepository.getAllFromDB()
        .map { state ->
            state.map { list ->
                list.map { acc ->
                    moneyAccountUIMapper.toUI(acc)
                }
            }.toState()
        }
        .stateIn(coroutineIOScope, SharingStarted.Eagerly, UIState.None)


    val editableCategoriesStateList = mutableStateListOf<CategoryUI>()

    // костыль чтобы сравнивать з значения приходящие из параметров composable функции TransactionScreen
    // для того чтобы после удаления категории при поровороте экрана или его обновлении не приходили удаленные категории
    var prevCategoryIdList: List<String>? = null

    val currencyListState: StateFlow<UIState<List<CurrencyUI>>> =
        currencyRepository.getCurrencyList()
            .map {
                it.map { list ->
                    list.map { currency ->
                        currency.toUI()
                    }
                }.toState()
            }.onEach {
                if (it is UIState.Success)
                    logger?.info("${it.data}")

            }
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily, UIState.None
            )

    val allGoodsFlow =
        goodsRepository.getAllFromDatabase(searchParams = SearchParams.getDefault())
            .map { request ->
                request.map { goods ->

                    goodsMapper.toUI(goods)
                }
            }
            .cachedIn(viewModelScope)
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily, PagingData.empty()
            )
    private var _incomingGoodsFlow = MutableStateFlow<UIState<List<GoodsPurchaseUI>>>(UIState.None)
    val incomingGoodsFlow: StateFlow<UIState<List<GoodsPurchaseUI>>> get() = _incomingGoodsFlow


    val newGoodsBuilderList = mutableStateListOf<GoodsUI.Builder>()
    val operationBuilder =
        MutableStateFlow<OperationUI.Builder>(OperationUI.Builder())

    val newTransactionState = MutableStateFlow(TransactionUI.Builder())

    val scheduleListBuilder = mutableStateListOf(ScheduleUI.Builder())


    fun getCategoryListById(idList: List<String>) {
        viewModelScope.launch(dispatchers.io) {
            _categoryList.emitAll(
                categoryRepository.getByCategoryListId(idList)
                    .map { req ->
                        req.map { list ->
                            list.map { cat ->
                                categoryMapper.toCategoryUI(
                                    cat
                                )
                            }
                        }.toState()
                    }
                    .onEach { state ->
                        if (state is UIState.Success)
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
                apiRequest.toState()
                    .map { goodsList -> goodsList.map { goods -> goodsMapper.toGoodsPurchaseUI(goods) } }
            })
        }
    }

    fun addTransaction() {
        viewModelScope.launch(dispatchers.io) {
            val transactionUI: TransactionUI.Builder =
                newTransactionState.value.setOperation(
                    operationBuilder.value
                        .setGoodsList(
                            newGoodsBuilderList.map { it.build() }
                        ).setCategoryList(editableCategoriesStateList)
                        .build()
                )
            transactionStringResourceExceptionHandler.suspendGetStringErrorFromResource(
                {
                    transactionValidator.validate(newTransactionState.value)
                    transactionRepository.addTransaction(
                        transactionMapper.toTransaction(transactionUI.build())
                    )

                },
                catchBlock = { errorMessage: String ->

                }
            )

        }
    }

}