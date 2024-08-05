package com.jk.transaction

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.jk.category_common_ui.CategoryUI
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.State
import com.jk.common_ui.clickAnimation
import com.jk.common_ui.composable.TextWithDropDownMenu
import com.jk.goods_common_ui.GoodsUI
import com.jk.money_common_ui.CurrencyUI
import com.jk.shared_res.R
import java.util.BitSet

// при поворачивании экрана в селект категори категории не добавляются

@Composable
fun TransactionScreen(
    viewModel: AddNewTransactionViewModel,
    categoryListId: List<String>?,
    onBackClick: (() -> Unit)?,
    onAddCategory: (List<String>?) -> Unit
) {
    val goodsList = viewModel.allGoodsFlow.collectAsLazyPagingItems()
    LaunchedEffect(key1 = categoryListId) {

        if (categoryListId != null && (viewModel.prevCategoryIdList == null || viewModel.prevCategoryIdList != categoryListId)) {
            viewModel.prevCategoryIdList = categoryListId
            viewModel.getCategoryListById(categoryListId)
        }

        Log.e("QQ", "TransactionScreen:${categoryListId} ")
    }
    val currencyList = viewModel.currencyListState.collectAsState()

    Scaffold(containerColor = FinanceHelperTheme.colors.primaryBackground, topBar = {
        Row(modifier = Modifier.padding(FinanceHelperTheme.shape.padding)) {
            if (onBackClick != null)
                IconButton(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = onBackClick
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "ic_back"
                    )
                }
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(2f, fill = false),
                text = "Create Transaction",
                style = FinanceHelperTheme.typography.h1,
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Visible,
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }) { scaffoldPadding ->
        Column(
            modifier = Modifier.padding(
                top = scaffoldPadding.calculateTopPadding(),
                bottom = scaffoldPadding.calculateBottomPadding(),
                start = scaffoldPadding.calculateStartPadding(LayoutDirection.Ltr),
                end = scaffoldPadding.calculateEndPadding(LayoutDirection.Rtl)
            ), verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TransactionInfoSection(
                modifier = Modifier.padding(
                    start = 10.dp, end = 10.dp
                ), currencyList.value
            )

            CategorySection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                viewModel = viewModel
            ) {
                onAddCategory(it)
            }

            //разделить на стейты загрузка хуюска и обработать
            //если выбраны категории прихода то товары недост
            GoodsSection(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                goodsList = goodsList.itemSnapshotList.items,
                currencyListState = currencyList.value
            )
        }
    }
}

/**
 * Transaction section contains base info about transaction f.e name amount currency etc.
 * */
@Composable
fun TransactionInfoSection(modifier: Modifier = Modifier, currencyList: State<List<CurrencyUI>>) {
    val transactionName = rememberSaveable() {
        mutableStateOf("")
    }
    val amount = rememberSaveable() {
        mutableStateOf("")
    }
    val currency = rememberSaveable() {
        mutableStateOf("")
    }
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(text = "General", style = FinanceHelperTheme.typography.h2)
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            TransactionNameText(
                modifier = Modifier.weight(2f),
                transactionName.value,
                onValueChange = { transactionName.value = it },
                onError = {}
            )

            CurrencyAmountText(
                modifier = Modifier.weight(1f),
                value = amount.value,
                onValueChange = {
                    amount.value = if (it.count { c -> c == '.' } <= 1) {
                        it
                    } else amount.value
                }) {
            }

            CurrencyDropDownMenu(
                modifier = Modifier
                    .weight(1f),
                currencyListState = currencyList,
                placeholderText = "Currency",
                color = FinanceHelperTheme.colors.defaultButtonColor
            ) {
                currency.value = it
                Log.e("TAG", "TransactionInfoSection:${it} ")
            }
        }
    }
}

@Composable
fun CategorySection(
    modifier: Modifier,
    viewModel: AddNewTransactionViewModel,
    onAddCategory: (List<String>?) -> Unit
) {
    val categoryIdList = viewModel.categoryList.collectAsState()
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(modifier = Modifier, text = "Categories", style = FinanceHelperTheme.typography.h2)
            IconButton(
                modifier = Modifier
                    .background(
                        color = FinanceHelperTheme.colors.defaultButtonColor,
                        shape = FinanceHelperTheme.shape.shape20
                    )
                    .height(24.dp)
                    .width(24.dp),
                onClick = {
                    onAddCategory(viewModel.editableCategoriesStateList.map { it.id })
                }
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Filled.Add,
                    contentDescription = "ic_add"
                )
            }
        }
        CategoryGrid(
            modifier = Modifier.fillMaxWidth(),
            items = viewModel.editableCategoriesStateList,
            onDelete = {
                viewModel.editableCategoriesStateList.remove(it)
            })
    }
}

@Composable
fun CategoryGrid(
    modifier: Modifier,
    items: List<CategoryUI>,
    onDelete: (CategoryUI) -> Unit
) {
    HorizontalCategoryGrid(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp), items = items.toList()
    ) {
        onDelete(it)
    }
}

