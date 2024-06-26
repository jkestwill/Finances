package com.jk.financehelper.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jk.category.CategoryViewModel
import com.jk.financehelper.R
import com.jk.financehelper.domain.model.preview.TransactionPreview
import com.jk.financehelper.ui.common.ButtonWithDropdownMenu
import com.jk.financehelper.ui.custom.AutoSizeText
import com.jk.financehelper.ui.theme.Celadon
import com.jk.financehelper.ui.theme.FinanceHelperTheme
import com.jk.financehelper.ui.theme.Red
import com.jk.financehelper.ui.theme.colorPickList
import com.jk.financehelper.utils.DataUtils


@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel? = null,
    navController: NavController,
    categoryId: String?,
    colorValue: ULong?
) {
    categoryId?.let {
        viewModel?.getById(it)
    }
    val color = remember(colorValue) {
        mutableStateOf(Color(colorValue ?: Celadon.toArgb().toULong()))
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
                    .shadow(2.dp, shape = FinanceHelperTheme.shape.shape10),
                categoryName = "Taxi",
                description = "All Transactions",
                icon = painterResource(id = R.drawable.ic_sort_down),
                color = color.value,
                lightColor = lightColor.value
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .shadow(2.dp, FinanceHelperTheme.shape.shape10)
                    .background(
                        color = lightColor.value, FinanceHelperTheme.shape.shape10
                    )
                    .padding(10.dp), verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                ButtonWithDropdownMenu(modifier = Modifier
                    .shadow(4.dp, RoundedCornerShape(20))
                    .background(
                        color = color.value, shape = FinanceHelperTheme.shape.shape10
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
                        color = color.value, FinanceHelperTheme.shape.shape10
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
            .background(lightColor, FinanceHelperTheme.shape.shape20)
            .padding(start = 10.dp, end = 10.dp)
            .height(60.dp)
            .padding(5.dp)

    ) {

        Column(
            modifier = Modifier
                .weight(0.5f)
                .padding(5.dp)
                .align(Alignment.CenterVertically)
        ) {
            AutoSizeText(
                modifier = Modifier.weight(1f),
                text = item.operation.name,
                minTextSize = (FinanceHelperTheme.typography.h3.fontSize.value - 5).sp,
                maxTextSize = FinanceHelperTheme.typography.h1.fontSize,
                maxLines = 1,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "${item.date.hour}:${item.date.minute}",
                style = FinanceHelperTheme.typography.h4,
                textAlign = TextAlign.Start
            )
        }
        Box(
            modifier = Modifier
                .weight(0.3f)
                .align(Alignment.CenterVertically)
                .height(40.dp)
                .background(
                    color = color, shape = FinanceHelperTheme.shape.shape20
                )

        ) {
            AutoSizeText(
                modifier = Modifier.align(Alignment.Center),
                text = "${item.operation.money.amount} ${item.operation.money.currency.name}",
                minTextSize = (FinanceHelperTheme.typography.h3.fontSize.value - 15).sp,
                maxTextSize = FinanceHelperTheme.typography.h3.fontSize,
                alignment = Alignment.Center,
                maxLines = 1
            )
        }
    }
}

@Composable
@Preview
fun CategoryScreenPreview() {
    FinanceHelperTheme {
        CategoryScreen(
            navController = rememberNavController(),
            categoryId = "zxc",
            colorValue = colorPickList[3].value
        )
    }
}