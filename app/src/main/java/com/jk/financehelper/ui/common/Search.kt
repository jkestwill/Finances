package com.jk.financehelper.ui.common

import android.util.Log
import android.view.ViewTreeObserver
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.jk.financehelper.ui.custom.CharacterLimitTextField
import com.jk.financehelper.ui.theme.FinanceHelperTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class SearchState {
    COLLAPSED, EXPANDED
}

private const val TAG = "Search"

//
@Composable
fun Search(
    modifier: Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    // onClick: (SearchState) -> Unit
) {
    val keyboardState = keyboardAsState()
    val state = remember {
        mutableStateOf(SearchState.COLLAPSED)
    }
    val coroutineScope = rememberCoroutineScope()
    val offsetY = animateFloatAsState(
        targetValue = if (state.value == SearchState.COLLAPSED) 0f else 100f,
        tween(100, if (state.value == SearchState.COLLAPSED) 300 else 0, LinearOutSlowInEasing)
    )
    val keyboard = LocalSoftwareKeyboardController.current
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val size =
        animateDpAsState(
            targetValue = if (state.value == SearchState.COLLAPSED) (-100).dp else 150.dp,
            tween(100, if (state.value == SearchState.COLLAPSED) 0 else 300, LinearOutSlowInEasing)
        )

    Row(modifier = modifier
        .graphicsLayer {
            this.translationY = offsetY.value
        }, horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        IconButton(modifier = Modifier
            .width(40.dp)
            .height(40.dp)
            .background(
                FinanceHelperTheme.colors.secondaryBackground,
                FinanceHelperTheme.shape.shape20
            )
            .zIndex(1f), onClick = {
            coroutineScope.launch {
                state.value = SearchState.EXPANDED
                delay(200)
                focusRequester.requestFocus()
                keyboard?.show()
            }
        }) {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "ic_search")
        }

        CharacterLimitTextField(
            modifier = modifier
                .height(40.dp)
                .width(size.value)
                .focusRequester(focusRequester),
            textStyle = FinanceHelperTheme.typography.h3,
            maxLines = 1,
            value = text,
            onValueChange = onValueChange,
            textLimit = TextLimit.SearchTextLimit(),
            onError = {
                Log.e("TAG", "Search:${it} ")
            },
        )

    }
    LaunchedEffect(key1 = keyboardState.value) {
        if (keyboardState.value == KeyboardState.CLOSED) {
            //focusRequester.captureFocus()
            Log.e(TAG, "Search: ${size.value}")
            state.value = SearchState.COLLAPSED
        }
    }
}

enum class KeyboardState {
    OPEN, CLOSED
}

@Composable
fun keyboardAsState(): State<KeyboardState> {
    val keyboardState = remember { mutableStateOf(KeyboardState.CLOSED) }
    val view = LocalView.current
    val viewTreeObserver = view.viewTreeObserver

    DisposableEffect(viewTreeObserver) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = android.graphics.Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            keyboardState.value = if (keypadHeight > screenHeight * 0.15) {
                KeyboardState.OPEN
            } else {
                KeyboardState.CLOSED
            }
        }
        viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)

        onDispose {
            viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }

    return keyboardState
}