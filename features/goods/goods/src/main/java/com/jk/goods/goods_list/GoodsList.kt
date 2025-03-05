package com.jk.goods.goods_list

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.composable.Search
import com.jk.goods_common_ui.models.GoodsPreviewUI


@Composable
fun GoodsListScreen(viewModel: GoodsListViewModel,onAddClick:()->Unit) {
    val searchText = remember {
        mutableStateOf("")
    }
    val goodsList = viewModel.goodsListStateFlow.collectAsLazyPagingItems()
    viewModel.getGoodsList(q = searchText.value,"", isAsc = true)
    Scaffold(topBar = {
        Box(modifier = Modifier.fillMaxWidth()) {
            Search(
                modifier = Modifier.align(Alignment.CenterEnd),
                text = searchText.value,
                onValueChange = {
                    searchText.value = it
                }
            )
        }
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            onAddClick()
        }) {
            Image(imageVector = Icons.Default.Add,"zxc")
        }
    }) { paddings ->
        GoodsList(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = paddings.calculateStartPadding(LayoutDirection.Rtl),
                    end = paddings.calculateEndPadding(LayoutDirection.Rtl)
                ),
            goodList = goodsList
        )
    }

}


@Composable
fun GoodsList(modifier: Modifier = Modifier, goodList: LazyPagingItems<GoodsPreviewUI>) {
    when (goodList.loadState.refresh) {
        is LoadState.NotLoading -> {
          //  if (goodList.itemCount != 0)
                GoodsList(modifier = modifier, goodsList = goodList.itemSnapshotList.items)
        }

        is LoadState.Loading -> {
            CircularProgressIndicator(progress = { 1f })
        }

        is LoadState.Error -> {
            // todo error message
        }

    }
}

@Composable
fun GoodsList(
    modifier: Modifier = Modifier,
    goodsList: List<GoodsPreviewUI>,
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
    goodsList: GoodsPreviewUI,
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
            text = goodsList.cost.toString(),
            style = style ?: LocalTextStyle.current,
            maxLines = 1
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            text = goodsList.currency,
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
    FinanceHelperTheme {
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

@Preview
@Composable
fun GoodsScreenPreview() {
    MaterialTheme {
        GoodsListScreen(viewModel = hiltViewModel(),{})
    }
}

val test = listOf(
    GoodsPreviewUI(
        id = "gg",
        name = "fimojhgjhgjhgjhghjz",
        cost = 223.0,
        currency = "BYN"
    ),
    GoodsPreviewUI(
        id = "g3g",
        name = "fimojhgjhgjhgjhghjz",
        cost = 223.0,
        currency = "BYN"
    )
)