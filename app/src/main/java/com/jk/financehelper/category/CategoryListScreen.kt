@file:OptIn(ExperimentalFoundationApi::class)

package com.jk.financehelper.category

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
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
    val isSelectionMode = remember {
        mutableStateOf(false)
    }
    Scaffold(topBar = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(text = "Categories", style = FinanceHelperTheme.typography.label)
            Search(text = searchText.value, viewModel)
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
                    onItemClick = {
                        navController.navigate("${Routes.CATEGORY}?categoryId=${it.id}&colorInt=${it.color}")
                    },
                    isSelectionMode = isSelectionMode,
                    onSelectionMode = {
                        isSelectionMode.value = it
                    }
                )
            }
            if (isSelectionMode.value) {
                SelectItemsMenu {

                }
            } else {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(65.dp)
                        .padding(10.dp)
                        .background(
                           FinanceHelperTheme.colors.defaultButtonColor,
                           FinanceHelperTheme.shape.shape10
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


}

/**
 * Menu for selected items*/
@Composable
fun BoxScope.SelectItemsMenu(onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .align(Alignment.BottomCenter)

    ) {

        IconButton(modifier = Modifier.weight(1f).background(FinanceHelperTheme.colors.buttonDeleteColor, shape = FinanceHelperTheme.shape.shape20), onClick = onDelete) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = "ic_delete")
        }
    }
}

@Composable
fun ColumnScope.CategoryGrid(
    categoryPagingList: LazyPagingItems<TransactionCategory>,
    onSelectionMode: (Boolean) -> Unit,
    isSelectionMode: State<Boolean>,
    onItemClick: (TransactionCategory) -> Unit,
) {
    val selectedItemList = remember {
        mutableStateOf(listOf<String>())
    }
    Log.e("zxc", "CategoryGrid:${categoryPagingList.itemCount} ")
    Log.e("zxc", "CategoryGrid:${categoryPagingList.loadState.refresh} ")
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
                                    modifier = Modifier
                                        .combinedClickable(
                                            onClick = {
                                                if (isSelectionMode.value) {
                                                    isSelected = !isSelected
                                                    if (isSelected) {
                                                        selectedItemList.value += category.id
                                                    } else {
                                                        selectedItemList.value -= category.id
                                                    }
                                                } else {
                                                    onItemClick(category)
                                                }

                                            }, onLongClick = {
                                                onSelectionMode(!isSelectionMode.value)
                                            }),
                                    category = category,
                                    color = Color(category.color),
                                    isSelectionMode = isSelectionMode,
                                    isSelected = isSelected
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

// сделать выбор элементов по долгому нажатимю и вынести это в общее тк это нужно будет для других списков
@Composable
fun CategoryItem(
    modifier: Modifier,
    category: TransactionCategory,
    color: Color,
    isSelectionMode: State<Boolean>,
    isSelected: Boolean
) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(15))
            .height(100.dp)
            .drawBehind {
                drawRect(color = color)
                if (isSelectionMode.value) {
                    val offset = Offset(
                        x = size.width / 4f,
                        y = size.height - size.height / 5
                    )
                    drawRect(
                        color = Color.Black,
                        topLeft = offset,
                        size = Size(width = size.width / 2, height = 10f)
                    )
                    if (isSelected) {
                        drawRect(
                            color = Color.Cyan,
                            topLeft = offset,
                            size = Size(width = size.width / 2, height = 10f)
                        )
                    }

                }
            }
    ) {

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
