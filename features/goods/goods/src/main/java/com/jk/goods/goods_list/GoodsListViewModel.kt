package com.jk.goods.goods_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.jk.common_data.Dispatchers
import com.jk.common_data.SearchParams
import com.jk.goods.GoodsRepository
import com.jk.goods_common_ui.GoodsMoneyDateUI
import com.jk.goods_common_ui.GoodsPreviewUI
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
class GoodsListViewModel @Inject constructor(
    private val goodsRepository: GoodsRepository,
    private val goodsUIMapper: GoodsUIMapper,
    private val dispatcherProvider: Dispatchers
) : ViewModel() {

    private var _goodsStateFlow: StateFlow<PagingData<GoodsPreviewUI>> =
        MutableStateFlow<PagingData<GoodsPreviewUI>>(PagingData.empty())

    val goodsListStateFlow: StateFlow<PagingData<GoodsPreviewUI>> = _goodsStateFlow


    fun getGoodsList(q: String, sortBy: String, isAsc: Boolean) {
        viewModelScope.launch(dispatcherProvider.io) {
            _goodsStateFlow =
                goodsRepository.getGoodsPreviewList(SearchParams(q, sortBy = sortBy, isAsc = isAsc))
                    .map { pagingData ->
                        pagingData.map { goodsList ->
                            goodsUIMapper.toPreviewUI(goodsList)
                        }
                    }
                    .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
        }
    }

    fun add(goodsList: List<GoodsMoneyDateUI>) {
        viewModelScope.launch(dispatcherProvider.io) {
            goodsRepository.add(goodsList.map {
                goodsUIMapper.toGoods(it)
            })
        }
    }



}