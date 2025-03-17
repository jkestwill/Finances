package com.jk.goods.goods_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jk.common_data.DispatcherProvider
import com.jk.common_data.Validator
import com.jk.common_data.map
import com.jk.common_ui.UIState
import com.jk.common_ui.toState
import com.jk.goods.GoodsRepository
import com.jk.goods_common_ui.models.GoodsUI
import com.jk.goods_common_ui.GoodsUIMapper
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.CurrencyUIMapper
import com.jk.money_data.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoodsViewModel @Inject constructor(
    private val goodsRepository: GoodsRepository,
    private val currencyRepository: CurrencyRepository,
    private val currencyUIMapper: CurrencyUIMapper,
    private val goodsMapper: GoodsUIMapper,
    private val goodsValidator: Validator<GoodsUI>,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private var _goodsFlow = MutableStateFlow<UIState<GoodsUI>>(UIState.None)
    val goodsFlow: StateFlow<UIState<GoodsUI>> = _goodsFlow

    val currencyListFlow: StateFlow<UIState<List<CurrencyUI>>> =
        currencyRepository.getCurrencyList()
            .map { state ->
                state.map { currencyList ->
                    currencyList.map { currency ->
                        currencyUIMapper.toUI(currency)
                    }
                }.toState()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = UIState.None
            )

    fun getById(id: String) {
        viewModelScope.launch(dispatcher.io) {
            goodsRepository.getById(id)
                .map {
                    it.map { goods -> goodsMapper.toUI(goods) }.toState()
                }
                .stateIn(
                    viewModelScope,
                    started = SharingStarted.Lazily,
                    initialValue = UIState.None
                )
                .collect {
                    _goodsFlow.value = it
                }
        }

    }


    fun create(goodsUI: GoodsUI) {
        goodsValidator.validate(goodsUI)
        viewModelScope.launch {
            goodsRepository.add(listOf(goodsMapper.toGoods(goodsUI)))
        }
    }

    fun getCostHistoryList(goodsId: String) {

    }

    fun getStoreList(goodsId: String) {

    }
}