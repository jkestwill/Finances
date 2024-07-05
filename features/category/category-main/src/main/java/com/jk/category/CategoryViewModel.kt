package com.jk.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.jk.category_data.CategoryRepository
import com.jk.common.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

// вывести пагинацию в compose
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private var _categoryList = MutableStateFlow<State<Category>>(State.None)
    val categoryList: StateFlow<State<Category>> get() = _categoryList

    companion object {
        const val NAME_LENGTH_VISIBILITY = 20
    }

    fun getById(categoryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.getById(categoryId)
        }
    }


    fun getAll(q: String, sortBy: String, isAsc: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.getList(q, sortBy, isAsc)
                .map {
                it
                    .map { s ->
                    s.copy(name = if (s.name.length == NAME_LENGTH_VISIBILITY) s.name.substring(0..NAME_LENGTH_VISIBILITY) else s.name)
                }
            }
        }
    }
}