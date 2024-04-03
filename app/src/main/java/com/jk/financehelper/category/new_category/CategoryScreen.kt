package com.jk.financehelper.category.new_category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import androidx.navigation.NavController
import com.jk.category.CategoryListViewModel
import com.jk.financehelper.R
import com.jk.financehelper.category.CategoryHeader
import com.jk.financehelper.domain.model.preview.TransactionPreview
import com.jk.financehelper.ui.common.ButtonWithDropdownMenu
import com.jk.financehelper.ui.custom.AutoSizeText
import com.jk.financehelper.ui.theme.Celadon
import com.jk.financehelper.ui.theme.FinanceHelperTheme
import com.jk.financehelper.ui.theme.Red
import com.jk.financehelper.utils.DataUtils


@Composable
fun CategoryScreen(
    viewModel: CategoryListViewModel,
    navController: NavController,
    categoryId: String?,
    colorInt: Long?
) {
    categoryId?.let {
        viewModel.getById(it)
    }
    val color = remember(colorInt) {
        mutableStateOf(Color(colorInt ?: Celadon.toArgb().toLong()))
    }
    val sortIcon = painterResource(id = R.drawable.ic_sort_down)
    val lightColor = remember(color) {
        mutableStateOf(
            Color(
                ColorUtils.blendARGB(
                    color.value.toArgb(), Color.White.toArgb(), 0.3f
                )
            )
        )
    }
    Scaffold(contentWindowInsets = WindowInsets(15.dp, 20.dp, 5.dp, 5.dp), topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(5.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(0.1f)
                    .clickable {
                        navController.popBackStack()
                    },
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "zxc"
            )
            CategoryHeader(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .weight(0.6f)
                    .shadow(2.dp, shape = FinanceHelperTheme.shape.shape),
                categoryName = "Taxi",
                description = "All Transactions",
                icon = painterResource(id = R.drawable.ic_sort_down),
                color = color.value,
                lightColor = lightColor.value
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .shadow(2.dp, FinanceHelperTheme.shape.shape)
                    .background(
                        color = lightColor.value, FinanceHelperTheme.shape.shape
                    )
                    .padding(10.dp), verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                ButtonWithDropdownMenu(modifier = Modifier
                    .shadow(4.dp, RoundedCornerShape(20))
                    .background(
                        color = color.value,shape= FinanceHelperTheme.shape.shape
                    )
                    .weight(0.2f),
                    list = listOf("Date", "Name", "Amount"),
                    color = Red,
                    icon = sortIcon,
                    onClick = {
                        println(it)
                    })
                ButtonWithDropdownMenu(modifier = Modifier
                    .weight(0.2f)
                    .shadow(4.dp, RoundedCornerShape(20))
                    .background(
                        color = color.value, FinanceHelperTheme.shape.shape
                    ),
                    list = listOf("Date", "Name", "Amount"),
                    color = Red,
                    icon = sortIcon,
                    onClick = {
                        println(it)
                    })
            }
        }
    }, floatingActionButton = {
        FloatingActionButton(onClick = {

        }, contentColor = Celadon) {
            Icon(imageVector = Icons.Filled.Edit, contentDescription = "edit_ic")
        }
    }) {
        Column(
            modifier = Modifier.padding(
                top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()
            ), verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TransactionList(
                transactionList = DataUtils.getTransactionPreview(), color.value, lightColor.value
            )
        }
    }
}

@Composable
fun TransactionList(transactionList: List<TransactionPreview>, color: Color, lightColor: Color) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(start = 5.dp, end = 5.dp, top = 20.dp)
    ) {
        items(transactionList) {
            TransactionPreviewItem(item = it, color = color, lightColor = lightColor)
        }
    }
}

@Composable
fun TransactionPreviewItem(item: TransactionPreview, color: Color, lightColor: Color) {
    Row(
        Modifier
            .clip(RoundedCornerShape(20))
            .padding(end = 5.dp)
            .drawBehind {
                drawRoundRect(
                    topLeft = Offset(50f, 0f),
                    size = Size(width = size.width, height = size.height),
                    color = lightColor,
                    alpha = 0.6f,
                    cornerRadius = CornerRadius(10f),
                )


            }
            .height(60.dp)

    ) {
        Spacer(modifier = Modifier.weight(0.1f))
        Column(
            modifier = Modifier
                .weight(0.5f)
                .padding(10.dp)
                .align(Alignment.CenterVertically)
        ) {
            AutoSizeText(
                modifier = Modifier.weight(1f),
                text = item.operation.name,
                minTextSize = 20.sp,
                maxTextSize = 25.sp,
                maxLines = 1,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "${item.date.hour}:${item.date.minute}",
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
        }
        Box(
            modifier = Modifier
                .weight(0.3f)
                .align(Alignment.CenterVertically)
                .height(40.dp)
                .background(
                    color, shape = RoundedCornerShape(20)
                )

        ) {
            AutoSizeText(
                modifier = Modifier.align(Alignment.Center),
                text = "${item.operation.money.amount} ${item.operation.money.currency.name}",
                minTextSize = 15.sp,
                maxTextSize = 20.sp,
                alignment = Alignment.Center,
                maxLines = 1
            )
        }

    }
}