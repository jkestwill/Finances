package com.jk.goods.select_goods

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.jk.common_data.SearchParams
import com.jk.goods.GoodsRepository
import com.jk.goods_common_ui.GoodsUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SelectGoodsViewModel @Inject constructor(
    private val goodsRepository: GoodsRepository,
    private val goodsUIMapper: GoodsUIMapper
) : ViewModel(), SelectableItems<String> {
    val goodsListFlow = goodsRepository.getAllFromDatabase(SearchParams.getDefault())
        .map { pagingList ->
            pagingList.map { goods ->
                goodsUIMapper.toUI(goods)
            }
        }
        .cachedIn(viewModelScope)
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty(),
        )



    override val selectableItems: SnapshotStateList<String> = mutableStateListOf<String>()
}

interface SelectableItems<T> {
    val selectableItems: SnapshotStateList<T>
}

