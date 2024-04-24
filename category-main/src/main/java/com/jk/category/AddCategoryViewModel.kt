package com.jk.category

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jk.category_data.CategoryRepository
import com.jk.category_data.TransactionCategory
import com.jk.common_data.State
import com.jk.common_data.sha256
import com.jk.common_data.toState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private var _newCategoryNameErr = MutableStateFlow("")
    val newCategoryNameErr: StateFlow<String> get() = _newCategoryNameErr

    private var _addCategoryResponse = MutableStateFlow<State<Long>>(State.None())
    val addCategoryResponse: StateFlow<State<Long>> get() = _addCategoryResponse

    val random = Random(213123)

    fun addCategory(name: String, color: ULong?, isExpenses: Boolean) {
        val regex = Regex("([a-zA-Zа-яА-я]){3,40}")
        when {
            !name.matches(regex) -> {
                _newCategoryNameErr.value =
                    "Name length must be in range 3..40 and symbols must match a-z, A-Z"
                println("Name length must be in range 3..40 and symbols must match a-z, A-Z")
            }
            color==null->{
                _newCategoryNameErr.value = "Choose color"
            }
            else -> {
                Log.e("pp", "addCategory:${color} ")
                _newCategoryNameErr.value = ""
                addCategory(
                    TransactionCategory(
                        "$name $color $isExpenses".sha256(),
                        name,
                        color,
                        isExpenses
                    )
                )
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
        _newCategoryNameErr.value = ""
        super.onCleared()
    }
}