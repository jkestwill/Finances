package com.jk.goods

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.jk.common_data.SearchParams
import com.jk.common_goods_data.Goods
import com.jk.goods_common_ui.GoodsUI
import com.jk.goods_common_ui.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SelectGoodsViewModel @Inject constructor(
    private val goodsRepository: GoodsRepository,
) : ViewModel(), Selectable<GoodsUI> {
    val goodsListFlow = goodsRepository.getAllFromDatabase(SearchParams.getDefault())
        .map { pagingList ->
            pagingList.map { goods ->
                goods.toUI()
            }
        }
        .cachedIn(viewModelScope)
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty(),
        )

    override val selectableItems: SnapshotStateList<GoodsUI> = mutableStateListOf<GoodsUI>()
}

interface Selectable<T> {
    val selectableItems: SnapshotStateList<T>
}