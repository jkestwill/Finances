package com.jk.category

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jk.category_common_ui.CategoryUI
import com.jk.common_ui.Celadon
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.State
import com.jk.common_ui.composable.AutoSizeText
import com.jk.shared_res.R
import com.jk.transaction_common_ui.TransactionPreviewUI
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// скрин категории где показываются все транзакции в категории
@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel,
    navController: NavController,
    categoryId: String?,
    onCreateTransactionClick: () -> Unit
) {

    LaunchedEffect(key1 = categoryId) {
        categoryId?.let {
            viewModel.getById(it)
            viewModel.getAllTransactionsPreviewByCategoryId(it)
        }
    }
    val category = viewModel.categoryFlow.collectAsState()
    val color by remember(category.value) {
        derivedStateOf {
            when (category.value) {
                is State.Success -> (category.value as State.Success<CategoryUI>).data.color.toULong()
                else -> {
                    Celadon.value
                }
            }
        }
    }
    val transactionPreviewPagingList = viewModel.transactionPagingList.collectAsLazyPagingItems()

    val contentTranslationY = remember {
        mutableFloatStateOf(0f)
    }
    Scaffold(
        contentWindowInsets = WindowInsets(15.dp, 20.dp, 5.dp, 5.dp),
        containerColor = FinanceHelperTheme.colors.primaryBackground,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(5.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)

                        .clickable {
                            navController.popBackStack()
                        },
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "ic_back"
                )

                CategoryHeader(
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    categoryState = category.value,
                    color = Color(color),
                    onSearchTranslationY = {
                        contentTranslationY.floatValue = it
                    }
                ) {
                    // вынести search или добавить слушатель на анимацию
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = FinanceHelperTheme.shape.shapeRoundMedium
                    ),
                shape = FinanceHelperTheme.shape.shapeRoundMedium,
                onClick = onCreateTransactionClick,
                containerColor = FinanceHelperTheme.colors.defaultButtonColor
            ) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "edit_ic")
            }
        }) {
        Column(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding(),
                    start = it.calculateStartPadding(LayoutDirection.Ltr),
                    end = it.calculateEndPadding(LayoutDirection.Rtl)
                )
                .graphicsLayer {
                    translationY = contentTranslationY.floatValue
                }
                .fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TransactionList(
                transactionList = CategoryViewModel.test, Color(0xffffffff)
            )
        }
    }
}

@Composable
fun CategoryHeader(
    modifier: Modifier,
    categoryState: State<CategoryUI>,
    color: Color,
    onSearchTranslationY: (Float) -> Unit,
    onSearch: (String) -> Unit
) {
    val errorMessage = stringResource(id = R.string.error)
    val categoryName = remember(categoryState) {
        derivedStateOf {
            when (categoryState) {
                is State.Loading -> {
                    categoryState.data?.name ?: ""
                }

                is State.Success -> {
                    categoryState.data.name
                }

                is State.Error -> {
                    categoryState.data?.name ?: categoryState.message
                }

                is State.None -> {
                    errorMessage
                }

            }
        }
    }
    CategoryHeader(
        modifier = modifier,
        categoryName = categoryName.value,
        description = stringResource(id = R.string.all_transactions),
        color = color,
        onSearchTranslationY = onSearchTranslationY,
        onSearch = onSearch
    )
}

@Composable
fun TransactionList(
    modifier: Modifier = Modifier,
    transactionPagingList: LazyPagingItems<TransactionPreviewUI>,
    color: Color
) {
    when (transactionPagingList.loadState.refresh) {
        is LoadState.Loading -> {
            Text(text = "LOADING...")
        }

        is LoadState.NotLoading -> {
            TransactionList(
                transactionList = transactionPagingList.itemSnapshotList.items,
                color = color,
            )
        }

        is LoadState.Error -> {
            Text(modifier = modifier, text = "No transactions here")
        }
    }
}

@Composable
fun TransactionList(transactionList: List<TransactionPreviewUI>, color: Color) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(start = 5.dp, end = 5.dp, top = 20.dp)
    ) {
        items(transactionList.size) {


            if ((transactionList[it].date.dayOfYear != transactionList[(it - 1).coerceAtLeast(0)].date.dayOfYear || it - 1 < 0)) {
                val pattern = remember(transactionList) {
                    derivedStateOf {
                        if (LocalDateTime.now().year != transactionList[it].date.year) {
                            println("zxczxc")
                            "dd MMM YYYY"
                        } else {
                            println("qwqwqwqwqw")
                            "dd MMM"
                        }
                    }
                }
                Text(
                    text = transactionList[it].date.format(DateTimeFormatter.ofPattern(pattern.value)),
                    style = FinanceHelperTheme.typography.h2
                )
            }

            TransactionPreviewItem(item = transactionList[it], color = color)
        }
    }
}

@Composable
fun TransactionPreviewItem(item: TransactionPreviewUI, color: Color) {
    Row(
        Modifier
            .background(color, FinanceHelperTheme.shape.shapeRoundMedium)
            .border(2.dp, Color.Black, RoundedCornerShape(20))
            .height(60.dp)
            .padding(start = 10.dp)
    ) {

        Column(
            modifier = Modifier
                .weight(0.5f)

                .align(Alignment.CenterVertically)
        ) {
            AutoSizeText(
                modifier = Modifier.weight(2f),
                text = item.operation.name,
                minTextSize = (FinanceHelperTheme.typography.h3.fontSize.value - 5).sp,
                maxTextSize = FinanceHelperTheme.typography.h2.fontSize,
                maxLines = 2,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.weight(1f),
                text = item.date.format(DateTimeFormatter.ofPattern("hh:mm")),
                style = FinanceHelperTheme.typography.h4,
                textAlign = TextAlign.Start
            )
        }
        Box(
            modifier = Modifier
                .weight(0.3f)
                .align(Alignment.CenterVertically)
                .height(40.dp)
                .background(
                    color = color, shape = FinanceHelperTheme.shape.shapeRoundMedium
                )

        ) {
            AutoSizeText(
                modifier = Modifier.align(Alignment.Center),
                text = "${item.operation.money.amount} ${item.operation.money.currency.name}",
                minTextSize = FinanceHelperTheme.typography.h3.fontSize,
                maxTextSize = FinanceHelperTheme.typography.h2.fontSize,
                style = FinanceHelperTheme.typography.h3,
                alignment = Alignment.Center,
                maxLines = 1
            )
        }
    }
}

@Composable
@Preview
fun CategoryScreenPreview() {
    FinanceHelperTheme {
        CategoryScreen(
            navController = rememberNavController(),
            categoryId = "zxc",
            viewModel = viewModel(),
           onCreateTransactionClick =  {

            }
        )
    }
}