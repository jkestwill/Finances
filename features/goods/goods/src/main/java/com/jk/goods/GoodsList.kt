package com.jk.goods

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import  com.jk.common.State

// сделать просто списком в параметры передать лист убрать viewmodel
@Composable
fun GoodsList(goodsViewModel: GoodsListViewModel) {
    LaunchedEffect(Unit) {
        goodsViewModel.add(GoodsListViewModel.test)
        goodsViewModel.getAllGoods()
    }

    val qq = goodsViewModel.goodsStateFlow.collectAsState(
        initial = State.None
    )

    LazyColumn{

    }
    Log.e("TAG", "GoodsList: ${qq.value}")
}
@Composable
fun GoodsItem(goodsUI: GoodsUI){
    Row {
        Text(text=goodsUI.name)
        Text(text = goodsUI.amount.toString())
        Text(text = goodsUI.cost.amount.toString())
        Text(text = goodsUI.cost.currency.name)


    }
}