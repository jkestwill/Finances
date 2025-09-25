package com.jk.financehelper.ui.table

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableDemoScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: (() -> Unit)? = null
) {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Заголовок экрана
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            onNavigateBack?.let {
                TextButton(onClick = it) {
                    Text("← Назад")
                }
            }
            
            Text(
                text = "Демо: Таблица с изменяемыми колонками",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = if (onNavigateBack != null) 8.dp else 0.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Описание
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Инструкция:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Перетаскивайте правый край заголовков колонок для изменения их ширины\n" +
                            "• Минимальная ширина: 50dp, максимальная: 300dp\n" +
                            "• Таблица поддерживает прокрутку для большого количества данных\n" +
                            "• Строки имеют чередующийся цвет для лучшей читаемости",
                    fontSize = 14.sp
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Таблица с финансовыми данными
        TableDemoContent()
    }
}

@Composable
private fun TableDemoContent() {
    val columns = remember {
        listOf(
            TableColumn("id", "ID", 60.dp, 40.dp, 100.dp),
            TableColumn("date", "Дата", 120.dp, 80.dp, 150.dp),
            TableColumn("description", "Описание", 200.dp, 100.dp, 300.dp),
            TableColumn("category", "Категория", 130.dp, 80.dp, 200.dp),
            TableColumn("amount", "Сумма", 100.dp, 80.dp, 150.dp),
            TableColumn("type", "Тип", 90.dp, 60.dp, 120.dp)
        )
    }
    
    val rows = remember {
        generateSampleFinancialData()
    }
    
    var selectedColumnInfo by remember { mutableStateOf<String?>(null) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Финансовые транзакции",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            ResizableTable(
                columns = columns,
                rows = rows,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                onColumnWidthChanged = { columnKey, newWidth ->
                    selectedColumnInfo = "Колонка '$columnKey' изменена на ${newWidth.value.toInt()}dp"
                }
            )
            
            // Информация об изменениях
            selectedColumnInfo?.let { info ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = info,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

private fun generateSampleFinancialData(): List<TableRow> {
    val categories = listOf(
        "Продукты", "Транспорт", "Развлечения", "Коммунальные", 
        "Здоровье", "Образование", "Одежда", "Техника", "Путешествия", "Прочее"
    )
    
    val descriptions = listOf(
        "Покупка продуктов в супермаркете",
        "Заправка автомобиля на АЗС",
        "Билеты в кинотеатр",
        "Оплата электричества",
        "Покупка лекарств в аптеке",
        "Оплата курсов программирования",
        "Покупка зимней куртки",
        "Новый смартфон Samsung",
        "Отпуск в Турции",
        "Подарок на день рождения",
        "Ремонт велосипеда",
        "Стрижка в парикмахерской",
        "Покупка книг в магазине",
        "Оплата интернета",
        "Обед в ресторане",
        "Покупка спортивной обуви",
        "Билеты на концерт",
        "Оплата страховки",
        "Покупка цветов",
        "Такси до аэропорта"
    )
    
    val types = listOf("Расход", "Доход")
    val random = Random()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    
    return (1..50).map { index ->
        val isIncome = random.nextBoolean() && random.nextFloat() < 0.2f // 20% chance for income
        val amount = if (isIncome) {
            (5000..50000).random()
        } else {
            (100..15000).random()
        }
        
        val date = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -random.nextInt(365))
        }.time
        
        TableRow(
            id = index.toString(),
            data = mapOf(
                "id" to index.toString(),
                "date" to dateFormat.format(date),
                "description" to descriptions.random(),
                "category" to categories.random(),
                "amount" to if (isIncome) "+$amount" else "-$amount",
                "type" to if (isIncome) "Доход" else "Расход"
            )
        )
    }.sortedByDescending { it.data["date"] }
}