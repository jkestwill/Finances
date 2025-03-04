package com.jk.goods_common_ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.CombinedModifier
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jk.common_data.sha256
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.State
import com.jk.common_ui.clickAnimation
import com.jk.common_ui.composable.DraggableWidthContent
import com.jk.common_ui.composable.rememberIncrement
import com.jk.money_common_ui.CurrencyAmountText
import com.jk.money_common_ui.CurrencyDropDownMenu
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyDateUI
import com.jk.money_common_ui.MoneyUI
import java.time.LocalDate

@Composable
fun GoodsList(
    modifier: Modifier = Modifier,
    goodsList: List<GoodsMoneyDateUI.Builder>,
    currencyListState: State<List<CurrencyUI>>,
    onGoodsListChange: (List<GoodsMoneyDateUI.Builder>) -> Unit,
    onChange: (GoodsMoneyDateUI.Builder, Int) -> Unit,
    onAdd: (GoodsMoneyDateUI.Builder) -> Unit,
    onRemove: (GoodsMoneyDateUI.Builder) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(5.dp)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 150.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(
                key = {
                    goodsList[it].build().id
                },
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
                        if (goodsList.isNotEmpty() && index < goodsList.size) {
                            onChange(it.id(goodsList[index].build().id), index)
                            onGoodsListChange(goodsList)
                        }
                    }, onRemove = {
                        onRemove(goodsList[index])
                        onGoodsListChange(goodsList - goodsList[index])
                    })
            }
        }
        Box(modifier = Modifier
            .clickAnimation {
                onAdd(
                    GoodsMoneyDateUI
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

@OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeUiApi::class)
@Composable
fun EditableListItem(
    modifier: Modifier,
    currencyListState: State<List<CurrencyUI>>,
    item: GoodsMoneyDateUI.Builder,
    onChange: (GoodsMoneyDateUI.Builder) -> Unit,
    onRemove: () -> Unit,
) {
    val preBuild = remember(item) {
        mutableStateOf(item.build())
    }
    val goodsName = rememberSaveable() {
        mutableStateOf(preBuild.value.name)
    }
    val goodsCount = rememberSaveable(preBuild.value.amount) {
        mutableStateOf(preBuild.value.amount)
    }
    val goodsCountString = rememberSaveable(preBuild.value.amount) {
        mutableStateOf(preBuild.value.amount.toString())
    }
    //todo поменять
    val goodsAmount = rememberSaveable() {
        mutableStateOf(0.0)
    }
    //todo поменять
    val goodsAmountString = rememberSaveable() {
        mutableStateOf("")
    }
    //todo поменять
    val currency = rememberSaveable() {
        mutableStateOf(
            CurrencyUI(
                id = "",
                name = ""
            )
        )
    }


    val (first, second) = remember { FocusRequester.createRefs() }
    val defaultModifier = Modifier
        .background(
            FinanceHelperTheme.colors.error,
            FinanceHelperTheme.shape.shapeRoundMedium
        )
        .border(
            FinanceHelperTheme.shape.borderStroke,
            FinanceHelperTheme.shape.shapeRoundMedium
        )

    val iconButtonModifier = defaultModifier.size(32.dp)

    val increment = rememberIncrement(startValue = goodsCount.value, onChange = {
        goodsCountString.value = it.toString()

    }) {
        goodsCount.value = it
    }

    LaunchedEffect(key1 = goodsCountString.value) {
        Log.d("TAG", "EditableListItem: onChange ${goodsCountString.value}")
    }

    LaunchedEffect(key1 = goodsCount.value) {
        goodsCountString.value = goodsCount.value.toString()
        goodsAmountString.value = (goodsAmount.value * goodsCount.value).toString()
    }


    LaunchedEffect(
        goodsCount.value,
        goodsName.value,
        goodsAmount.value,
        currency.value
    ) {
        onChange(
            item
                .amount(goodsCount.value)
                .name(goodsName.value)
                .cost(
                    MoneyUI(
                        id = "${goodsAmount}${currency.value}".sha256(),
                        amount = goodsAmount.value,
                        currency = currency.value,
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
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                increment.onPress(this)
                            },
                            onLongPress = {
                                increment.onLongPress(true)
                            },
                            onTap = {
                                increment.onTap(true)
                            }
                        )
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
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    increment.onPress(this)
                                },
                                onLongPress = {
                                    increment.onLongPress(false)
                                },
                                onTap = {
                                    increment.onTap(false)
                                }

                            )
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
        DraggableWidthContent(width = 50) { modifier ->
            GoodsCountText(
                modifier = modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                value = goodsCountString.value,
                onValueChange = {
                    goodsCount.value = try {
                        if (it.isNotEmpty()) {
                            it.toInt()
                        } else 0
                    } catch (e: NumberFormatException) {
                        e.printStackTrace()
                        0
                    }
                },
                onError = {

                }
            )
        }

        DraggableWidthContent(width = 50) { modifier ->
            GoodsNameText(modifier =
            Modifier
                .fillMaxHeight()
                .focusProperties {
                    previous = second
                    next = second
                }
                .focusable(
                    enabled = true,
                    interactionSource = remember { MutableInteractionSource() })
                .then(modifier.weight(2f)),
                value = goodsName.value,
                onValueChange = {
                    goodsName.value = it
                },
                onError = {

                })
        }

        DraggableWidthContent(width = 50) { mod ->
            CurrencyAmountText(
                modifier = Modifier
                    .fillMaxHeight()
                    .focusRequester(second)
                    .focusProperties {
                        previous = first
                        next = first
                    }
                    .align(Alignment.CenterVertically)
                    .then(mod.weight(1f)),

                value = goodsAmountString.value,
                onValueChange = {
                    goodsAmountString.value = it
                    goodsAmount.value = try {
                        if (it.isNotEmpty()) {
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
                    //  goodsAmount.value *= goodsCount.value
                    goodsAmountString.value = goodsAmount.value.toString()
                })
        }
        DraggableWidthContent(width = 70) {
            CurrencyDropDownMenu(
                modifier = Modifier
                    .fillMaxHeight()
                    .then(it.weight(1f)),
                currencyListState = currencyListState,
                color = FinanceHelperTheme.colors.defaultButtonColor,
                placeholderText = currency.value.name.ifEmpty { stringResource(id = com.jk.shared_res.R.string.currency) }
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
}

