package com.jk.category

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jk.category_data.CategoryRepository
import com.jk.category_data.TransactionCategory
import com.jk.common_data.State
import com.jk.common_data.toState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    companion object {
        const val SEED = 11023
        private const val TAG = "CategoryViewModel"
    }

    private val searchRegex = Regex("([a-zA-Z]\\s){0,30}")

    private var _categoryFLow =
        MutableStateFlow<PagingData<TransactionCategory>>(PagingData.empty())
    val categoryFlow: StateFlow<PagingData<TransactionCategory>> get() = _categoryFLow

    val categoryDeleteState = MutableStateFlow<State<Unit>>(State.None())

    var selectedCategoryIdList = MutableStateFlow(listOf<String>())

    val selectionState = MutableStateFlow<SelectionState>(SelectionState.OFF)


    fun searchMatch(input: String): Boolean {
        return isMatch(regex = searchRegex, input = input)
    }

    private fun isMatch(regex: Regex, input: String): Boolean {
        return regex.matches(input)
    }


    fun observeCategoryDeleteState(){
        viewModelScope.launch {
            categoryDeleteState.collect {
                if(it is State.Success){
                    categoryDeleteState.value=State.None()
                    selectedCategoryIdList.value= listOf()
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
        Log.e(TAG, "getAllCategories: ")
        viewModelScope.launch {
            _categoryFLow.emitAll(
                categoryRepository.getList(search, "id", true).cachedIn(viewModelScope).stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.Lazily,
                    initialValue = PagingData.empty()
                )
            )
        }
    }

    fun getRandomColorList(size: Int): List<Color> {
        val random = Random(SEED)
        return List(size) {
            Color(
                red = random.nextInt(256),
                green = random.nextInt(256),
                blue = random.nextInt(256),
                alpha = 150
            )
        }
    }

    enum class SelectionState() {
        ON, OFF,
    }
}