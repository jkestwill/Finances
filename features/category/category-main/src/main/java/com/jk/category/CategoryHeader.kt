package com.jk.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.Red
import com.jk.common_ui.composable.AutoSizeText
import com.jk.common_ui.composable.Search
import com.jk.common_ui.loadingAnimation
import com.jk.shared_res.R

private val emptyTextModifier = Modifier
    .width(150.dp)


@Composable
fun CategoryHeader(
    modifier: Modifier = Modifier,
    categoryName: String,
    description: String,
    color: Color,
    onSearchTranslationY:((Float)->Unit)?=null,
    onSearch: ((String) -> Unit)? = null
) {
    val searchText = remember {
        mutableStateOf("")
    }
    val loadingModifier = remember {
        mutableStateOf(
            if (categoryName == "") emptyTextModifier.loadingAnimation(
                isVisible = categoryName == "",
                color = color
            ) else Modifier
        )
    }
    Box(modifier=Modifier.fillMaxWidth()) {
        Row(
            modifier = modifier
                .padding(10.dp)
                .align(Alignment.CenterStart),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(5.dp)

            ) {

                AutoSizeText(
                    modifier = loadingModifier.value,
                    text = categoryName,
                    maxTextSize = 24.sp,
                    minTextSize = 20.sp,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    modifier = loadingModifier.value,
                    text = description,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start
                )
            }
        }

        Search(
            modifier = Modifier.align(Alignment.Center).onGloballyPositioned {
                if (onSearchTranslationY != null) {
                    onSearchTranslationY(it.positionInRoot().y)
                }
            },
            color=color,
            text = searchText.value,
            onValueChange = onSearch ?: { searchText.value = it })
    }
}

@Composable
@Preview(backgroundColor = 0xFFFFFFFF)
fun Preview() {
    FinanceHelperTheme {
        CategoryHeader(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            categoryName = "Taxi",
            description = "All Transactions",
            color = Red,
        )
    }
}