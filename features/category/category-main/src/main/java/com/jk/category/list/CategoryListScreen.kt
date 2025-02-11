package com.jk.category.list

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jk.category.R
import com.jk.category_common_ui.CategoryUI
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.composable.Limit
import com.jk.common_ui.composable.Search
import com.jk.common_ui.composable.TextError
import com.jk.common_ui.composable.TextLimit
import com.jk.common_ui.composable.TextLimitConfig

private const val TAG = "CategoryListScreen"

@SuppressLint("RememberReturnType", "FlowOperatorInvokedInComposition", "RestrictedApi")
@Composable
fun CategoryListScreen(
    viewModel: CategoryListViewModel,
    onCategoryItemClick: (CategoryUI) -> Unit,
    onNewCategoryClick: () -> Unit
) {
    val categoryList = viewModel.categoryFlow.collectAsLazyPagingItems()
    val searchText = remember {
        mutableStateOf("")
    }
    val s by viewModel.selectedCategoryIdList.collectAsState()
    val isSelectionMode by viewModel.selectionState.collectAsState()
    val selectAll = remember {
        mutableStateOf(false)
    }
    var searchPositionY by remember {
        mutableFloatStateOf(0f)
    }

    BackHandler(isSelectionMode == CategoryListViewModel.SelectionState.ON) {

        viewModel.selectionState.value = CategoryListViewModel.SelectionState.OFF
    }

    LaunchedEffect(key1 = s) {
        viewModel.selectedCategoryIdList.collect {
            Log.e(TAG, "CategoryListScreen: $it")
            selectAll.value = it.size == categoryList.itemSnapshotList.items.size
        }
    }


    Scaffold(topBar = {
        Box(modifier = Modifier.fillMaxWidth()) {
            Search(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                text = searchText.value,
                onValueChange = {
                    searchText.value = it
                    viewModel.getAllCategories(it)
                },
                textLimit = TextLimitConfig(
                    TextLimit(minLength = Limit(0, true), maxLength = Limit(32, false)),
                    error = TextError(maxTextLengthError = "Err")
                )
            )
            // header
            Row(
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp)
                    .align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Text(
                    modifier = Modifier.weight(1f),
                    text = "Categories",
                    style = FinanceHelperTheme.typography.label,
                    color = FinanceHelperTheme.colors.primaryText
                )
                Spacer(modifier = Modifier.weight(1f))

            }
        }
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .background(FinanceHelperTheme.colors.primaryBackground)
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            Column(
                modifier = Modifier
                    .padding(FinanceHelperTheme.shape.headerPadding)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                CategoryGrid(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    categoryPagingList = categoryList,
                    onItemClick = { category ->
                        onCategoryItemClick(category)
                    },
                    viewModel = viewModel
                )
            }

            SelectItemsMenu(
                isSelectionMode == CategoryListViewModel.SelectionState.ON,
                viewModel = viewModel
            ) {
                Log.e("TAG", "CategoryListScreen: ${viewModel.selectedCategoryIdList.value}")
                viewModel.removeCategoriesById()
                selectAll.value = false
            }
            // add category button
            if (isSelectionMode != CategoryListViewModel.SelectionState.ON) {
                IconButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(65.dp)
                        .padding(10.dp)
                        .background(
                            color = FinanceHelperTheme.colors.defaultButtonColor,
                            shape = FinanceHelperTheme.shape.shapeRoundMedium
                        ),
                    onClick = {
                        onNewCategoryClick()
                    }
                ) {
                    Icon(
                        modifier = Modifier.align(Alignment.Center),
                        imageVector = Icons.Filled.Add,
                        contentDescription = "add"
                    )

                }

                SelectAll(isSelected = selectAll.value) {
                    selectAll.value = !selectAll.value
                    if (selectAll.value) {
                        viewModel.selectedCategoryIdList.value = listOf()
                        viewModel.selectedCategoryIdList.value += categoryList.itemSnapshotList.map {
                            it?.id ?: ""
                        }
                    } else viewModel.selectedCategoryIdList.value = listOf()
                }

            }
        }
    }
}

/**
 * Contains Select text and radio button
 * */
@Composable
fun SelectAll(modifier: Modifier = Modifier, isSelected: Boolean, onSelectAll: () -> Unit) {
    Row(modifier) {
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = stringResource(id = R.string.select_all),
            style = FinanceHelperTheme.typography.h2,

            )
        RadioButton(
            modifier = Modifier.align(Alignment.CenterVertically),
            selected = isSelected, onClick = {
                onSelectAll()
            })
    }
}

/**
 * Menu for selected items*/

