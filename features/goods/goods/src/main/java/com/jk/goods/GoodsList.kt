package com.jk.goods

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.jk.common.State

// сделать просто списком в параметры передать лист убрать viewmodel
@Composable
fun GoodsList(goodsViewModel:GoodsListViewModel){
    LaunchedEffect(Unit){
      //  goodsViewModel.add(GoodsListViewModel.test)
        goodsViewModel.getAllGoods()

    }

   val qq= goodsViewModel.goodsStateFlow.collectAsState(
        initial = State.None
    )

    Log.e("TAG", "GoodsList: ${qq.value}", )
}