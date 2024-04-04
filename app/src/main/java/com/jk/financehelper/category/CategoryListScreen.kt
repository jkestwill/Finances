package com.jk.financehelper.category

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
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
    viewModel.getAllCategories(searchText.value)
    Scaffold(topBar = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(text = "Categories", style = FinanceHelperTheme.typography.label)
            Search(text =searchText.value)
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
                ) {
                    navController.navigate("${Routes.CATEGORY}?categoryId=${it.id}&colorInt=${it.color}")
                }
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


@Composable
fun ColumnScope.CategoryGrid(
    categoryPagingList: LazyPagingItems<TransactionCategory>,
    onItemClick: (TransactionCategory) -> Unit
) {

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
                    "CategoryGrid: ${(categoryPagingList.loadState.refresh as LoadState.Error).error.stackTrace[0].fileName}",
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
                                CategoryItem(
                                    category = category,
                                    color = Color(category.color),
                                    onClick = onItemClick
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

@Composable
fun CategoryItem(
    category: TransactionCategory,
    color: Color,
    onClick: (TransactionCategory) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(15))
            .drawBehind {
                drawRect(color)
            }
            .height(100.dp)
            .clickable { onClick(category) }

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
fun Search(text: String) {
    var searchText = remember() { mutableStateOf(text) }
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
