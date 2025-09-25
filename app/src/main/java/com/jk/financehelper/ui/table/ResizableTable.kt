package com.jk.financehelper.ui.table

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Данные для колонки таблицы
 */
data class TableColumn<T>(
    val key: String,
    val title: String,
    val initialWidth: Dp = 120.dp,
    val minWidth: Dp = 50.dp,
    val maxWidth: Dp = 300.dp,
    val formatter: (T?) -> String = { it?.toString() ?: "" }
)

/**
 * Данные для строки таблицы с типизированными данными
 */
data class TableRow<T>(
    val id: String,
    val data: Map<String, T>
)

/**
 * Состояние размеров колонок
 */
@Composable
fun <T> rememberColumnWidthState(columns: List<TableColumn<T>>) = remember(columns) {
    mutableStateMapOf<String, Dp>().apply {
        columns.forEach { column ->
            this[column.key] = column.initialWidth
        }
    }
}

/**
 * Компонент таблицы с изменяемой шириной колонок
 */
@Composable
fun <T> ResizableTable(
    columns: List<TableColumn<T>>,
    rows: List<TableRow<T>>,
    modifier: Modifier = Modifier,
    headerBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    rowBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    alternateRowBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
    borderColor: Color = MaterialTheme.colorScheme.outline,
    onColumnWidthChanged: ((String, Dp) -> Unit)? = null
) {
    val columnWidths = rememberColumnWidthState(columns)
    val density = LocalDensity.current
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clipToBounds()
    ) {
        // Заголовок таблицы
        TableHeader(
            columns = columns,
            columnWidths = columnWidths,
            backgroundColor = headerBackgroundColor,
            borderColor = borderColor,
            onColumnResize = { columnKey, newWidth ->
                val column = columns.find { it.key == columnKey }
                if (column != null) {
                    val constrainedWidth = newWidth.coerceIn(column.minWidth, column.maxWidth)
                    columnWidths[columnKey] = constrainedWidth
                    onColumnWidthChanged?.invoke(columnKey, constrainedWidth)
                }
            }
        )
        
        // Тело таблицы
        TableBody(
            columns = columns,
            rows = rows,
            columnWidths = columnWidths,
            rowBackgroundColor = rowBackgroundColor,
            alternateRowBackgroundColor = alternateRowBackgroundColor,
            borderColor = borderColor
        )
    }
}

@Composable
private fun <T> TableHeader(
    columns: List<TableColumn<T>>,
    columnWidths: Map<String, Dp>,
    backgroundColor: Color,
    borderColor: Color,
    onColumnResize: (String, Dp) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .border(1.dp, borderColor)
    ) {
        columns.forEachIndexed { index, column ->
            val width = columnWidths[column.key] ?: column.initialWidth
            
            Box(
                modifier = Modifier
                    .width(width)
                    .height(48.dp)
                    .border(
                        width = if (index < columns.size - 1) 0.dp else 0.dp,
                        color = borderColor
                    )
            ) {
                Text(
                    text = column.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .wrapContentHeight(Alignment.CenterVertically),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                
                // Разделитель для изменения размера (только если не последняя колонка)
                if (index < columns.size - 1) {
                    ColumnResizeHandle(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        onDrag = { dragAmount ->
                            val newWidth = width + dragAmount
                            onColumnResize(column.key, newWidth)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun <T> TableBody(
    columns: List<TableColumn<T>>,
    rows: List<TableRow<T>>,
    columnWidths: Map<String, Dp>,
    rowBackgroundColor: Color,
    alternateRowBackgroundColor: Color,
    borderColor: Color
) {
    LazyColumn(
        state = rememberLazyListState()
    ) {
        items(rows) { row ->
            val rowIndex = rows.indexOf(row)
            val backgroundColor = if (rowIndex % 2 == 0) rowBackgroundColor else alternateRowBackgroundColor
            
            TableRowItem(
                columns = columns,
                row = row,
                columnWidths = columnWidths,
                backgroundColor = backgroundColor,
                borderColor = borderColor
            )
        }
    }
}

@Composable
private fun <T> TableRowItem(
    columns: List<TableColumn<T>>,
    row: TableRow<T>,
    columnWidths: Map<String, Dp>,
    backgroundColor: Color,
    borderColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .border(0.5.dp, borderColor)
    ) {
        columns.forEach { column ->
            val width = columnWidths[column.key] ?: column.initialWidth
            val cellValue = row.data[column.key]
            val cellData = column.formatter(cellValue)
            
            Box(
                modifier = Modifier
                    .width(width)
                    .height(40.dp)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = cellData,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight(Alignment.CenterVertically),
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun ColumnResizeHandle(
    modifier: Modifier = Modifier,
    onDrag: (Dp) -> Unit
) {
    val density = LocalDensity.current
    
    Box(
        modifier = modifier
            .width(8.dp)
            .fillMaxHeight()
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val dragAmountDp = with(density) { change.x.toDp() }
                    onDrag(dragAmountDp)
                }
            }
            .background(Color.Transparent)
    ) {
        // Визуальный индикатор для ручки изменения размера
        Box(
            modifier = Modifier
                .width(2.dp)
                .fillMaxHeight()
                .align(Alignment.Center)
                .background(Color.Gray.copy(alpha = 0.3f))
        )
    }
}

/**
 * Предварительный просмотр компонента с типизированными данными
 */
@Composable
fun ResizableTablePreview() {
    val sampleColumns = listOf(
        TableColumn<String>("id", "ID", 60.dp),
        TableColumn<String>("name", "Название", 150.dp),
        TableColumn<String>("amount", "Сумма", 100.dp),
        TableColumn<String>("date", "Дата", 120.dp),
        TableColumn<String>("category", "Категория", 130.dp)
    )
    
    val sampleRows = listOf(
        TableRow("1", mapOf(
            "id" to "1",
            "name" to "Покупка продуктов",
            "amount" to "1500.00",
            "date" to "2024-01-15",
            "category" to "Еда"
        )),
        TableRow("2", mapOf(
            "id" to "2",
            "name" to "Заправка автомобиля",
            "amount" to "3200.50",
            "date" to "2024-01-14",
            "category" to "Транспорт"
        )),
        TableRow("3", mapOf(
            "id" to "3",
            "name" to "Покупка книг",
            "amount" to "750.00",
            "date" to "2024-01-13",
            "category" to "Образование"
        )),
        TableRow("4", mapOf(
            "id" to "4",
            "name" to "Оплата коммунальных услуг",
            "amount" to "8500.00",
            "date" to "2024-01-12",
            "category" to "Коммунальные платежи"
        )),
        TableRow("5", mapOf(
            "id" to "5",
            "name" to "Поход в кино",
            "amount" to "1200.00",
            "date" to "2024-01-11",
            "category" to "Развлечения"
        ))
    )
    
    ResizableTable(
        columns = sampleColumns,
        rows = sampleRows,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onColumnWidthChanged = { columnKey, newWidth ->
            println("Column $columnKey resized to $newWidth")
        }
    )
}