package com.jk.transaction

import android.util.Log
import android.view.KeyEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.jk.category_common_ui.CategoryUI
import com.jk.common_data.sha256
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.State
import com.jk.common_ui.clickAnimation
import com.jk.common_ui.composable.TextWithDropDownMenu
import com.jk.goods_common_ui.GoodsUI
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyUI
import com.jk.shared_res.R
import kotlin.math.log


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
            if (onBackClick != null) IconButton(
                modifier = Modifier.align(Alignment.CenterVertically), onClick = onBackClick
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
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .height(400.dp),
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
        mutableStateOf(CurrencyUI(id = "", name = ""))
    }
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(text = "General", style = FinanceHelperTheme.typography.h2)
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            TransactionNameText(modifier = Modifier.weight(2f),
                transactionName.value,
                onValueChange = { transactionName.value = it },
                onError = {})

            CurrencyAmountText(modifier = Modifier.weight(1f),
                value = amount.value,
                onValueChange = {
                    amount.value = if (it.count { c -> c == '.' } <= 1) {
                        it
                    } else amount.value
                }) {}

            CurrencyDropDownMenu(
                modifier = Modifier.weight(1f),
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
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(modifier = Modifier, text = "Categories", style = FinanceHelperTheme.typography.h2)
            IconButton(modifier = Modifier
                .background(
                    color = FinanceHelperTheme.colors.defaultButtonColor,
                    shape = FinanceHelperTheme.shape.shape20
                )
                .height(24.dp)
                .width(24.dp), onClick = {
                onAddCategory(viewModel.editableCategoriesStateList.map { it.id })
            }) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Filled.Add,
                    contentDescription = "ic_add"
                )
            }
        }
        CategoryGrid(modifier = Modifier.fillMaxWidth(),
            items = viewModel.editableCategoriesStateList,
            onDelete = {
                viewModel.editableCategoriesStateList.remove(it)
            })
    }
}

@Composable
fun CategoryGrid(
    modifier: Modifier, items: List<CategoryUI>, onDelete: (CategoryUI) -> Unit
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
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.goods), style = FinanceHelperTheme.typography.h2
            )
            Box(modifier = Modifier
                .clickAnimation {

                }
                .background(
                    FinanceHelperTheme.colors.defaultButtonColor,
                    FinanceHelperTheme.shape.shape20
                )
                .border(FinanceHelperTheme.shape.borderStroke, FinanceHelperTheme.shape.shape20)) {
                Image(imageVector = Icons.Filled.Add, contentDescription = "ic_add")
            }
        }
        GoodsList(
            modifier = Modifier,
            goodsList = goodsList,
            currencyListState = currencyListState,
            onListChanged = {

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
    val editableItemList = remember {
        mutableStateListOf<GoodsUI.Builder>()
    }
    val focusManager = LocalFocusManager.current
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(5.dp)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 300.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(goodsList.size) {
                ImmutableGoodsListItem(currencyListState = currencyListState,
                    goodsUI = goodsList[it],
                    onCountChanged = {

                    })
            }
            items(
                key = { editableItemList[it].build().id },
                count = editableItemList.size
            ) { index ->

                EditableListItem(modifier = Modifier
                    .fillMaxWidth()
                    .onKeyEvent {
                        Log.e("TAG", "GoodsList:${it.key} ")
                        println(it.key)
                        if (it.key == Key.Enter) {
                            focusManager.moveFocus(FocusDirection.Next)
                            true
                        } else {
                            false
                        }
                    },
                    currencyListState = currencyListState,
                    onAdd = {
                        editableItemList.add(GoodsUI.Builder())
                    }, onRemove = {
                        //addCount-=1
                        editableItemList.remove(editableItemList[index])
                    })
            }
        }
        Box(modifier = Modifier
            .clickAnimation {
                editableItemList.add(
                    GoodsUI
                        .Builder()
                        .id("${editableItemList.size + System.currentTimeMillis()}".sha256())
                )
            }
            .fillMaxWidth()
            .height(30.dp)
            .background(
                FinanceHelperTheme.colors.defaultButtonColor,
                FinanceHelperTheme.shape.shape20
            )
            .border(FinanceHelperTheme.shape.borderStroke, FinanceHelperTheme.shape.shape20)
        ) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                imageVector = Icons.Filled.Add,
                contentDescription = "ic_add"
            )
        }
    }
}

