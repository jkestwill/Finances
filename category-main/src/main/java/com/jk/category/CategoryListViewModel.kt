package com.jk.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jk.category_data.CategoryRepository
import com.jk.common_data.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// вывести пагинацию в compose
@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private var _categoryList = MutableStateFlow<State<Category>>(State.None())
    val categoryList: StateFlow<State<Category>> get() =_categoryList


    fun getById(categoryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.getById(categoryId)
        }
    }

    fun getAll(q:String,sortBy: String, isAsc: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.getList(q,sortBy, isAsc)
        }
    }
}