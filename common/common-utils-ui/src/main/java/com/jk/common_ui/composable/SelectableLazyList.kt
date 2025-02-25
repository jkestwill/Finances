package com.jk.common_ui.composable

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import com.jk.common_ui.FinanceHelperTheme

@Composable
fun <T> SelectableLazyList(
    modifier: Modifier = Modifier,
    items: @Composable (T) -> Unit,
    selectedItemList: List<T>,
    onListChange: (List<T>) -> Unit
) {
    val sel = remember {
        mutableStateListOf<T>()
    }
    val pointerOffset = remember {
        mutableStateOf(Offset(0f,0f))
    }
    LazyColumn(modifier = modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                Log.e("TAG", "SelectableLazyList: ", )
                pointerOffset.value = change.position
            }
        }
    ) {
        items(selectedItemList.size) {
            val intSource = remember {
                MutableInteractionSource()
            }

            val isHovered by intSource.collectIsHoveredAsState()
            val isSelected = remember {
                mutableStateOf(false)
            }
            val color = remember {
                derivedStateOf {
                    if (isSelected.value || isHovered)
                        Color.Red
                    else
                        Color.Blue
                }

            }
            val pos = remember {
                mutableStateOf(Offset(0f,0f))
            }
            val size = remember {
                mutableStateOf(IntSize(0,0))
            }
            LaunchedEffect(pointerOffset.value) {
                if (pointerOffset.value.y in pos.value.y..size.value.height.toFloat() || pointerOffset.value.x in pos.value.x..size.value.width.toFloat()) {
                    Log.e("TAG", "SelectableLazyList:sasasas ", )
                }
                Log.e("TAG", "SelectableLazyList:sasasas ", )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(color = color.value)
                .onGloballyPositioned {
                    size.value = it.size
                    pos.value = pointerOffset.value
                }
//                .pointerInput(Unit) {
//
//                    detectDragGestures { change, dragAmount ->
//                        if (isSelected.value) {
//                            sel.remove(selectedItemList[it])
//                            isSelected.value = false
//                        } else {
//                            sel.add(selectedItemList[it])
//                            isSelected.value = true
//                        }
//
//                        change.consume()
//                        Log.e(
//                            "TAG",
//                            "SelectableLazyList[${selectedItemList[it]}]: ${this.size}",
//                        )
//                        Log.e(
//                            "TAG",
//                            "SelectableLazyList[${selectedItemList[it]}]: ${change.position}",
//                        )
//                        onListChange(sel.toList())
//                    }
//                }
//                .clickable {
//                    if (isSelected.value) {
//                        sel.remove(selectedItemList[it])
//                        isSelected.value = false
//                    } else {
//                        sel.add(selectedItemList[it])
//                        isSelected.value = true
//                    }
//                    onListChange(sel.toList())
//
//                }
            ) {
                items(selectedItemList[it])
            }
        }
    }
}

@Preview
@Composable
internal fun SelectableLazyListPreview() {
    FinanceHelperTheme {
        SelectableLazyList<String>(
            items = {
                Text(text = it)
            }, selectedItemList = listOf("zzxc", "2", "3", "4")
        ) {
            Log.e("GAT", "SelectableLazyListPreview: ${it}")
        }
    }
}