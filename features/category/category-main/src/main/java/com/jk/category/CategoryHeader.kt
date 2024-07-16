package com.jk.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jk.common_ui.composable.AutoSizeText


@Composable
fun CategoryHeader(
    modifier: Modifier = Modifier,
    categoryName: String,
    description: String,
    color: Color,
    lightColor: Color,
    onSearch: ((String) -> Unit)? = null
) {
    Row(
        modifier = modifier
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(8f)
        ) {

            AutoSizeText(
                text = categoryName,
                maxTextSize = 24.sp,
                minTextSize = 20.sp,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = description,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start
            )
        }


    }
}

@Composable
@Preview(backgroundColor = 0xFFFFFFFF)
fun Preview() {
    com.jk.common_ui.FinanceHelperTheme {
        CategoryHeader(
            modifier = Modifier
                .width(400.dp)
                .height(100.dp),
            categoryName = "Taxi",
            description = "All Transactions",
            color = com.jk.common_ui.Red,
            lightColor = com.jk.common_ui.Red
        )
    }
}