package com.jk.category.add_new_category

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jk.category.CategoryUIMapper
import com.jk.category_common_ui.CategoryUI
import com.jk.category_data.CategoryRepository
import com.jk.common_data.FinanceHelperException
import com.jk.common_data.LoggerTags
import com.jk.common_data.sha256
import com.jk.common_ui.State
import com.jk.common_ui.toState
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
import java.util.logging.Logger
import javax.inject.Inject
import javax.inject.Named
import kotlin.random.Random

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val colorList: List<Color>,
    private val categoryUIMapper: CategoryUIMapper,
    private val categoryValidator: CategoryUIValidator,
    @Named(LoggerTags.ADD_CATEGORY)
    private val logger: Logger?,
    private val dispatchers: com.jk.common_data.Dispatchers
) : ViewModel() {


    private var _addCategoryError =
        MutableSharedFlow<String>(1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    val addCategoryError: SharedFlow<String> get() = _addCategoryError

    private var _addCategoryResponse = MutableStateFlow<State<Long>>(State.None)
    val addCategoryResponse: StateFlow<State<Long>> get() = _addCategoryResponse


    fun addCategory(name: String, color: Int?) {
        viewModelScope.launch(dispatchers.io) {
            try {
                val category =  CategoryUI(
                    "$name $color".sha256(),
                    name,
                    color ?: getRandomColor()
                )
                categoryValidator.validate(category)
                categoryRepository.add(categoryUIMapper.toCategory(category))
                logger?.info("category created ${category}")
            }catch (e:FinanceHelperException){
                _addCategoryError.emit(e.message?:"Unrecognized error")
                logger?.severe(e.message)
            }
        }
    }

    private fun getRandomColor(): Int {
        return colorList[Random.nextInt(0,colorList.size-1)].toArgb()
    }

    private fun addCategory(category: CategoryUI) {
        viewModelScope.launch(Dispatchers.IO) {
            _addCategoryResponse.emitAll(
                categoryRepository.add(categoryUIMapper.toCategory(category)).map { apiRequest ->
                    apiRequest.toState()
                })
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
            _addCategoryError.emit("")
        }
        super.onCleared()
    }
}