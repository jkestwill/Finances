package com.jk.category

import androidx.compose.ui.graphics.Color
import com.jk.category.add_new_category.AddCategoryViewModel
import com.jk.category.add_new_category.CategoryUIValidator
import com.jk.category_common_data.Category
import com.jk.category_common_ui.CategoryUI
import com.jk.category_data.s.CategoryRepository
import com.jk.common_data.ApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.internal.MockitoCore
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class AddCategoryViewModelUnitTest {
    @Mock
    private lateinit var categoryRepository: CategoryRepository
    @Mock
    private lateinit var colorList: List<Color>
    @Mock
    private lateinit var categoryUIMapper: CategoryUIMapper
    @Mock
    private lateinit var categoryValidator: CategoryUIValidator
    @Mock
    private val dispatchers: com.jk.common_data.Dispatchers = com.jk.common_data.Dispatchers(default=Dispatchers.Default)
    @InjectMocks
    private lateinit var addCategoryViewModel:AddCategoryViewModel

    @Before
    fun init(){
        MockitoAnnotations.openMocks(this)
    }
    @Test
    fun addCategory_withCorrectParams():Unit = runBlocking{
        val categoryUI = CategoryUI("id","nbame",0)
        val category =Category("id2","name2",1)
        Mockito.`when`(categoryRepository.add(category)).thenReturn(flowOf(ApiRequest.Success(1)))
        addCategoryViewModel.addCategory(categoryUI.name,categoryUI.color)
        addCategoryViewModel.addCategoryError.collect{
            assertEquals("",it)
        }
    }
}