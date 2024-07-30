package com.jk.category.select_category_dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.jk.category_common_ui.CategoryUI
import com.jk.category_common_ui.toUI
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
    val categoryRepository: CategoryRepository
) : ViewModel() {

    val allCategoryListFlow = categoryRepository.getList(SearchParams.getDefault())
        .map {
            it.map { category ->
                category.toUI()
            }
        }
        .cachedIn(viewModelScope)
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily, PagingData.empty()
        )

   private val _selectedCategoryFlow: MutableStateFlow<State<List<CategoryUI>>> = MutableStateFlow(State.None)
    val selectedCategoryFlow: StateFlow<State<List<CategoryUI>>> get() = _selectedCategoryFlow

    fun getCategoryListById(listId: List<String>) {
        viewModelScope.launch {
            _selectedCategoryFlow.emitAll(categoryRepository.getByCategoryListId(listId)
                .map { req -> req.map { list -> list.map { cat -> cat.toUI() } }.toState() }
            )
        }
    }
}