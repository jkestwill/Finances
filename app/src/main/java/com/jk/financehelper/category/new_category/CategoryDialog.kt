package com.jk.financehelper.category.new_category

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.jk.category.AddCategoryViewModel
import com.jk.financehelper.navigation.Routes
import com.jk.financehelper.ui.theme.FinanceHelperTheme
import com.jk.common_data.State

@Composable
fun CategoryDialog(viewModel: AddCategoryViewModel, navController: NavController) {
    val name = remember {
        mutableStateOf("")
    }
    val isColorPopupVisible = remember {
        mutableStateOf(false)
    }
    val colorHex = remember {
        mutableStateOf(Color(0xFF000000))
    }

    val isExpensesSwitch = remember {
        mutableStateOf(false)
    }
    AddCategory(viewModel = viewModel, navController =navController )

    val nameError = viewModel.newCategoryNameErr.collectAsState()
    Log.e("zxc", "CategoryDialog: $nameError")
    Dialog(onDismissRequest = {
        navController.popBackStack()
    }) {
        Box() {
            Column(
                modifier = Modifier
                    .clip(FinanceHelperTheme.shape.shape)
                    .background(FinanceHelperTheme.colors.secondaryBackground)
                    .height(250.dp)
                    .padding(FinanceHelperTheme.shape.padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Add new category",
                    style = FinanceHelperTheme.typography.label
                )
                Row(modifier = Modifier.height(100.dp)) {
                    EditField(
                        modifier = Modifier.weight(1f),
                        text = name.value,
                        placeHolderText = "Name",
                        errorText = nameError.value,
                        isError = nameError.value.isNotBlank()
                    ) {
                        name.value = it
                    }
                    Column(
                        modifier = Modifier
                            .weight(0.5f)
                            .align(Alignment.Top)
                    ) {
                        Box(modifier = Modifier
                            .drawBehind {
                                Log.e("qwe", "CategoryDialog:${colorHex.value} ")
                                drawRect(
                                    color = colorHex.value,
                                )
                            }
                            .width(60.dp)
                            .fillMaxHeight()
                            .clickable {
                                isColorPopupVisible.value = true
                            }
                            .align(Alignment.CenterHorizontally)
                        )
                        if (isColorPopupVisible.value)
                            ColorPopup(onColorChange = {
                                colorHex.value = it
                            }, initialColor = colorHex) {
                                isColorPopupVisible.value = false
                            }
                    }
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(FinanceHelperTheme.shape.padding),
                        text = "Expenses",
                        style = FinanceHelperTheme.typography.body
                    )
                    Switch(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        checked = isExpensesSwitch.value,
                        onCheckedChange = {
                            isExpensesSwitch.value = it
                        })
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(FinanceHelperTheme.shape.padding)
            ) {
                Box(modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.addCategory(
                            name = name.value,
                            color = colorHex.value.value,
                            isExpenses = false
                        )
//                        navController.popBackStack(
//                            route = Routes.CATEGORY_LIST,
//                            inclusive = false,
//                            saveState = false
//                        )

                    }) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Create",
                        fontSize = 16.sp
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
                        modifier = Modifier.align(Alignment.Center),
                        text = "Cancel",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }

}

@Composable
fun AddCategory(viewModel: AddCategoryViewModel, navController: NavController) {
    val state = viewModel.addCategoryResponse.collectAsState()
    val context = LocalContext.current
    when (state.value) {
        is State.None -> {Log.e("qqs", "AddCategory:NONE")}
        is State.Loading -> {Log.e("qqs", "AddCategory:LOADING")}
        is State.Success -> {
            Log.e("qqs", "AddCategory:Success")
            navController.popBackStack(
                route = Routes.CATEGORY_LIST,
                inclusive = false,
                saveState = false
            )
        }

        is State.Error -> {
            Log.e("qqs", "AddCategory:ERROR")
            Toast.makeText(
                context,
                "${(state.value as State.Error<Long>).message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

@Composable
fun ColorPicker(controller: ColorPickerController, onColorChange: (color: Color) -> Unit) {
    HsvColorPicker(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        controller = controller,
        onColorChanged = { colorEnvelope: ColorEnvelope ->

            onColorChange(colorEnvelope.color)
        }
    )
}

@SuppressLint("RememberReturnType")
@Composable
fun ColorPopup(
    onColorChange: (color: Color) -> Unit,
    initialColor: androidx.compose.runtime.State<Color>,
    onDismiss: () -> Unit
) {
    val colorHex = remember {
        mutableStateOf("#${initialColor.value.value.toString(16).substring(0, 8)}")
    }
    val controller = rememberColorPickerController().apply {

    }

    remember {
        controller.selectByColor(initialColor.value, false)
        controller.setWheelRadius(4.dp)
    }
    Popup(
        onDismissRequest = onDismiss,
        offset = IntOffset(170, 0),
        alignment = Alignment.BottomStart
    ) {
        Box(
            modifier = Modifier
                .clip(FinanceHelperTheme.shape.shape)
                .background(FinanceHelperTheme.colors.secondaryBackground)
                .height(200.dp)
                .width(150.dp)
                .padding(FinanceHelperTheme.shape.padding)
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clickable {
                        onDismiss()
                    },
                imageVector = Icons.Filled.Close,
                contentDescription = "close_ic"
            )
            Column(

            ) {
                Text(text = "Choose color", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                Text(text = colorHex.value, fontSize = 12.sp)
                ColorPicker(controller = controller) {
                    onColorChange(it)
                    colorHex.value = "#${it.value.toString(16).substring(0, 8)}"
                }

            }
        }
    }
}


@Composable
fun EditField(
    modifier: Modifier = Modifier,
    text: String,
    placeHolderText: String,
    isError: Boolean = false,
    errorText: String = "",
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier
            .fillMaxWidth(),
        value = text,
        singleLine = true,
        textStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
        onValueChange = onValueChange,
        supportingText = {
            if (isError)
                Text(modifier = Modifier.fillMaxSize(), text = errorText, maxLines = 2)
        },
        placeholder = {
            Text(
                text = placeHolderText,
                fontSize = 16.sp,
                style = TextStyle(brush = null, alpha = 0.5f)
            )
        },
        isError = isError
    )
}