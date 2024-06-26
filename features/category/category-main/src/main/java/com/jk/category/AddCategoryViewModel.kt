package com.jk.category

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jk.category_data.CategoryRepository
import com.jk.category_data.TransactionCategory
import com.jk.common_data.State
import com.jk.common_data.sha256
import com.jk.common_data.toState
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
import kotlin.random.Random

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

    private var _addCategoryResponse = MutableStateFlow<State<Long>>(State.None())
    val addCategoryResponse: StateFlow<State<Long>> get() = _addCategoryResponse

    val random = Random(213123)

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
                        TransactionCategory(
                            "$name $color $isExpenses".sha256(),
                            name,
                            color ?: colorList.random().value,
                            isExpenses
                        )
                    )
                }
            }
        }
    }

    private fun addCategory(category: TransactionCategory) {
        viewModelScope.launch(Dispatchers.IO) {
            _addCategoryResponse.emitAll(categoryRepository.add(category).map { apiRequest ->
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