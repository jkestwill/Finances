package com.jk.goods.goods_info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jk.common_data.sha256
import com.jk.common_ui.State
import com.jk.common_ui.composable.CharacterLimitTextField
import com.jk.goods_common_ui.models.GoodsUI
import com.jk.money_common_ui.CurrencyDropDownMenu
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyUI


@Composable
fun GoodsScreen(viewModel: GoodsViewModel, goodsId: String? = null) {
    goodsId?.let {
        viewModel.getById(it)
    }
    val goodsState = viewModel.goodsFlow.collectAsState()
    val currencyListState = viewModel.currencyListFlow.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        GoodsGeneralInfo(goodsState.value, currencyListState.value, onCreate = {
            viewModel.create(it)
        })
    }
}

@Composable
fun GoodsGeneralInfo(
    goodsUIState: State<GoodsUI>,
    currencyListState: State<List<CurrencyUI>>,
    onCreate: (GoodsUI) -> Unit
) {
    when (goodsUIState) {
        is State.Success -> {
            GoodsGeneralInfo(goodsUIState.data, currencyListState, onCreate = onCreate)
        }

        is State.Loading -> {
            CircularProgressIndicator()
        }

        is State.Error -> {

        }

        is State.None -> {
            GoodsGeneralInfo(null, currencyListState, onCreate)
        }
    }
}

@Composable
fun GoodsGeneralInfo(
    goodsUI: GoodsUI?,
    currencyListState: State<List<CurrencyUI>>,
    onCreate: (GoodsUI) -> Unit
) {
    val name = rememberSaveable() {
        mutableStateOf(goodsUI?.name ?: "")
    }
    val cost = rememberSaveable() {
        mutableStateOf(goodsUI?.cost?.amount?.toString() ?: "")
    }

    val currency = rememberSaveable() {
        mutableStateOf(goodsUI?.cost?.currency?.toString() ?: "")
    }
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        CharacterLimitTextField(value = name.value, onValueChange = {
            name.value = it
        }, placeHolder = {
            Text(text = "Name")
        })
        Row {
            CharacterLimitTextField(
                modifier = Modifier.weight(1f),
                value = cost.value,
                onValueChange = {
                    cost.value = it
                },
                placeHolder = {
                    Text(text = "Cost")
                })
            CurrencyDropDownMenu(
                modifier = Modifier,
                currencyListState = currencyListState,
                color = Color.Cyan,
                placeholderText = "Currency"
            ) {

            }
            CharacterLimitTextField(
                modifier = Modifier.weight(1f),
                value = currency.value,
                onValueChange = {
                    currency.value = it
                },
                placeHolder = {
                    Text(text = "Currency")
                })
        }

        Button(onClick = {
            onCreate(
                GoodsUI.Builder().id("${name.value}${cost.value}".sha256())
                    .cost(MoneyUI("", cost.value.toDouble(), CurrencyUI("zxc", currency.value)))
                    .name(name.value).build()
            )
        }) {
            Text(text = "Create")
        }
        // todo придумать какой нибудь IDGEnerator чтобы генерировать уникальные ид в репозиториях
        // todo придумать как соотносить цену и кол-во. Условно за 1кг грудки 8 руб и чтобы это автоматом считалось. Если за 1 кг грудки 8р то за 2кг 16р
    }
}