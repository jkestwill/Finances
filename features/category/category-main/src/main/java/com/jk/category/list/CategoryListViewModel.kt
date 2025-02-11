package com.jk.category.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.jk.category_common_ui.CategoryUI
import com.jk.category.CategoryUIMapper
import com.jk.category_data.s.CategoryRepository
import com.jk.common_data.SearchParams
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
class CategoryListViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val categoryUIMapper: CategoryUIMapper
) : ViewModel() {

    companion object {
        private const val TAG = "CategoryViewModel"
    }

    private var _categoryFLow: MutableStateFlow<PagingData<CategoryUI>> =
        MutableStateFlow(PagingData.empty())
    val categoryFlow: StateFlow<PagingData<CategoryUI>> get() = _categoryFLow

    val categoryDeleteState = MutableStateFlow<State<Unit>>(State.None)

    var selectedCategoryIdList = MutableStateFlow(listOf<String>())

    val selectionState = MutableStateFlow(SelectionState.OFF)

    fun observeCategoryDeleteState() {
        viewModelScope.launch {
            categoryDeleteState.collect {
                if (it is State.Success) {
                    categoryDeleteState.value = State.None
                    selectedCategoryIdList.value = listOf()
                }
            }
        }
    }

    fun removeCategoriesById() {
        viewModelScope.launch {
            categoryDeleteState.emitAll(
                categoryRepository.removeByIdList(selectedCategoryIdList.value)
                    .map { it.toState() }
            )
        }
    }

    fun getAllCategories(search: String) {
        viewModelScope.launch {
            _categoryFLow.emitAll(
                categoryRepository.getList(
                    SearchParams(
                        q = search,
                        sortBy = "name",
                        isAsc = true
                    )
                )
                    .map { pagingData ->
                        pagingData.map { category ->
                            Log.e(TAG, "getAllCategories: ${category}")
                            categoryUIMapper.toCategoryUI(category)
                        }
                    }
                    .cachedIn(viewModelScope)
                    .stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.Lazily,
                        initialValue = PagingData.empty()
                    )
            )
        }
    }

    enum class SelectionState {
        ON, OFF,
    }
}