package com.jk.goods.goods_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.jk.common_data.DispatcherProvider
import com.jk.common_data.SearchParams
import com.jk.goods.GoodsRepository
import com.jk.goods_common_ui.models.GoodsUI
import com.jk.goods_common_ui.models.GoodsPreviewUI
import com.jk.goods_common_ui.GoodsUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoodsListViewModel @Inject constructor(
    private val goodsRepository: GoodsRepository,
    private val goodsUIMapper: GoodsUIMapper,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private var _goodsStateFlow: MutableStateFlow<PagingData<GoodsPreviewUI>> =
        MutableStateFlow<PagingData<GoodsPreviewUI>>(PagingData.empty())

    val goodsListStateFlow: StateFlow<PagingData<GoodsPreviewUI>> = _goodsStateFlow


    fun getGoodsList(q: String, sortBy: String, isAsc: Boolean) {
        viewModelScope.launch(dispatcherProvider.io) {
                goodsRepository.getGoodsPreviewList(SearchParams(q, sortBy = sortBy, isAsc = isAsc))
                    .onEach {
                        Log.e("Z", "getGoodsList:${it} ", )
                    }
                    .map { pagingData ->
                        pagingData.map { goodsList ->
                            goodsUIMapper.toPreviewUI(goodsList)
                        }
                    }
                    .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
                    .collect{
                        _goodsStateFlow.value = it
                    }
        }
    }

    fun add(goodsList: List<GoodsUI>) {
        viewModelScope.launch(dispatcherProvider.io) {
            goodsRepository.add(goodsList.map {
                goodsUIMapper.toGoods(it)
            })
        }
    }



}