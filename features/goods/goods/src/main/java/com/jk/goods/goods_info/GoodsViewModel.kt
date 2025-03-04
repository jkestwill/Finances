package com.jk.goods.goods_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jk.common_data.Dispatchers
import com.jk.common_data.map
import com.jk.common_ui.State
import com.jk.common_ui.toState
import com.jk.goods.GoodsRepository
import com.jk.goods_common_ui.GoodsUI
import com.jk.goods_common_ui.GoodsUIMapper
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
    private val goodsMapper: GoodsUIMapper,
    private val dispatcher: Dispatchers
) : ViewModel() {

    private var _goodsFlow = MutableStateFlow<State<GoodsUI>>(State.None)
    val goodsFlow: StateFlow<State<GoodsUI>> = _goodsFlow

    fun getById(id: String) {
        viewModelScope.launch(dispatcher.io) {
            goodsRepository.getById(id)
                .map {
                    it.map { goods -> goodsMapper.toUI(goods) }.toState()
                }
                .stateIn(
                    viewModelScope,
                    started = SharingStarted.Lazily,
                    initialValue = State.None
                )
                .collect {
                    _goodsFlow.value = it
                }
        }

    }

    fun getCostHistoryList(goodsId:String){

    }

    fun getStoreList(goodsId:String){

    }
}