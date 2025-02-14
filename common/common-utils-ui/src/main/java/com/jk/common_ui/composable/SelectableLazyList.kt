package com.jk.common_ui.composable

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> SelectableLazyList(modifier: Modifier=Modifier,items: @Composable LazyListScope.()->Unit,selectedItemList:List<T>) {
    //

}