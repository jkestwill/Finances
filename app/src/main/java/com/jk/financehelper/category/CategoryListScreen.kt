package com.jk.financehelper.category

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jk.category.Category
import com.jk.category.CategoryViewModel
import com.jk.category_data.TransactionCategory
import com.jk.financehelper.R
import com.jk.financehelper.navigation.Routes
import com.jk.financehelper.ui.theme.Celadon
import com.jk.financehelper.ui.theme.FinanceHelperTheme

@SuppressLint("RememberReturnType")
@Composable
fun CategoryListScreen(viewModel: CategoryViewModel, navController: NavController) {
    val categoryList = viewModel.categoryFlow.collectAsLazyPagingItems()
    val searchText = remember {
        mutableStateOf("")
    }
    var isSelectionEnable by remember {
        mutableStateOf(false)
    }
    var selectedIdList by remember {
        mutableStateOf(listOf<String>())
    }
    // viewModel.getAllCategories(searchText.value)
    Scaffold(topBar = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(text = "Categories", style = FinanceHelperTheme.typography.label)
            Search(text = searchText.value, viewModel)
            if (isSelectionEnable) {
                SelectionBar(
                    selectedCount = selectedIdList.size,
                    onSelectAll = {
                        if (it)
                        selectedIdList+=categoryList.itemSnapshotList.map { it?.id?:"" }
                        else selectedIdList= listOf()
                    },
                    onClose = { isSelectionEnable=false }) {

                }
            }
        }
    }) {
        Box(
            modifier = Modifier
                .background(FinanceHelperTheme.colors.primaryBackground)
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            Column(
                modifier = Modifier
                    .padding(FinanceHelperTheme.shape.padding)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                CategoryGrid(
                    categoryPagingList = categoryList,
                    onSelectEnable = { enabled ->
                        Log.e("TAG", "CategoryListScreen:$enabled ")
                        isSelectionEnable = enabled
                    },
                    onItemClick = {
                        navController.navigate("${Routes.CATEGORY}?categoryId=${it.id}&colorInt=${it.color}")
                    },
                    onItemSelected = {
                        selectedIdList=it
                    }
                )

            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(10.dp)
                    .background(
                        Celadon,
                        RoundedCornerShape(30)
                    )
                    .clickable {

                        navController.navigate(Routes.NEW_CATEGORY)

                    },
            ) {

                Icon(
                    modifier = Modifier.align(Alignment.Center),
                    imageVector = Icons.Filled.Add,
                    contentDescription = "add"
                )

            }

        }
    }


}

/**
 * Selection bar for handling selected item
 * */
@Composable
fun SelectionBar(
    selectedCount: Int,
    onSelectAll: (Boolean) -> Unit,
    onClose: () -> Unit,
    onDelete: () -> Unit
) {
    val selected = remember {
        mutableStateOf(false)
    }
    Row {
        RadioButton(selected = selected.value, onClick = {
            selected.value = !selected.value
            onSelectAll(selected.value)
        })
        Text(text = "$selectedCount")
        Icon(
            modifier = Modifier.clickable { onClose() },
            imageVector = Icons.Filled.Close,
            contentDescription = "close"
        )
    }
}

@Composable
fun ColumnScope.CategoryGrid(
    categoryPagingList: LazyPagingItems<TransactionCategory>,
    onItemClick: (TransactionCategory) -> Unit,
    onSelectEnable:(Boolean)->Unit,
    onItemSelected:(List<String>)->Unit,
) {
    var selectedIdList by remember {
        mutableStateOf(listOf<String>())
    }
    Crossfade(
        targetState = categoryPagingList.loadState.refresh, animationSpec = tween(),
    ) { state ->
        when (state) {
            is LoadState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            is LoadState.Error -> {
                ErrorCategory()
                Log.e(
                    "qq",
                    "CategoryGrid: ${(state as LoadState.Error).error.stackTrace[0].fileName}",
                )
            }

            else -> {

                if (categoryPagingList.itemCount > 0)
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                    ) {
                        items(categoryPagingList.itemCount) {
                            categoryPagingList[it]?.let { category ->
                                var isSelected by remember {
                                    mutableStateOf(false)
                                }
                                CategoryItem(
                                    category = category,
                                    color = Color(category.color),
                                    onClick = { onItemClick(category) },
                                    isSelected = isSelected,
                                    onSelectEnable = onSelectEnable,
                                    onSelectItem = {selected->
                                        if(selected){
                                            selectedIdList+=category.id

                                        }
                                        else{
                                            selectedIdList-=category.id
                                        }
                                        onItemSelected(selectedIdList)
                                        isSelected = selected
                                    }
                                )
                            }
                        }
                    }
                else {
                    ErrorCategory()
                }
            }
        }
    }


}

@Composable
fun ColumnScope.ErrorCategory() {
    val text = stringResource(id = R.string.list_empty)
    Box(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            style = FinanceHelperTheme.typography.body,
            color = Color.LightGray
        )
    }
}

@Composable
fun LoadingCategory(data: LazyPagingItems<TransactionCategory>) {
    CircularProgressIndicator()
}

// сделать выбор элементов по долгому нажатимю и вынести это в общее тк это нужно будет для других списков
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryItem(
    category: TransactionCategory,
    color: Color,
    onClick: () -> Unit,
    selectedColor: Color? = null,
    isSelected: Boolean,
    onSelectEnable: (Boolean) -> Unit,
    onSelectItem: (Boolean) -> Unit
) {
    var selectedMode by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(15))
            .combinedClickable(
                onLongClick = {
                    Log.e("TAG", "CategoryItem: ")
                    selectedMode = !selectedMode
                    onSelectEnable(selectedMode)
                },
                onClick = onClick
            )
            .drawBehind {
                drawRect(color)
            }

            .height(100.dp)
        // .clickable { onClick() }


    ) {
        if (selectedMode)
            Checkbox(checked = isSelected, onCheckedChange = {
                onSelectItem(it)
            })

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = category.name,
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}

@Composable
fun Search(text: String, viewModel: CategoryViewModel) {
    var searchText = remember() { mutableStateOf(text) }
    viewModel.getAllCategories(searchText.value)
    TextField(
        modifier = Modifier
            .width(150.dp)
            .height(50.dp),
        value = searchText.value, onValueChange = {
            searchText.value = it
        },
        textStyle = FinanceHelperTheme.typography.body,
        placeholder = {
            Text(text = "Search")
        })
}