// onAdd - when user left the focus on one of this elements
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditableListItem(
    modifier: Modifier,
    currencyListState: State<List<CurrencyUI>>,
    onAdd: (GoodsUI.Builder) -> Unit,
    onRemove: () -> Unit
) {
    val goodsName = rememberSaveable() {
        mutableStateOf("")
    }
    val goodsAmount = rememberSaveable() {
        mutableStateOf("")
    }
    val currency = rememberSaveable() {
        mutableStateOf(CurrencyUI(id = "", name = ""))
    }
    val goodsCount = rememberSaveable() {
        mutableIntStateOf(0)
    }
    val (first, second) = remember { FocusRequester.createRefs() }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(modifier = Modifier
            .size(30.dp)
            .background(
                FinanceHelperTheme.colors.defaultButtonColor,
                FinanceHelperTheme.shape.shape20
            )
            .border(FinanceHelperTheme.shape.borderStroke, FinanceHelperTheme.shape.shape20)
            .clickAnimation {
                goodsCount.intValue += 1
            }) {
            Image(
                modifier = Modifier.align(Alignment.Center),
                imageVector = Icons.Filled.Add,
                contentDescription = "ic_add"
            )
        }
        if (goodsCount.intValue > 0)
            Box(modifier = Modifier
                .size(30.dp)
                .clickAnimation {
                    if (goodsCount.intValue > 0) goodsCount.intValue -= 1
                }
                .background(
                    FinanceHelperTheme.colors.error,
                    FinanceHelperTheme.shape.shape20
                )
                .border(FinanceHelperTheme.shape.borderStroke, FinanceHelperTheme.shape.shape20)

            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "-",
                    style = FinanceHelperTheme.typography.h2,
                    textAlign = TextAlign.Center
                )
            }
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(20.dp),
            text = goodsCount.intValue.toString(),
            style = FinanceHelperTheme.typography.h3,
            textAlign = TextAlign.Center
        )
        GoodsNameText(modifier = Modifier
            .weight(2f)
            .focusRequester(first)
            .focusProperties {
                previous = second
                next = second
            }
            .onKeyEvent {
                if (it.key == Key.Enter) {
                    onAdd(
                        GoodsUI
                            .Builder()
                            .amount(goodsCount.intValue)
                            .cost(
                                MoneyUI(
                                    id = "${goodsAmount.value}${goodsCount}".sha256(),
                                    amount = goodsAmount.value.toDouble(),
                                    currency = currency.value
                                )
                            )
                    )
                    true
                } else false
            }
            .focusable(
                enabled = true,
                interactionSource = remember { MutableInteractionSource() }),

            value = goodsName.value,
            onValueChange = {
                goodsName.value = it
            },
            onError = {})

        CurrencyAmountText(modifier = Modifier
            .focusRequester(second)
            .focusProperties {
                previous = first
                next = first
            }
            .weight(1.5f),
            value = goodsAmount.value,
            onValueChange = { goodsAmount.value = it },
            onError = {

            })

        CurrencyDropDownMenu(
            modifier = Modifier.weight(1f),
            currencyListState = currencyListState,
            color = FinanceHelperTheme.colors.defaultButtonColor,
            placeholderText = currency.value.name
        ) {
            currency.value = it
        }
        Box(modifier = Modifier
            .size(40.dp)
            .background(
                FinanceHelperTheme.colors.error,
                FinanceHelperTheme.shape.shape20
            )
            .border(FinanceHelperTheme.shape.borderStroke, FinanceHelperTheme.shape.shape20)
            .clickAnimation {
                onRemove()
            }) {
            Image(
                modifier = Modifier.align(Alignment.Center),
                imageVector = Icons.Filled.Delete,
                contentDescription = "ic_add"
            )
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
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterVertically)
            .clickAnimation {
                goodsCount.intValue += goodsCount.intValue
                onCountChanged(goodsCount.intValue)
            }
        ) {
            Image(
                modifier = Modifier.align(Alignment.Center),
                imageVector = Icons.Filled.Add,
                contentDescription = "ic_add"
            )
        }

        if (goodsUI.amount > 0) Box(modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterVertically)
            .clickAnimation {
                if (goodsCount.intValue > 0) goodsCount.intValue -= goodsCount.intValue
                onCountChanged(goodsCount.intValue)
            }) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(),
                text = "-",
                style = FinanceHelperTheme.typography.h2,
                textAlign = TextAlign.End
            )
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
    onClick: (CurrencyUI) -> Unit
) {
    val currencyList by remember(currencyListState) {
        derivedStateOf {
            when (currencyListState) {
                is State.Success -> {
                    currencyListState.data
                }

                else -> {
                    listOf()
                }
            }
        }
    }
    TextWithDropDownMenu(
        modifier = modifier,
        list = currencyList,
        placeholderText = placeholderText,
        color = color,
        onClick = onClick
    )

}