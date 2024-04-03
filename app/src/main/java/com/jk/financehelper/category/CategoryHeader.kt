package com.jk.financehelper.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import com.jk.financehelper.R
import com.jk.financehelper.ui.custom.AutoSizeText
import com.jk.financehelper.ui.theme.Red

@Composable
fun CategoryHeader(
    modifier: Modifier = Modifier,
    categoryName: String,
    description: String,
    icon: Painter,
    color: Color,
    lightColor: Color
) {
    val editIcon = painterResource(id = R.drawable.ic_edit)

    Row(
        modifier = modifier
            .background(color = lightColor, shape = RoundedCornerShape(20))
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(color = color, shape = RoundedCornerShape(20))
                .fillMaxHeight()
                .width(50.dp)
            //  .shadow(2.dp, shape = RoundedCornerShape(20))

        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = icon,
                contentDescription = "category_header_ic"
            )
        }
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

        Box(
            modifier = Modifier
                .shadow(2.dp, shape = RoundedCornerShape(20))
                .height(20.dp)
                .background(color = color, shape = RoundedCornerShape(20))
                .align(Alignment.Top)
                .weight(2f)
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(4.dp),
                painter = editIcon,
                contentDescription = "edit_ic"
            )
        }

    }
}

@Composable
@Preview
fun Preview() {
    CategoryHeader(
        modifier = Modifier
            .width(400.dp)
            .height(100.dp),
        categoryName = "Taxi",
        description = "All Transactions",
        icon = painterResource(id = R.drawable.ic_sort_down),
        color = Red,
        lightColor = Red
    )
}