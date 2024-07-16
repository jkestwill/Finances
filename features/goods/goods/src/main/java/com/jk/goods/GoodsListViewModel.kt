package com.jk.goods

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jk.common_ui.State
import com.jk.goods_common_ui.GoodsUI
import com.jk.common_ui.toState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.jk.common_data.map
import com.jk.goods_common_ui.toGoods
import com.jk.goods_common_ui.toUI

@HiltViewModel
class GoodsListViewModel @Inject constructor(
    private val goodsRepository: GoodsRepository
) : ViewModel() {

    private var _goodsStateFlow = MutableStateFlow<State<List<GoodsUI>>>(State.None)
    val goodsStateFlow: Flow<State<List<GoodsUI>>> get() = _goodsStateFlow


    fun getAllGoods() {
        viewModelScope.launch {
            goodsRepository.getAllFromDatabase().map { apiRequest ->
                apiRequest.map { goodsList ->
                    goodsList.map { goods ->
                        goods.toUI()
                    }
                }.toState()
            }.collectLatest {
                _goodsStateFlow.value = it
                when (it) {
                    is State.Success -> {

                        Log.e("VIEWMODEl", "getAllGoods: ${it.data}")
                    }

                    else -> {}
                }
            }
        }
    }

    fun add(goodsList: List<GoodsUI>) {
        viewModelScope.launch {
            goodsRepository.add(goodsList.map {
                it.toGoods()
            })
        }

    }


}