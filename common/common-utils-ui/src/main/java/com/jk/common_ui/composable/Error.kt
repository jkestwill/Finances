package com.jk.common_ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jk.common_ui.FinanceHelperTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow

private const val TAG = "Error"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxScope.Error(
    modifier: Modifier = Modifier,
    message: SharedFlow<String>
) {

    val visibility = remember {
        mutableStateOf(false)
    }
    val pp = remember {
        mutableStateOf("")
    }
    val state = rememberSwipeToDismissBoxState(positionalThreshold = { 50f })

    LaunchedEffect(key1 = message) {

        message.collect{
            visibility.value = true
            println(it)
            if(it.isNotEmpty()){
                state.reset()
            }
            pp.value = it
            delay(2000)
            state.dismiss(SwipeToDismissBoxValue.EndToStart)
        }

    }
    if(visibility.value)
        SwipeToDismissBox(
            modifier = modifier,
            state = state,
            backgroundContent = {
            }
        ) {
            Box(
                modifier = Modifier
                    .background(
                        FinanceHelperTheme.colors.error,
                        RoundedCornerShape(topEndPercent = 20, bottomEndPercent = 20)
                    )
                    .widthIn(80.dp, 200.dp)
                    .height(30.dp)
                    .padding(FinanceHelperTheme.shape.buttonPadding)
            ) {
                AutoSizeText(
                    text = pp.value,
                    style = FinanceHelperTheme.typography.body,
                    alignment = Alignment.Center,
                    minTextSize = 10.sp,
                    maxTextSize = 16.sp,
                    maxLines = 2
                )
            }

        }

}