@Composable
fun GoodsSection(
    modifier: Modifier = Modifier,
    goodsList: List<GoodsUI>,
    currencyListState: State<List<CurrencyUI>>
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row {
            Text(
                text = stringResource(id = R.string.goods),
                style = FinanceHelperTheme.typography.h2
            )
            Box(modifier = Modifier.clickAnimation {

            }) {
                Image(imageVector = Icons.Filled.Add, contentDescription = "ic_add")
            }
        }
        GoodsList(goodsList = goodsList, currencyListState = currencyListState, onListChanged = {

        })
    }
}

@Composable
fun GoodsList(
    modifier: Modifier = Modifier,
    goodsList: List<GoodsUI>,
    currencyListState: State<List<CurrencyUI>>,
    onListChanged: (List<GoodsUI>) -> Unit
) {
    val addCount = remember {
        mutableStateOf(0)
    }
    val editableItemList = remember {
        mutableStateListOf<GoodsUI>()
    }
    Column {
        LazyColumn(modifier = modifier) {
            items(goodsList.size) {
                ImmutableGoodsListItem(
                    currencyListState = currencyListState,
                    goodsUI = goodsList[it],
                    onCountChanged = {

                    })
            }
            items(addCount.value) {
                EditableListItem(
                    modifier = Modifier.fillMaxWidth(),
                    currencyListState = currencyListState,
                    onAdd = {
                        editableItemList.add(it)
                    })
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickAnimation {
                addCount.value += addCount.value
            }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "ic_add")
        }
    }
}

// onAdd - when user left the focus on one of this elements
@Composable
fun EditableListItem(
    modifier: Modifier,
    currencyListState: State<List<CurrencyUI>>,
    onAdd: (GoodsUI) -> Unit
) {
    val goodsName = rememberSaveable() {
        mutableStateOf("")
    }
    val goodsAmount = rememberSaveable() {
        mutableStateOf("")
    }
    val currency = rememberSaveable() {
        mutableStateOf("")
    }
    val goodsCount = rememberSaveable() {
        mutableIntStateOf(0)
    }

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier
            .weight(1f)
            .clickAnimation {
                goodsCount.intValue += goodsCount.intValue
            }) {
            Image(imageVector = Icons.Filled.Add, contentDescription = "ic_add")
        }
        if (goodsCount.intValue > 0)
            Box(modifier = Modifier
                .weight(1f)
                .clickAnimation {
                    if (goodsCount.intValue > 0)
                        goodsCount.intValue -= goodsCount.intValue
                }) {
                Text(text = "-", style = FinanceHelperTheme.typography.h2)
            }

        GoodsNameText(
            modifier = Modifier.weight(1f),
            value = goodsName.value,
            onValueChange = {
                goodsName.value = it
            },
            onError = {}
        )
        CurrencyAmountText(
            modifier = Modifier,
            value = goodsAmount.value,
            onValueChange = { goodsAmount.value = it }) {
        }

        CurrencyDropDownMenu(
            modifier = Modifier.weight(1f),
            currencyListState = currencyListState,
            color = FinanceHelperTheme.colors.defaultButtonColor,
            placeholderText = currency.value
        ) {
            currency.value = it
        }

    }
}

@Composable
fun ImmutableGoodsListItem(
    modifier: Modifier = Modifier,
    currencyListState: State<List<CurrencyUI>>,
    goodsUI: GoodsUI,
    onCountChanged: (Int) -> Unit
) {

    val goodsCount = remember {
        mutableIntStateOf(0)
    }
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier
            .weight(1f)
            .clickAnimation {
                goodsCount.intValue += goodsCount.intValue
                onCountChanged(goodsCount.intValue)
            }) {
            Image(imageVector = Icons.Filled.Add, contentDescription = "ic_add")
        }

        if (goodsUI.amount > 0)
            Box(modifier = Modifier
                .weight(1f)
                .clickAnimation {
                    if (goodsCount.intValue > 0)
                        goodsCount.intValue -= goodsCount.intValue
                    onCountChanged(goodsCount.intValue)
                }) {
                Text(text = "-", style = FinanceHelperTheme.typography.h2)
            }

        Text(
            modifier = Modifier.weight(1f),
            text = goodsUI.amount.toString(),
            style = FinanceHelperTheme.typography.h2
        )
        Row(
            modifier = Modifier.weight(2f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                modifier = Modifier.weight(2f),
                text = goodsUI.name,
                style = FinanceHelperTheme.typography.h2
            )
            Text(
                modifier = Modifier.weight(1f),
                text = goodsUI.cost.amount.toString(),
                style = FinanceHelperTheme.typography.h2
            )

            CurrencyDropDownMenu(
                modifier = Modifier.weight(1f),
                currencyListState = currencyListState,
                color = FinanceHelperTheme.colors.defaultButtonColor,
                placeholderText = goodsUI.cost.currency.name
            ) {

            }
        }
    }
}


@Composable
fun CurrencyDropDownMenu(
    modifier: Modifier,
    currencyListState: State<List<CurrencyUI>>,
    color: Color,
    placeholderText: String,
    onClick: (String) -> Unit
) {
    val currencyList by remember(currencyListState) {
        derivedStateOf {
            when (currencyListState) {
                is State.Success -> {
                    currencyListState.data
                }

                else -> {
                    listOf<CurrencyUI>()
                }
            }
        }
    }
    TextWithDropDownMenu(
        modifier = modifier,
        list = currencyList.map { it.name },
        placeholderText = placeholderText,
        color = color,
        onClick = onClick
    )

}