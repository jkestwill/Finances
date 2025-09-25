package com.jk.common_ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

/**
 * Данные колонки таблицы
 * @param key уникальный ключ колонки
 * @param title заголовок колонки
 * @param width начальная ширина колонки
 * @param minWidth минимальная ширина колонки
 * @param maxWidth максимальная ширина колонки
 * @param resizable может ли быть изменена ширина колонки
 */
data class TableColumn(
    val key: String,
    val title: String,
    val width: Dp = 100.dp,
    val minWidth: Dp = 50.dp,
    val maxWidth: Dp = 300.dp,
    val resizable: Boolean = true
)

/**
 * Данные строки таблицы
 * @param id уникальный идентификатор строки
 * @param data карта данных где ключ соответствует ключу колонки
 */
data class TableRow(
    val id: String,
    val data: Map<String, String>
)

/**
 * Состояние таблицы для управления ширинами колонок
 */
@Composable
fun rememberTableState(columns: List<TableColumn>): TableState {
    return remember(columns) {
        TableState(columns)
    }
}

/**
 * Класс для управления состоянием таблицы
 */
class TableState(private val initialColumns: List<TableColumn>) {
    private val _columnWidths = mutableStateMapOf<String, Dp>()
    
    init {
        initialColumns.forEach { column ->
            _columnWidths[column.key] = column.width
        }
    }
    
    fun getColumnWidth(columnKey: String): Dp {
        return _columnWidths[columnKey] ?: 100.dp
    }
    
    fun setColumnWidth(columnKey: String, width: Dp) {
        _columnWidths[columnKey] = width
    }
    
    fun getColumn(columnKey: String): TableColumn? {
        return initialColumns.find { it.key == columnKey }
    }
}

/**
 * Компонент изменяемой ширины колонки
 */
@Composable
private fun ResizableColumn(
    column: TableColumn,
    tableState: TableState,
    content: @Composable (Modifier) -> Unit
) {
    var currentWidth by rememberSaveable(column.key) {
        mutableStateOf(tableState.getColumnWidth(column.key))
    }
    
    val dragState = rememberDraggableState { delta ->
        if (column.resizable) {
            val newWidth = (currentWidth + delta.roundToInt().dp).coerceIn(
                column.minWidth,
                column.maxWidth
            )
            currentWidth = newWidth
            tableState.setColumnWidth(column.key, newWidth)
        }
    }
    
    Row(modifier = Modifier.width(currentWidth)) {
        Box(modifier = Modifier.weight(1f)) {
            content(Modifier.fillMaxSize())
        }
        
        if (column.resizable) {
            // Разделитель для изменения размера
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .draggable(
                        state = dragState,
                        orientation = Orientation.Horizontal
                    )
                    .background(Color.Gray.copy(alpha = 0.3f))
            ) {
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(Color.Gray)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

/**
 * Компонент таблицы с изменяемой шириной колонок
 * @param columns список колонок таблицы
 * @param rows список строк таблицы
 * @param modifier модификатор для таблицы
 * @param headerBackgroundColor цвет фона заголовков
 * @param rowBackgroundColor цвет фона строк
 * @param borderColor цвет границ
 * @param tableState состояние таблицы для управления ширинами колонок
 */
@Composable
fun ResizableTable(
    columns: List<TableColumn>,
    rows: List<TableRow>,
    modifier: Modifier = Modifier,
    headerBackgroundColor: Color = Color.Gray.copy(alpha = 0.2f),
    rowBackgroundColor: Color = Color.Transparent,
    borderColor: Color = Color.Gray.copy(alpha = 0.5f),
    tableState: TableState = rememberTableState(columns)
) {
    val lazyListState = rememberLazyListState()
    
    Column(
        modifier = modifier
            .border(1.dp, borderColor)
            .clipToBounds()
    ) {
        // Заголовок таблицы
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(headerBackgroundColor)
                .border(bottom = 1.dp, color = borderColor)
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            columns.forEach { column ->
                ResizableColumn(
                    column = column,
                    tableState = tableState
                ) { cellModifier ->
                    Box(
                        modifier = cellModifier
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = column.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
        }
        
        // Строки таблицы
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                items = rows,
                key = { it.id }
            ) { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(rowBackgroundColor)
                        .border(bottom = 1.dp, color = borderColor)
                        .height(40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    columns.forEach { column ->
                        ResizableColumn(
                            column = column,
                            tableState = tableState
                        ) { cellModifier ->
                            Box(
                                modifier = cellModifier
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    text = row.data[column.key] ?: "",
                                    fontSize = 12.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Start
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Простая версия таблицы с базовой настройкой
 */
@Composable
fun SimpleResizableTable(
    headers: List<String>,
    rows: List<List<String>>,
    modifier: Modifier = Modifier,
    initialColumnWidth: Dp = 120.dp,
    minColumnWidth: Dp = 80.dp,
    maxColumnWidth: Dp = 250.dp
) {
    val columns = headers.mapIndexed { index, header ->
        TableColumn(
            key = "column_$index",
            title = header,
            width = initialColumnWidth,
            minWidth = minColumnWidth,
            maxWidth = maxColumnWidth
        )
    }
    
    val tableRows = rows.mapIndexed { rowIndex, rowData ->
        val data = mutableMapOf<String, String>()
        rowData.forEachIndexed { colIndex, cellData ->
            if (colIndex < columns.size) {
                data[columns[colIndex].key] = cellData
            }
        }
        TableRow(id = "row_$rowIndex", data = data)
    }
    
    ResizableTable(
        columns = columns,
        rows = tableRows,
        modifier = modifier
    )
}