package com.jk.goods.goods_info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.jk.common_ui.State
import com.jk.common_ui.composable.ThemedTextField
import com.jk.goods_common_ui.GoodsUI


@Composable
fun GoodsScreen(viewModel: GoodsViewModel, goodsId: String? = null) {
    goodsId?.let {
        viewModel.getById(it)
    }
    val goodsState = viewModel.goodsFlow.collectAsState()
    Column(modifier = Modifier.fillMaxWidth()) {
        GoodsGeneralInfo(goodsState.value)
    }
}

@Composable
fun GoodsGeneralInfo(goodsUIState: State<GoodsUI>) {
    when (goodsUIState) {
        is State.Success -> {
            GoodsGeneralInfo(goodsUIState.data)
        }

        is State.Loading -> {

        }

        is State.Error -> {

        }

        is State.None -> {

        }
    }
}

@Composable
fun GoodsGeneralInfo(goodsUI: GoodsUI?) {
    val name = rememberSaveable() {
        mutableStateOf(goodsUI?.name ?: "")
    }
    val amount = rememberSaveable() {
        mutableStateOf(goodsUI?.amount?.toString() ?: "")
    }
    val cost = rememberSaveable() {
        mutableStateOf(goodsUI?.moneyUI?.amount?.toString() ?: "")
    }

    val currency = rememberSaveable() {
        mutableStateOf(goodsUI?.moneyUI?.currency?.toString() ?: "")
    }
    Column(Modifier.fillMaxWidth()) {
        ThemedTextField(value = name.value, onValueChange = {
            name.value = it
        })
        Row {
            ThemedTextField(value = cost.value, onValueChange = {
                cost.value = it
            })
            ThemedTextField(value = currency.value, onValueChange = {
                currency.value = it
            })
        }
        // todo придумать как соотносить цену и кол-во. Условно за 1кг грудки 8 руб и чтобы это автоматом считалось. Если за 1 кг грудки 8р то за 2кг 16р
    }
}