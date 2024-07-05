package com.jk.goods

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jk.common.State
import com.jk.common.toState
import com.jk.common_data.map
import com.jk.money_data.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    fun add(goodsList: List<Goods>) {
        viewModelScope.launch {
            goodsRepository.add(goodsList)
        }

    }

    companion object {
        val test = listOf(
            Goods(
                id = "gg",
                name = "fimoz",
                specifications = listOf(
                    Specification(
                        id = "sp", text = "weight", amount = 2f,
                        measure = Measure("mm", Language("ll", text = "kg", "english", "eng"))
                    )
                ),
                cost = com.jk.money_data.Money("qq", 20.0, Currency("zxc", "BYN"))
            )
        )
    }
}