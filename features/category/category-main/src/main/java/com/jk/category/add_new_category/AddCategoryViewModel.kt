package com.jk.category.add_new_category

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jk.category_common_ui.toCategory
import com.jk.category_common_ui.CategoryUI
import com.jk.category_data.CategoryRepository
import com.jk.common_data.sha256
import com.jk.common_ui.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.jk.common_ui.toState

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val colorList:List<Color>
) : ViewModel() {

    companion object {
        private const val TAG = "AddCategoryViewModel"
    }

    private var _addCategoryError =
        MutableSharedFlow<String>(1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    val addCategoryError: SharedFlow<String> get() = _addCategoryError

    private var _addCategoryResponse = MutableStateFlow<State<Long>>(State.None)
    val addCategoryResponse: StateFlow<State<Long>> get() = _addCategoryResponse


    fun addCategory(name: String, color: ULong?, isExpenses: Boolean) {
        Log.e(TAG, "addCategory:${name} ${color} ${isExpenses} ")
        viewModelScope.launch {
            when {
                name.isEmpty() -> {
                    Log.e(TAG, "addCategory:name is null ")
                    _addCategoryError.emit("Name must not be empty")
                }

                else -> {
                    Log.e(TAG, "addCategory:${color} ")
                    _addCategoryError.emit("")
                    addCategory(
                        CategoryUI(
                            "$name $color $isExpenses".sha256(),
                            name,
                            color ?: colorList.random().value,

                        )
                    )
                }
            }
        }
    }

    private fun addCategory(category: CategoryUI) {
        viewModelScope.launch(Dispatchers.IO) {
            _addCategoryResponse.emitAll(categoryRepository.add(category.toCategory()).map { apiRequest ->
                apiRequest.toState()
            })
        }
    }

    override fun onCleared() {
        Log.e("ViewModel", "onCleared: ")
        viewModelScope.launch {
            _addCategoryError.emit("")
        }
        super.onCleared()
    }
}