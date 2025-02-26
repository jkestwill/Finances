package com.jk.category.select_category_dialog

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.jk.category_common_ui.CategoryUI
import com.jk.category.CategoryUIMapper

import com.jk.category_data.CategoryRepository
import com.jk.common_data.SearchParams
import com.jk.common_data.map
import com.jk.common_ui.State
import com.jk.common_ui.toState
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectCategoryDialogViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val categoryUIMapper: CategoryUIMapper
) : ViewModel() {

    val allCategoryListFlow = categoryRepository.getList(SearchParams.getDefault())
        .map {
            it.map { category ->
                categoryUIMapper.toCategoryUI(category)
            }
        }
        .cachedIn(viewModelScope)
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily, PagingData.empty()
        )

    private val _preselectedCategoryFlow: MutableStateFlow<State<List<CategoryUI>>> =
        MutableStateFlow(State.None)
    val preselectedCategoryFlow: StateFlow<State<List<CategoryUI>>> get() = _preselectedCategoryFlow

    var selectedCategoryList = mutableStateListOf<CategoryUI>()
    fun getCategoryListById(listId: List<String>) {
        viewModelScope.launch {
            _preselectedCategoryFlow.emitAll(categoryRepository.getByCategoryListId(listId)
                .map { req ->
                    req.map { list ->
                        list.map { cat ->
                            categoryUIMapper.toCategoryUI(
                                cat
                            )
                        }
                    }.toState()
                }
            )
        }
    }
}