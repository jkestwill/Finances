package com.jk.financehelper.category.new_category

import android.annotation.SuppressLint
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.jk.category.AddCategoryViewModel
import com.jk.common_data.State
import com.jk.financehelper.R
import com.jk.financehelper.navigation.Routes
import com.jk.financehelper.ui.common.Error
import com.jk.financehelper.ui.common.clickAnimation
import com.jk.financehelper.ui.custom.ThemedTextField
import com.jk.financehelper.ui.theme.FinanceHelperTheme
import com.jk.financehelper.ui.theme.colorPickList

private const val TAG = "CategoryDialog"

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun CategoryDialog(viewModel: AddCategoryViewModel, navController: NavController) {
    val name = remember {
        mutableStateOf("")
    }
    val colorHex = remember {
        mutableStateOf<ULong?>(null)
    }

    val isExpensesSwitch = remember {
        mutableStateOf(false)
    }
    AddCategory(viewModel = viewModel, navController = navController)
    val addCategoryErrorVisibility = remember {
        mutableStateOf(false)
    }


    Log.e("zxc", "CategoryDialog: $addCategoryErrorVisibility")

    Dialog(properties = DialogProperties(usePlatformDefaultWidth = false), onDismissRequest = {
        navController.popBackStack()
    }) {
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
                        .clip(FinanceHelperTheme.shape.shape10)
                        .background(FinanceHelperTheme.colors.secondaryBackground)
                        .padding(5.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = stringResource(id = R.string.add_new_category),
                        style = FinanceHelperTheme.typography.label
                    )
                    ThemedTextField(
                        modifier = Modifier.padding(FinanceHelperTheme.shape.padding),
                        value = name.value,
                        onValueChange = {
                            name.value = it
                        },
                        placeHolder = {
                            Text(
                                modifier = Modifier.alpha(0.5f),
                                text = stringResource(id = R.string.create),
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
                                .padding(FinanceHelperTheme.shape.padding),
                            text = stringResource(id = R.string.expenses),
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
                            .padding(FinanceHelperTheme.shape.padding)
                    ) {
                        Box(modifier = Modifier
                            .weight(1f)
                            .clickable {
                                viewModel.addCategory(
                                    name = name.value,
                                    color = colorHex.value,
                                    isExpenses = isExpensesSwitch.value
                                )

                            }) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(5.dp),
                                text = stringResource(id = R.string.create),
                                style = FinanceHelperTheme.typography.h2,
                                textAlign = TextAlign.Center
                            )
                        }
                        Box(modifier = Modifier
                            .weight(1f)
                            .clickable {
                                navController.popBackStack(
                                    route = Routes.NEW_CATEGORY,
                                    inclusive = true,
                                    saveState = false
                                )
                            }) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(5.dp),
                                text = stringResource(id = R.string.cancel),
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
fun ColorPicker(modifier: Modifier = Modifier, onPick: (ULong?) -> Unit) {
    val selectedColor = remember {
        mutableStateOf<ULong?>(null)
    }
    val borderColor = FinanceHelperTheme.colors.secondaryText
    val shape = FinanceHelperTheme.shape.shape10
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
                    if (selectedColor.value == it.value) Modifier.border(
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
                    .background(color = it, shape = FinanceHelperTheme.shape.shape20)
                    .clickAnimation {

                        if (it.value == selectedColor.value) {
                            Log.e(
                                TAG,
                                "ColorPicker: it.value == selectedColor.value ${it.value == selectedColor.value}"
                            )
                            selectedColor.value = null
                        } else {
                            selectedColor.value = it.value
                        }
                        onPick(selectedColor.value)
                    }
            )
        }
    }
}

@Composable
fun AddCategory(viewModel: AddCategoryViewModel, navController: NavController) {
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
            navController.popBackStack(
                route = Routes.CATEGORY_LIST,
                inclusive = false,
                saveState = false
            )
            navController.navigate(
                Routes.CATEGORY_LIST,
                navOptions = NavOptions.Builder().setLaunchSingleTop(true)
                    .setPopUpTo(route = Routes.CATEGORY_LIST, true).build()
            )
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



