package com.jk.goods

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jk.goods_common_ui.GoodsUI
import com.jk.goods_common_ui.LanguageUI
import com.jk.goods_common_ui.MeasureUI
import com.jk.goods_common_ui.SpecificationsUI
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyUI


// сделать просто списком в параметры передать лист убрать viewmodel


@Composable
fun GoodsList(
    modifier: Modifier = Modifier,
    goodsList: List<GoodsUI>,
    headerTextStyle: TextStyle? = null,
    itemsTextStyle: TextStyle? = null,
    headerList: List<String> = listOf(),
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(5.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (headerList.isNotEmpty())
            item {
                Row(
                    modifier = Modifier.background(
                        Color(0xFF1A936F),
                        shape = RoundedCornerShape(10)
                    )
                ) {
                    repeat(headerList.size) {
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .padding(5.dp),
                            text = headerList[it],
                            textAlign = TextAlign.Center,
                            style = headerTextStyle ?: LocalTextStyle.current
                        )
                    }
                }
            }
        items(goodsList.size) {
            GoodsItem(
                modifier = Modifier
                    .background(
                        color = Color(0xFF1A936F),
                        shape = RoundedCornerShape(10)
                    )
                    .padding(5.dp), goodsList = goodsList[it], style = itemsTextStyle
            )
        }
    }
}

@Composable
fun GoodsItem(
    modifier: Modifier = Modifier,
    goodsList: GoodsUI,
    style: TextStyle? = null
) {
    Row(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            text = goodsList.name,
            style = style ?: LocalTextStyle.current,
            maxLines = 2
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            text = goodsList.amount.toString(),
            style = style ?: LocalTextStyle.current,
            maxLines = 1
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            text = goodsList.cost.amount.toString(),
            style = style ?: LocalTextStyle.current,
            maxLines = 1
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            text = goodsList.cost.currency.name,
            style = style ?: LocalTextStyle.current,
            maxLines = 1
        )
    }

}

@Composable
fun VerticalTableListItem(
    modifier: Modifier = Modifier,
    rowTextList: List<String>,
    item: @Composable RowScope.(String) -> Unit
) {
    Row(modifier = modifier) {
        repeat(rowTextList.size) {
            item(this, rowTextList[it])
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE, device = "spec:parent=Nexus 5"
)
@Composable
fun GoodsListPreview() {
    MaterialTheme {
        GoodsList(
            modifier = Modifier
                .fillMaxWidth(),
            goodsList = test,
            headerTextStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            ),
            itemsTextStyle = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            ),
            headerList = listOf("Name", "Amount", "Cost", "Currency")
        )
    }
}

val test = listOf(
    GoodsUI(
        id = "gg",
        name = "fimojhgjhgjhgjhghjz",
        specifications = listOf(
            SpecificationsUI(
                id = "sp", text = "weight", amount = 2f,
                measure = MeasureUI(
                    "mm",
                    "kg"
                )
            )
        ),
        cost = MoneyUI("qq", 200000.0, CurrencyUI("zxc", "BYN")),
        amount = 1
    ),
    GoodsUI(
        id = "gg",
        name = "fimojhgjhgjhgjhghjz",
        specifications = listOf(
            SpecificationsUI(
                id = "sp", text = "weight", amount = 2f,
                measure = MeasureUI(
                    "mm",
                   "kg"
                )
            )
        ),
        cost = MoneyUI("qq", 200000.0, CurrencyUI("zxc", "BYN")),
        amount = 1
    ),
)