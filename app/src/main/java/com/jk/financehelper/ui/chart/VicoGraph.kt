package com.jk.financehelper.ui.chart

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.jk.financehelper.domain.model.Transaction
import com.jk.financehelper.ui.theme.Celadon
import com.jk.financehelper.ui.theme.SeaGreen
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.component.shape.dashedShape
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.component.shape.DashedShape
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.component.text.VerticalPosition
import com.patrykandpatrick.vico.core.dimensions.MutableDimensions
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import java.time.LocalDateTime
import java.time.YearMonth
import java.util.SortedMap

// анимация вырастания столбцов
// при нажатии на столбец, столбец расширяется,
// образуя прямоугольную область в которой находится список транзакций
@Composable
fun VicoGraph(
    modifier: Modifier = Modifier,
    transactionList: List<Transaction>,
    chartDataType: ChartData,
    ) {
    val columnChart =
        columnChart(
            // если 1 эл то применяется на все
            columns = listOf(
                LineComponent(
                    color = Celadon.toArgb(),
                    thicknessDp = 15f,
                    shape = Shapes.roundedCornerShape(40),
                    margins = MutableDimensions(
                        bottomDp = 10f,
                        endDp = 0f,
                        startDp = 0f,
                        topDp = 0f
                    ),
                ),

            ),
            spacing = chartDataType.chartDataStyle.spacing,
            dataLabelVerticalPosition = VerticalPosition.Bottom,
            decorations = listOf(),
        )

    val chartEntryModelProducer = ChartEntryModelProducer()
    val localDateTime by remember {
        mutableStateOf(LocalDateTime.now())
    }

    val dateStringList: List<String> by remember {
        mutableStateOf(
            chartDataType.getStringItems(YearMonth.of(localDateTime.year, localDateTime.month))
        )
    }

    val barData: SortedMap<Int, Double> by remember() {
        mutableStateOf(
            ChartInfo.combine(
                chartDataType.getDateItems(localDateTime),
                transactionList,
                chartDataType
            )
        )
    }
    val chartData: List<FloatEntry> by remember {
        mutableStateOf(
            barData.map {
                entryOf(x = it.key, y = it.value)
            }
        )
    }

    val horizontalFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, _ ->
        dateStringList[value.toInt()]
    }
    val axisDataLabel by remember {
        mutableStateOf(
            TextComponent.Builder().apply {
                textSizeSp = chartDataType.chartDataStyle.textSize.value
                color = chartDataType.chartDataStyle.textColor.toArgb()
                margins = MutableDimensions(0f, 10f, 0f, 0f)
            }.build()
        )
    }
    val axisDataLabel2 by remember {
        mutableStateOf(
            TextComponent.Builder().apply {
                textSizeSp = chartDataType.chartDataStyle.textSize.value
                color = chartDataType.chartDataStyle.textColor.toArgb()
                margins = MutableDimensions(0f, 0f, 0f, 0f)
            }.build()
        )
    }
    chartEntryModelProducer.setEntries(chartData)

    Chart(
        horizontalLayout = com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout.FullWidth(
            10f
        ),
        modifier = modifier.padding(20.dp),
        chart = columnChart,
        model = chartEntryModelProducer.getModel()?: entryModelOf(0),
        startAxis = rememberStartAxis(
            axis = null,
            guideline = LineComponent(
                color = Color.Black.copy(alpha = 0.6f).toArgb(),
                shape = Shapes.dashedShape(
                    shape = Shapes.rectShape,
                    dashLength = 5.dp,
                    gapLength = 4.dp,
                    fitStrategy = DashedShape.FitStrategy.Resize
                ),
                margins = MutableDimensions(startDp = 10f, topDp = 0f, endDp = 0f, bottomDp = 0f)
            ),
            label = axisDataLabel2,
            itemPlacer = AxisItemPlacer.Vertical.default(
                maxItemCount = chartDataType.chartDataStyle.verticalAxisItemCount,
                shiftTopLines = true
            )
        ),
        bottomAxis = rememberBottomAxis(
            label = axisDataLabel,
            valueFormatter = horizontalFormatter,
            tick = LineComponent(
                color = SeaGreen.toArgb(),
                thicknessDp = 5f,
                shape = Shapes.roundedCornerShape(40),
                margins = MutableDimensions(startDp = 0f, topDp = 0f, endDp = 0f, bottomDp = 0f)
            ),
            tickLength = 10.dp,
            itemPlacer = AxisItemPlacer.Horizontal.default(
                1,
                offset = 10,
                shiftExtremeTicks = true,
                addExtremeLabelPadding = true
            ),
            guideline = null,
            axis = null
            //axis = LineComponent(margins = MutableDimensions(startDp = 10f, topDp = 0f, endDp = 0f, bottomDp = 40f), color= Color.Black.copy(alpha = 0.6f).toArgb())
        )
    )
}


