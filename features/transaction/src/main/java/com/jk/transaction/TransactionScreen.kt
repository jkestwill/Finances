package com.jk.transaction

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.CombinedModifier
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.jk.category_common_ui.CategoryUI
import com.jk.common_data.sha256
import com.jk.common_ui.ExpandedSection
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.State
import com.jk.common_ui.clickAnimation
import com.jk.common_ui.composable.TextWithDropDownMenu
import com.jk.goods_common_ui.GoodsUI
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyUI
import com.jk.shared_res.R
import com.jk.transaction_common_ui.OperationUI
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


// при поворачивании экрана в селект категори категории не добавляются

@Composable
fun TransactionScreen(
    viewModel: AddNewTransactionViewModel,
    categoryListId: List<String>?,
    onBackClick: (() -> Unit)?,
    onAddCategory: (List<String>?) -> Unit,
    onGoodsAdd: (List<String>?) -> Unit,
    onBack: () -> Unit
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
        Row(
            modifier = Modifier
                .background(
                    FinanceHelperTheme.colors.primaryBackground,
                    FinanceHelperTheme.shape.shapeRoundMedium
                )
                .padding(FinanceHelperTheme.shape.headerPadding)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (onBackClick != null)
                IconButton(
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
                    .weight(3f, fill = false),
                text = "Create Transaction",
                style = FinanceHelperTheme.typography.h1,
                maxLines = 2,
                overflow = TextOverflow.Visible,
            )

            Box(
                modifier = Modifier
                    .clickAnimation {
                        viewModel.addTransaction(onSuccess = {
                            Log.e("TransactionScreen", "Success")
                        }, onFailure = {
                            Log.e("TransactionScreen", "$it")
                        })
                    }
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .border(
                        FinanceHelperTheme.shape.borderStroke,
                        FinanceHelperTheme.shape.shapeRoundedLow
                    )
                    .background(
                        FinanceHelperTheme.colors.defaultButtonColor,
                        FinanceHelperTheme.shape.shapeRoundedLow
                    )
                    .padding(start = 10.dp, end = 10.dp)

                    .height(30.dp)
            )
            {
                Image(
                    modifier = Modifier.align(Alignment.Center),
                    imageVector = Icons.Filled.Add,
                    contentDescription = "ic_add"
                )
            }
            Box(
                modifier = Modifier
                    .clickAnimation {
                        onBack()
                    }
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .border(
                        FinanceHelperTheme.shape.borderStroke,
                        FinanceHelperTheme.shape.shapeRoundedLow
                    )
                    .background(
                        FinanceHelperTheme.colors.error,
                        FinanceHelperTheme.shape.shapeRoundedLow
                    )
                    .padding(start = 10.dp, end = 10.dp)
                    .height(30.dp)
            )
            {
                Image(
                    modifier = Modifier.align(Alignment.Center),
                    imageVector = Icons.Filled.Close,
                    contentDescription = "ic_cancel"
                )
            }
        }
    }) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(
                    top = scaffoldPadding.calculateTopPadding(),
                    bottom = scaffoldPadding.calculateBottomPadding(),
                    start = scaffoldPadding.calculateStartPadding(LayoutDirection.Ltr),
                    end = scaffoldPadding.calculateEndPadding(LayoutDirection.Rtl)
                ), verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TransactionInfoSection(
                modifier = Modifier.padding(
                    start = 10.dp, end = 10.dp
                ),
                currencyList = currencyList.value,
                goodsList = viewModel.newGoodsBuilderList,
                onChange = {
                    viewModel.operationBuilder.value = it
                    Log.e("TAG", "TransactionScreen:${it} ")
                }
            )

            CategorySection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                viewModel = viewModel,
                onAddCategory = onAddCategory
            )
            //разделить на стейты загрузка хуюска и обработать
            //если выбраны категории прихода то секция товаров недоступна
            GoodsSection(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .height(400.dp),
                immutableGoodsList = goodsList.itemSnapshotList.items,
                mutableGoodsList = viewModel.newGoodsBuilderList,
                currencyListState = currencyList.value,
                onGoodsAdd = onGoodsAdd,

                )
        }
    }
}

