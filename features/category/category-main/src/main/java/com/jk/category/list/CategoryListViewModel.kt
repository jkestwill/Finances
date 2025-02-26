package com.jk.category.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.jk.category.CategoryUIMapper
import com.jk.category_common_ui.CategoryUI
import com.jk.category_data.CategoryRepository
import com.jk.common_data.LoggerTags
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
import java.util.logging.Level
import java.util.logging.Logger
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val categoryUIMapper: CategoryUIMapper,
    @Named(LoggerTags.CATEGORY_LIST)
    private val logger:Logger?
) : ViewModel() {

    companion object {
        private const val TAG = "CategoryViewModel"
    }

    private var _categoryListFLow: MutableStateFlow<PagingData<CategoryUI>> =
        MutableStateFlow(PagingData.empty())

    val categoryListFlow: StateFlow<PagingData<CategoryUI>> get() = _categoryListFLow

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

    fun removeSelectedCategories() {
        viewModelScope.launch {
            categoryDeleteState.emitAll(
                categoryRepository.removeByIdList(selectedCategoryIdList.value)
                    .map { it.toState() }
            )
        }
    }

    fun getAllCategories(search: String) {
        viewModelScope.launch {
            _categoryListFLow.emitAll(
                categoryRepository.getList(
                    SearchParams(
                        q = search,
                        sortBy = "name",
                        isAsc = true
                    )
                )
                    .map { pagingData ->
                        pagingData.map { category ->
                            logger?.log(Level.FINE,"getAllCategories#map: ${category}")
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