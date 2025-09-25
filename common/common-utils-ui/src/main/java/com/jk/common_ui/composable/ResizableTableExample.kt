package com.jk.common_ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Пример использования ResizableTable
 */
@Composable
fun ResizableTableExample(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Примеры таблиц с изменяемой шириной колонок",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        
        // Пример 1: Простая таблица
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Простая таблица",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                SimpleResizableTable(
                    headers = listOf("Имя", "Возраст", "Город", "Профессия"),
                    rows = listOf(
                        listOf("Иван Иванов", "25", "Москва", "Программист"),
                        listOf("Мария Петрова", "30", "Санкт-Петербург", "Дизайнер"),
                        listOf("Алексей Сидоров", "28", "Новосибирск", "Менеджер"),
                        listOf("Екатерина Смирнова", "32", "Екатеринбург", "Аналитик"),
                        listOf("Дмитрий Козлов", "26", "Казань", "Инженер")
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    initialColumnWidth = 120.dp,
                    minColumnWidth = 80.dp,
                    maxColumnWidth = 200.dp
                )
            }
        }
        
        // Пример 2: Настраиваемая таблица
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Настраиваемая таблица",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                val columns = listOf(
                    TableColumn(
                        key = "id",
                        title = "ID",
                        width = 60.dp,
                        minWidth = 40.dp,
                        maxWidth = 100.dp
                    ),
                    TableColumn(
                        key = "product",
                        title = "Товар",
                        width = 150.dp,
                        minWidth = 100.dp,
                        maxWidth = 250.dp
                    ),
                    TableColumn(
                        key = "price",
                        title = "Цена",
                        width = 80.dp,
                        minWidth = 60.dp,
                        maxWidth = 120.dp
                    ),
                    TableColumn(
                        key = "quantity",
                        title = "Количество",
                        width = 90.dp,
                        minWidth = 70.dp,
                        maxWidth = 130.dp
                    ),
                    TableColumn(
                        key = "total",
                        title = "Сумма",
                        width = 100.dp,
                        minWidth = 80.dp,
                        maxWidth = 150.dp,
                        resizable = false // Эта колонка не изменяется
                    )
                )
                
                val rows = listOf(
                    TableRow(
                        id = "1",
                        data = mapOf(
                            "id" to "001",
                            "product" to "Ноутбук",
                            "price" to "50000₽",
                            "quantity" to "2",
                            "total" to "100000₽"
                        )
                    ),
                    TableRow(
                        id = "2",
                        data = mapOf(
                            "id" to "002",
                            "product" to "Мышь",
                            "price" to "1500₽",
                            "quantity" to "5",
                            "total" to "7500₽"
                        )
                    ),
                    TableRow(
                        id = "3",
                        data = mapOf(
                            "id" to "003",
                            "product" to "Клавиатура",
                            "price" to "3000₽",
                            "quantity" to "3",
                            "total" to "9000₽"
                        )
                    ),
                    TableRow(
                        id = "4",
                        data = mapOf(
                            "id" to "004",
                            "product" to "Монитор",
                            "price" to "25000₽",
                            "quantity" to "1",
                            "total" to "25000₽"
                        )
                    )
                )
                
                ResizableTable(
                    columns = columns,
                    rows = rows,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    headerBackgroundColor = Color(0xFFE3F2FD),
                    rowBackgroundColor = Color.White,
                    borderColor = Color(0xFF90CAF9)
                )
            }
        }
        
        // Пример 3: Таблица с большим количеством данных
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Таблица с большим количеством данных",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                val bigDataRows = (1..50).map { index ->
                    listOf(
                        "User$index",
                        "${20 + (index % 40)}",
                        "user$index@example.com",
                        "Department${(index % 5) + 1}",
                        if (index % 2 == 0) "Активен" else "Неактивен"
                    )
                }
                
                SimpleResizableTable(
                    headers = listOf("Пользователь", "Возраст", "Email", "Отдел", "Статус"),
                    rows = bigDataRows,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    initialColumnWidth = 140.dp,
                    minColumnWidth = 100.dp,
                    maxColumnWidth = 220.dp
                )
            }
        }
        
        Text(
            text = "Инструкция: Перетащите границы колонок для изменения их ширины",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResizableTableExamplePreview() {
    MaterialTheme {
        ResizableTableExample()
    }
}