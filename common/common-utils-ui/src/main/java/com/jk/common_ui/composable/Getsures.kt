package com.jk.common_ui.composable

import android.util.Log
import androidx.compose.foundation.gestures.PressGestureScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Composable
fun rememberIncrement(
    startValue: Int = 0,
    delay: Long = 100L,
    step: Int = 1,
    onChange: (Int) -> Unit,
    onResult: (Int) -> Unit
): Gestures {
    val isIncrement = rememberSaveable() {
        mutableStateOf<Boolean?>(null)
    }

    val scope = rememberCoroutineScope()
    val result = remember(startValue) {
        mutableIntStateOf(startValue)
    }

    LaunchedEffect(key1 = isIncrement.value) {
        val mutex = Mutex()
        val job = scope.launch(Dispatchers.Default) {

            while (isIncrement.value != null) {
                if (isIncrement.value == true) {
                    mutex.withLock {
                        result.intValue += step
                    }
                } else if (isIncrement.value == false) {
                    mutex.withLock {
                        result.intValue = (result.intValue - step).coerceAtLeast(0)
                    }
                }
                delay(delay)
                onChange(result.intValue)
            }
        }
        if (isIncrement.value == null) {
            onResult(result.intValue)
            job.cancel()
        }
    }


    return rememberGestures(
        onLongPress = {
            isIncrement.value = it
        },
        onPress = {
            awaitRelease()
            isIncrement.value = null
        },
        onTap = {
            result.intValue += (if (it) 1 else -1).coerceAtLeast(0)
            onResult(result.intValue)
        }
    )
}

@Composable
fun rememberGestures(
    onLongPress: (Boolean) -> Unit = {},
    onPress: suspend PressGestureScope.() -> Unit = {},
    onTap: (Boolean) -> Unit = {}
): Gestures {
    val gestures = remember {
        mutableStateOf(
            Gestures(
                onLongPress = onLongPress,
                onPress = onPress,
                onTap = onTap
            )
        )
    }
    return gestures.value
}

data class Gestures(
    val onLongPress: (Boolean) -> Unit,
    val onPress: suspend PressGestureScope.() -> Unit,
    val onTap: (Boolean) -> Unit
)