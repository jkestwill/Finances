
package com.jk.transaction

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.jk.category_common_ui.CategoryUI
import com.jk.common_data.sha256
import com.jk.common_ui.ExpandedSection
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.State
import com.jk.common_ui.clickAnimation
import com.jk.goods_common_ui.GoodsList
import com.jk.goods_common_ui.GoodsUI
import com.jk.money_common_ui.CurrencyAmountText
import com.jk.money_common_ui.CurrencyDropDownMenu
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
    goodsIdList: List<String>?,
    onBackClick: (() -> Unit)?,
    onAddCategory: (List<String>?) -> Unit,
    onGoodsAdd: (List<String>?) -> Unit,
    onBack: () -> Unit
) {
    val preGoods = viewModel.incomingGoodsFlow.collectAsState()
    LaunchedEffect(key1 = goodsIdList) {
        println(goodsIdList?.size)
        if (goodsIdList != null) {
            viewModel.getGoodsListById(goodsIdList ?: listOf())
        }
    }

    LaunchedEffect(key1 = preGoods.value) {
        Log.e("TransactionScreen", "preselectedGoods:${preGoods.value} ")
    }
    LaunchedEffect(key1 = categoryListId) {
        if (categoryListId != null && (viewModel.prevCategoryIdList == null || viewModel.prevCategoryIdList != categoryListId)) {
            viewModel.prevCategoryIdList = categoryListId
            viewModel.getCategoryListById(categoryListId)
        }

        Log.e("QQ", "TransactionScreen:${categoryListId} ")
    }
    val currencyList = viewModel.currencyListState.collectAsState()
    val goodsAmount = rememberSaveable() {
        mutableStateOf(0.0)
    }
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

        }
    }, bottomBar = {
        Row(
            modifier = Modifier.padding(FinanceHelperTheme.shape.headerPadding),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            val defaultModifier = Modifier
                .weight(1f)
                .border(
                    FinanceHelperTheme.shape.borderStroke,
                    FinanceHelperTheme.shape.shapeRoundMedium
                )
                .padding(10.dp)

            Box(
                modifier = Modifier
                    .clickAnimation {
                        viewModel.addTransaction(onSuccess = {
                            Log.e("TransactionScreen", "Success")
                        }, onFailure = {
                            Log.e("TransactionScreen", "$it")
                        })
                    }
                    .background(
                        FinanceHelperTheme.colors.defaultButtonColor,
                        FinanceHelperTheme.shape.shapeRoundMedium
                    )
                    .then(defaultModifier)
            )
            {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = R.string.create),
                    style = FinanceHelperTheme.typography.h3
                )
            }
            Box(
                modifier = Modifier
                    .clickAnimation {
                        onBack()
                    }

                    .background(
                        FinanceHelperTheme.colors.error,
                        FinanceHelperTheme.shape.shapeRoundMedium
                    )
                    .then(defaultModifier)
            ) {

                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = R.string.cancel),
                    style = FinanceHelperTheme.typography.h3
                )
            }

            Box(
                modifier = Modifier
                    .clickAnimation {
                        onBack()
                    }
                    .then(defaultModifier)

            )
            {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Save",
                    style = FinanceHelperTheme.typography.h3
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 10.dp, end = 10.dp
                    ),
                currencyList = currencyList.value,
                goodsList = goodsAmount.value,
                onChange = {
                    viewModel.operationBuilder.value = it
                    Log.e("TAG", "TransactionScreen:${it} ")
                }
            )

            CategorySection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
                    .heightIn(max = 100.dp),
                viewModel = viewModel,
                onAddCategory = onAddCategory
            )
            //разделить на стейты загрузка хуюска и обработать
            //если выбраны категории прихода то секция товаров недоступна
            GoodsSection(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp),
                immutableStateGoodsList = preGoods.value,
                mutableGoodsList = viewModel.newGoodsBuilderList,
                currencyListState = currencyList.value,
                onGoodsAdd = onGoodsAdd,
                onGoodsListChange = { list ->
                    goodsAmount.value = list.sumOf {
                        it.build().cost.amount * it.build().amount
                    }
                }
            )
            ScheduleSection(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
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
    goodsList: Double,
    onChange: (OperationUI.Builder) -> Unit
) {
    val transactionName = rememberSaveable() {
        mutableStateOf("")
    }
    val amount = remember(goodsList) {
        mutableStateOf(
            goodsList
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
        Log.e("TAG", "TransactionInfoSection:${amount.value} ")
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
            .fillMaxWidth(),
        items = items.toList(),
        onChooseCategory = onAddCategory
    ) {
        onDelete(it)
    }
}

@Composable
fun GoodsSection(
    modifier: Modifier = Modifier,
    immutableStateGoodsList: State<List<GoodsUI>>,
    mutableGoodsList: SnapshotStateList<GoodsUI.Builder>,
    currencyListState: State<List<CurrencyUI>>,
    onGoodsAdd: (List<String>?) -> Unit,
    onGoodsListChange: (List<GoodsUI.Builder>) -> Unit
) {
    val deletedImmutables = remember {
        mutableStateOf(listOf<String>())
    }
    // не удаляются товары пришедшие из диалога
    // при перевороте экрана
    val immutableList = remember(immutableStateGoodsList) {
        derivedStateOf {
            when (immutableStateGoodsList) {
                is State.Success -> {
                    Log.e("GoodsSection", "onAddImmutableList:${immutableStateGoodsList.data} ")
                    immutableStateGoodsList.data.map { GoodsUI.Builder(it) }.filter {
                        !deletedImmutables.value.contains(it.build().id)
                    }

                }
                else -> {
                    listOf()
                }
            }
        }
    }


    val goodsList = remember() {
        mutableStateOf(immutableList.value + mutableGoodsList)
    }
    LaunchedEffect(key1 = immutableList.value, mutableGoodsList.size) {
        if (goodsList.value.size > 2) {
            println(goodsList.value[0] == goodsList.value[1])
        }
        println("q" + immutableList.value)
        println(mutableGoodsList.toList())
        goodsList.value = immutableList.value + mutableGoodsList

        Log.e("TAG", "GoodsSection: ${mutableGoodsList.toList()}")
    }

    ExpandedSection(modifier = modifier, expandedContent = {
        GoodsList(
            modifier = Modifier
                .heightIn(max = 200.dp),
            goodsList = goodsList.value,
            currencyListState = currencyListState,
            onGoodsListChange = {
                goodsList.value = it
                onGoodsListChange(it)
            },
            onChange = { goodsBuilder, index ->


            },
            onRemove = {
                if (!mutableGoodsList.remove(it)) {
                    deletedImmutables.value += it.build().id
                }
            },
            onAdd = {
                mutableGoodsList.add(it)
            }

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

                    onGoodsAdd(immutableList.value.map { it.build().id })
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
fun ScheduleSection(modifier: Modifier = Modifier) {
    ExpandedSection(modifier = modifier,
        content = {
            Row {
                Text(text = "Schedules", style = FinanceHelperTheme.typography.h2)
            }
        },
        expandedContent = {

        }
    )
}


// onAdd - when user left the focus on one of this elements