/**
 * Transaction section contains base info about transaction f.e name amount currency etc.
 * */
@Composable
fun TransactionInfoSection(
    modifier: Modifier = Modifier,
    currencyList: State<List<CurrencyUI>>,
    goodsList: SnapshotStateList<GoodsUI.Builder>,
    onChange: (OperationUI.Builder) -> Unit
) {
    val transactionName = rememberSaveable() {
        mutableStateOf("")
    }
    var amount = remember(goodsList) {
       mutableStateOf(
            goodsList.sumOf { it.build().cost.amount * it.build().amount },
       )
    }
    val amountString = rememberSaveable() {
        mutableStateOf(amount.value.toString())
    }
    val currency = rememberSaveable() {
        mutableStateOf(CurrencyUI(id = "", name = ""))
    }
    val operationBuilder = remember {
        mutableStateOf(OperationUI.Builder())
    }
    val coroutineScope = rememberCoroutineScope()

    val minHeightModifier by remember {
        mutableStateOf(Modifier.heightIn(40.dp))
    }

    LaunchedEffect(key1 = amount.value) {
        amountString.value = amount.value.toString()
    }
    DisposableEffect(key1 = amountString.value, currency.value, transactionName.value) {
        Log.e("TAG", "TransactionInfoSection:${amount.value} ", )
        val job = coroutineScope.launch {
            amount.value = try {
                amountString.value.toDouble()
            } catch (e: NumberFormatException) {
                0.0
            }
            delay(200)
            operationBuilder.value
                .setName(name = transactionName.value)
                .setMoney(
                    MoneyUI(
                        id = "${amount}${currency.value}".sha256(),
                        amount = amount.value,
                        currency = currency.value
                    )
                )
            onChange(operationBuilder.value)
        }
        onDispose {
            job.cancel()
        }
    }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(text = stringResource(id = R.string.general), style = FinanceHelperTheme.typography.h2)
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            TransactionNameText(modifier = minHeightModifier.weight(2f),
                transactionName.value,
                onValueChange = { transactionName.value = it },
                onError = {})

            CurrencyAmountText(modifier = minHeightModifier.weight(1f),
                value = amountString.value,
                onValueChange = {
                    amountString.value = it
                }, onError = {})

            CurrencyDropDownMenu(
                modifier = minHeightModifier.weight(1f),
                currencyListState = currencyList,
                placeholderText = stringResource(id = R.string.currency),
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
    ExpandedSection(modifier = modifier, expandedContent = {
        CategoryGrid(
            modifier = Modifier
                .fillMaxWidth(),
            items = viewModel.editableCategoriesStateList,
            onDelete = {
                viewModel.editableCategoriesStateList.remove(it)
            }, onAddCategory = onAddCategory
        )
    }) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.category),
                style = FinanceHelperTheme.typography.h2
            )
        }
    }
}

@Composable
fun CategoryGrid(
    modifier: Modifier,
    items: List<CategoryUI>,
    onAddCategory: (List<String>?) -> Unit,
    onDelete: (CategoryUI) -> Unit
) {
    HorizontalCategoryGrid(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        items = items.toList(),
        onChooseCategory = onAddCategory
    ) {
        onDelete(it)
    }
}