@Composable
fun BoxScope.SelectItemsMenu(
    visible: Boolean,
    viewModel: CategoryListViewModel,
    onDelete: () -> Unit
) {
    val deleteState = viewModel.categoryDeleteState.collectAsState()
    viewModel.observeCategoryDeleteState()
    when (deleteState.value) {
        is com.jk.common_ui.State.Loading -> {
            Box(
                modifier = Modifier
                    .background(
                        FinanceHelperTheme.colors.buttonDeleteColor,
                        shape = FinanceHelperTheme.shape.shapeRoundMedium
                    )
                    .align(Alignment.BottomCenter)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.BottomCenter))
            }
        }

        is com.jk.common_ui.State.Success -> {

        }

        is com.jk.common_ui.State.Error -> {
            // нарисовать зеленую гниду с табличкой ошибки ххиихихиххихихихи
            ErrorCategory(
                modifier = Modifier.align(Alignment.TopStart),
                errorText = stringResource(R.string.error)
            )

        }

        else -> {
            if (visible)
                IconButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(65.dp)
                        .padding(FinanceHelperTheme.shape.buttonPadding)
                        .background(
                            FinanceHelperTheme.colors.buttonDeleteColor,
                            shape = FinanceHelperTheme.shape.shapeRoundMedium
                        ), onClick = onDelete

                ) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "ic_delete")
                }
        }

    }

    LaunchedEffect(key1 = deleteState.value) {
        viewModel.getAllCategories(search = "")
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryGrid(
    modifier: Modifier = Modifier,
    categoryPagingList: LazyPagingItems<CategoryUI>,
    onItemClick: (CategoryUI) -> Unit,
    viewModel: CategoryListViewModel
) {
    Crossfade(
        targetState = categoryPagingList.loadState.refresh, animationSpec = tween(),
    ) { state ->
        when (state) {
            is LoadState.Loading -> {
                CircularProgressIndicator(
                    modifier = modifier
                        .width(100.dp)
                        .height(100.dp)
                )
            }

            is LoadState.Error -> {
                ErrorCategory(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    stringResource(id = R.string.empty_list)
                )
                Log.e(
                    "qq",
                    "CategoryGrid: ${state.error.message}",
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
                                var isSelected =
                                    viewModel.selectedCategoryIdList.collectAsState().value.contains(
                                        category.id
                                    )
                                CategoryItem(
                                    modifier = Modifier

                                        .combinedClickable(
                                            onClick = {
                                                if (viewModel.selectionState.value == CategoryListViewModel.SelectionState.ON) {
                                                    isSelected = !isSelected

                                                    if (isSelected) {
                                                        viewModel.selectedCategoryIdList.value += category.id
                                                    } else {
                                                        viewModel.selectedCategoryIdList.value -= category.id
                                                    }
                                                } else {
                                                    viewModel.selectedCategoryIdList.value =
                                                        listOf()
                                                    onItemClick(category)
                                                }

                                            }, onLongClick = {

                                                viewModel.selectionState.value =
                                                    if (viewModel.selectionState.value ==
                                                        CategoryListViewModel.SelectionState.OFF
                                                    ) {
                                                        viewModel.selectedCategoryIdList.value += category.id
                                                        CategoryListViewModel.SelectionState.ON
                                                    } else {
                                                        viewModel.selectedCategoryIdList.value =
                                                            listOf()
                                                        CategoryListViewModel.SelectionState.OFF
                                                    }
                                            }),
                                    category = category,
                                    color = Color(category.color ?: 0xfffffff),
                                    isSelectionMode = viewModel.selectionState.value,
                                    isSelected = isSelected
                                )
                            }
                        }
                    }
                else {
                    ErrorCategory(errorText = stringResource(id = R.string.empty_list))
                }
            }
        }
    }


}


@Composable
fun ErrorCategory(modifier: Modifier = Modifier, errorText: String) {
    Box(
        modifier = modifier

    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = errorText,
            style = FinanceHelperTheme.typography.body,
            color = Color.LightGray
        )
    }
}

// сделать выбор элементов по долгому нажатимю и вынести это в общее тк это нужно будет для других списков
@Composable
fun CategoryItem(
    modifier: Modifier,
    category: CategoryUI,
    color: Color,
    isSelectionMode: CategoryListViewModel.SelectionState,
    isSelected: Boolean
) {
    Box(
        modifier = modifier
            .height(100.dp)
            .background(color, FinanceHelperTheme.shape.shapeRoundedLow)
            .drawBehind {
                if (isSelectionMode == CategoryListViewModel.SelectionState.ON) {
                    val strokeWidth = 4f
                    if (isSelected) {
                        drawRect(
                            color = Color.Black,
                            topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                            size = this.size.copy(
                                this.size.width - strokeWidth,
                                this.size.height - strokeWidth
                            ),
                            style = Stroke(
                                strokeWidth,
                                pathEffect = PathEffect.cornerPathEffect(10.dp.toPx())
                            )
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



