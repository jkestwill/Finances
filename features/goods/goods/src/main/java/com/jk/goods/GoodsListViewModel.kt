package com.jk.goods

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.jk.common_data.SearchParams
import com.jk.goods_common_ui.GoodsUI
import com.jk.goods_common_ui.toGoods
import com.jk.goods_common_ui.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoodsListViewModel @Inject constructor(
    private val goodsRepository: GoodsRepository
) : ViewModel() {

    private var _goodsStateFlow =
        goodsRepository.getAllFromDatabase(SearchParams.getDefault()).map { pagingData ->
            pagingData.map { goodsList ->
                goodsList.toUI()
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())


    fun add(goodsList: List<GoodsUI>) {
        viewModelScope.launch {
            goodsRepository.add(goodsList.map {
                it.toGoods()
            })
        }

    }

}