@Composable
fun GoodsSection(
    modifier: Modifier = Modifier,
    immutableGoodsList: List<GoodsUI>,
    mutableGoodsList: SnapshotStateList<GoodsUI.Builder>,
    currencyListState: State<List<CurrencyUI>>,
    onGoodsAdd: (List<String>?) -> Unit
) {
    ExpandedSection(modifier = modifier, expandedContent = {
        GoodsList(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            goodsList = mutableGoodsList,
            currencyListState = currencyListState,
        )
    }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.goods), style = FinanceHelperTheme.typography.h2
            )
            Box(modifier = Modifier
                .clickAnimation {
                    onGoodsAdd(immutableGoodsList.map { it.id })
                }
                .background(
                    FinanceHelperTheme.colors.defaultButtonColor,
                    FinanceHelperTheme.shape.shapeRoundMedium
                )
                .border(
                    FinanceHelperTheme.shape.borderStroke,
                    FinanceHelperTheme.shape.shapeRoundMedium
                )
                .padding(5.dp)
            ) {
                Image(imageVector = Icons.Filled.Search, contentDescription = "ic_search")
            }
        }
    }
}

@Composable
fun GoodsList(
    modifier: Modifier = Modifier,
    goodsList: SnapshotStateList<GoodsUI.Builder>,
    currencyListState: State<List<CurrencyUI>>,
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(5.dp)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(
                key = { goodsList[it].build().id },
                count = goodsList.size
            ) { index ->
                EditableListItem(modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 40.dp)
                    .onKeyEvent {
                        if (it.key == Key.Enter) {
                            focusManager.moveFocus(FocusDirection.Next)
                            true
                        } else {
                            false
                        }
                    },
                    currencyListState = currencyListState,
                    item = goodsList[index],
                    onChange = {
                        Log.e("TAG", "GoodsList onChange ${it}")
                        goodsList[index] = it.id(goodsList[index].build().id)
                    }, onRemove = {
                        //addCount-=1
                        goodsList.remove(goodsList[index])
                    })
            }
        }
        Box(modifier = Modifier
            .clickAnimation {
                goodsList.add(
                    GoodsUI
                        .Builder()
                        .id("${goodsList.size + System.currentTimeMillis()}".sha256())
                )
            }
            .fillMaxWidth()
            .height(30.dp)
            .background(
                FinanceHelperTheme.colors.defaultButtonColor,
                FinanceHelperTheme.shape.shapeRoundMedium
            )
            .border(
                FinanceHelperTheme.shape.borderStroke,
                FinanceHelperTheme.shape.shapeRoundMedium
            )
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
    item: GoodsUI.Builder,
    onChange: (GoodsUI.Builder) -> Unit,
    onRemove: () -> Unit
) {
    val preBuild = remember {
        mutableStateOf(item.build())
    }
    val goodsName = rememberSaveable() {
        mutableStateOf(preBuild.value.name)
    }
    val goodsAmount = rememberSaveable() {
        mutableStateOf(preBuild.value.cost.amount)
    }
    val goodsAmountString = rememberSaveable() {
        mutableStateOf(goodsAmount.value.toString())
    }
    val currency = rememberSaveable() {
        mutableStateOf(
            CurrencyUI(
                id = preBuild.value.cost.currency.id,
                name = preBuild.value.cost.currency.name
            )
        )
    }
    val goodsCount = rememberSaveable() {
        mutableStateOf(preBuild.value.amount)
    }
    val goodsCountString = rememberSaveable() {
        mutableStateOf(goodsCount.value.toString())
    }

    val (first, second) = remember { FocusRequester.createRefs() }
    val defaultModifier = Modifier
        .fillMaxHeight()
        .background(
            FinanceHelperTheme.colors.error,
            FinanceHelperTheme.shape.shapeRoundMedium
        )
        .border(
            FinanceHelperTheme.shape.borderStroke,
            FinanceHelperTheme.shape.shapeRoundMedium
        )
    val iconButtonModifier = defaultModifier.width(30.dp)

    LaunchedEffect(key1 = goodsCount.value) {
        Log.e("EditableListItem", "amount:${goodsAmount.value} ")
        goodsCountString.value = goodsCount.value.toString()
        goodsAmountString.value = (goodsAmount.value * goodsCount.value).toString()
    }

    LaunchedEffect(
        goodsCountString.value,
        goodsName.value,
        goodsAmountString.value,
        currency.value
    ) {
        Log.e("EditableListItem", "sum:${goodsAmount.value * goodsCount.value} ")

        goodsCount.value = try {
            Log.e("EditableListItem", "goods count string:${goodsCountString.value} ")
            if (goodsCountString.value.isNotEmpty()) {
                goodsCountString.value.toInt()
            } else 0
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0
        }
        onChange(
            item
                .id("${goodsAmount}${goodsCountString}".sha256())
                .amount(goodsCount.value)
                .name(goodsName.value)
                .cost(
                    MoneyUI(
                        id = "${goodsAmount}${currency.value}".sha256(),
                        amount = goodsAmount.value,
                        currency = currency.value
                    )
                )
        )
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(
            modifier = CombinedModifier(
                iconButtonModifier,
                Modifier
                    .background(
                        FinanceHelperTheme.colors.buttonDeleteColor,
                        FinanceHelperTheme.shape.shapeRoundMedium
                    )
                    .clickAnimation {
                        goodsCount.value += 1
                    }
            )
        )
        {
            Image(
                modifier = Modifier.align(Alignment.Center),
                imageVector = Icons.Filled.Add,
                contentDescription = "ic_add"
            )
        }
        if (goodsCount.value > 0)
            Box(
                modifier = CombinedModifier(
                    Modifier
                        .clickAnimation {
                            if (goodsCount.value > 0) {
                                goodsCount.value -= 1
                                // goodsCount.value=gCount.value.toString()
                            }
                        },
                    iconButtonModifier
                )
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "-",
                    style = FinanceHelperTheme.typography.h2,
                    textAlign = TextAlign.Center
                )
            }
        GoodsCountText(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            value = goodsCountString.value,
            onValueChange = {
                goodsCountString.value = it
            },
            onError = {

            }
        )
        GoodsNameText(modifier =
        Modifier
            .weight(2f)
            .fillMaxHeight()
            .focusRequester(first)
            .focusProperties {
                previous = second
                next = second
            }
            .focusable(
                enabled = true,
                interactionSource = remember { MutableInteractionSource() }),
            value = goodsName.value,
            onValueChange = {
                goodsName.value = it
            },
            onError = {

            })

        CurrencyAmountText(
            modifier = Modifier
                .fillMaxHeight()
                .focusRequester(second)
                .focusProperties {
                    previous = first
                    next = first
                }
                .weight(1f)
                .align(Alignment.CenterVertically),
            value = goodsAmountString.value,
            onValueChange = {
                goodsAmountString.value = it
                goodsAmount.value = try {
                    if (it.isNotEmpty()) {
                        println(it)
                        println(it.toDouble())
                        println(it.toDouble() / goodsCount.value)
                        if (goodsCount.value != 0)
                            it.toDouble() / goodsCount.value
                        else it.toDouble()
                    } else 0.0
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                    0.0
                } catch (e: ArithmeticException) {
                    e.printStackTrace()
                    it.toDouble()
                }
            },
            onError = {

            },
            onDone = {
                goodsAmount.value *= goodsCount.value
                goodsAmountString.value = goodsAmount.value.toString()
            })

        CurrencyDropDownMenu(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            currencyListState = currencyListState,
            color = FinanceHelperTheme.colors.defaultButtonColor,
            placeholderText = currency.value.name.ifEmpty { stringResource(id = R.string.currency) }
        ) {
            currency.value = it
        }
        Box(
            modifier = CombinedModifier(
                outer = Modifier
                    .clickAnimation {
                        onRemove()
                    }, inner = iconButtonModifier
            )
        ) {
            Image(
                modifier = Modifier.align(Alignment.Center),
                imageVector = Icons.Filled.Delete,
                contentDescription = "ic_add"
            )
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