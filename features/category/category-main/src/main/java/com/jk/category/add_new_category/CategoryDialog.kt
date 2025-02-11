package com.jk.category.add_new_category

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.State
import com.jk.common_ui.clickAnimation
import com.jk.common_ui.colorPickList
import com.jk.common_ui.composable.Error
import com.jk.common_ui.composable.ThemedTextField

private const val TAG = "CategoryDialog"

@Composable
fun CategoryDialog(
    viewModel: AddCategoryViewModel,
    categoryLabel: String,
    categoryNamePlaceholder: String,
    expensesLabel: String,
    buttonCreateLabel: String,
    buttonCancelLabel: String,
    onDismiss: () -> Unit,
    onNewCategoryCreated: () -> Unit
) {
    val name = remember {
        mutableStateOf("")
    }
    val colorHex = remember {
        mutableStateOf<Int?>(null)
    }

    val isExpensesSwitch = remember {
        mutableStateOf(false)
    }
    AddCategory(viewModel = viewModel, onNewCategoryCreated = onNewCategoryCreated)


    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {

            Error(
                modifier = Modifier
                    .align(Alignment.TopStart),
                message = viewModel.addCategoryError,
            )
            Box(
                Modifier
                    .align(Alignment.Center)
                    .width(350.dp)
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier
                        .clip(FinanceHelperTheme.shape.shapeRoundedLow)
                        .background(FinanceHelperTheme.colors.secondaryBackground)
                        .padding(5.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = categoryLabel,
                        style = FinanceHelperTheme.typography.label
                    )
                    ThemedTextField(
                        modifier = Modifier.padding(FinanceHelperTheme.shape.textPadding),
                        value = name.value,
                        onValueChange = {
                            name.value = it
                        },
                        placeHolder = {
                            Text(
                                modifier = Modifier.alpha(0.5f),
                                text = categoryNamePlaceholder,
                                style = FinanceHelperTheme.typography.body
                            )
                        },
                        textStyle = FinanceHelperTheme.typography.body
                    )

                    ColorPicker(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(start = 10.dp, end = 10.dp)

                    ) {
                        colorHex.value = if (colorHex.value != it) {
                            Log.e(this.javaClass.name, "CategoryDialog colorPicker:$it ")
                            it

                        } else {
                            Log.e(this.javaClass.name, "CategoryDialog colorPicker null:  ")
                            null
                        }
                        Log.e(this.javaClass.name, "CategoryDialog: ")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(FinanceHelperTheme.shape.textPadding),
                            text = expensesLabel,
                            style = FinanceHelperTheme.typography.body,
                        )
                        Switch(
                            modifier = Modifier,
                            checked = isExpensesSwitch.value,
                            onCheckedChange = {
                                isExpensesSwitch.value = it
                            },
                            colors = SwitchDefaults.colors()
                                .copy(checkedTrackColor = FinanceHelperTheme.colors.defaultButtonColor)
                        )
                    }

//Create###Cancel Buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(FinanceHelperTheme.shape.headerPadding)
                    ) {
                        Box(modifier = Modifier
                            .weight(1f)
                            .clickable {
                                viewModel.addCategory(
                                    name = name.value,
                                    color = colorHex.value?: colorPickList[0].toArgb(),
                                    isExpenses = isExpensesSwitch.value
                                )

                            }) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(5.dp),
                                text = buttonCreateLabel,
                                style = FinanceHelperTheme.typography.h2,
                                textAlign = TextAlign.Center
                            )
                        }
                        Box(modifier = Modifier
                            .weight(1f)
                            .clickable {
//                                navController.popBackStack(
//                                    route = Routes.NEW_CATEGORY,
//                                    inclusive = true,
//                                    saveState = false
//                                )
                                onDismiss()
                            }) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(5.dp),
                                text = buttonCancelLabel,
                                style = FinanceHelperTheme.typography.h2,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

            }

        }
    }

}


@Composable
fun ColorPicker(modifier: Modifier = Modifier, onPick: (Int?) -> Unit) {
    val selectedColor = remember {
        mutableStateOf<Int?>(null)
    }
    val borderColor = FinanceHelperTheme.colors.secondaryText
    val shape = FinanceHelperTheme.shape.shapeRoundedLow
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        columns = GridCells.Adaptive(30.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(colorPickList) {
            val borderModifier by remember(selectedColor.value) {
                mutableStateOf(
                    if (selectedColor.value == it.toArgb()) Modifier.border(
                        2.dp,
                        borderColor,
                        shape
                    ) else Modifier
                )
            }
            Box(
                modifier = borderModifier
                    .height(30.dp)
                    .width(25.dp)
                    .background(color = it, shape = FinanceHelperTheme.shape.shapeRoundMedium)
                    .clickAnimation {

                        if (it.toArgb() == selectedColor.value) {
                            Log.e(
                                TAG,
                                "ColorPicker: it.value == selectedColor.value ${it.toArgb() == selectedColor.value}"
                            )
                            selectedColor.value = null
                        } else {
                            selectedColor.value = it.toArgb()
                        }
                        onPick(selectedColor.value)
                    }
            )
        }
    }
}

@Composable
fun AddCategory(viewModel: AddCategoryViewModel, onNewCategoryCreated: () -> Unit) {
    val state = viewModel.addCategoryResponse.collectAsState()
    val context = LocalContext.current

    when (state.value) {
        is State.None -> {
            Log.e(TAG, "AddCategory:NONE")
        }

        is State.Loading -> {
            Log.e(TAG, "AddCategory:LOADING")
        }

        is State.Success -> {
            Log.e(TAG, "AddCategory:Success")
            onNewCategoryCreated()
        }

        is State.Error -> {
            Log.e(TAG, "AddCategory:ERROR")
            Toast.makeText(
                context,
                (state.value as State.Error<Long>).message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}



