package com.jk.financehelper.ui.table

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
                            "• Строки имеют чередующийся цвет для лучшей читаемости\n" +
                            "• Поддержка дженериков и пользовательских форматтеров",
                    fontSize = 14.sp
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Таблица с типизированными финансовыми данными
        TypedTableDemoContent()
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Таблица с примитивными типами для сравнения
        StringTableDemoContent()
    }
}

// Модель данных для типизированной таблицы
data class FinancialTransaction(
    val id: Long,
    val date: LocalDate,
    val description: String,
    val category: String,
    val amount: BigDecimal,
    val isIncome: Boolean
)

@Composable
private fun TypedTableDemoContent() {
    val decimalFormat = DecimalFormat("#,##0.00")
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    
    val columns = remember {
        listOf(
            TableColumn<Long>(
                key = "id",
                title = "ID",
                initialWidth = 60.dp,
                minWidth = 40.dp,
                maxWidth = 100.dp,
                formatter = { it.toString() }
            ),
            TableColumn<LocalDate>(
                key = "date",
                title = "Дата",
                initialWidth = 120.dp,
                minWidth = 80.dp,
                maxWidth = 150.dp,
                formatter = { it?.format(dateFormatter) ?: "" }
            ),
            TableColumn<String>(
                key = "description",
                title = "Описание",
                initialWidth = 200.dp,
                minWidth = 100.dp,
                maxWidth = 300.dp
            ),
            TableColumn<String>(
                key = "category",
                title = "Категория",
                initialWidth = 130.dp,
                minWidth = 80.dp,
                maxWidth = 200.dp
            ),
            TableColumn<BigDecimal>(
                key = "amount",
                title = "Сумма",
                initialWidth = 120.dp,
                minWidth = 80.dp,
                maxWidth = 150.dp,
                formatter = { amount ->
                    amount?.let { decimalFormat.format(it) } ?: "0.00"
                }
            ),
            TableColumn<Boolean>(
                key = "isIncome",
                title = "Тип",
                initialWidth = 90.dp,
                minWidth = 60.dp,
                maxWidth = 120.dp,
                formatter = { if (it == true) "Доход" else "Расход" }
            )
        )
    }
    
    val transactions = remember { generateTypedFinancialData() }
    
    val rows = remember(transactions) {
        transactions.map { transaction ->
            TableRow(
                id = transaction.id.toString(),
                data = mapOf<String, Any?>(
                    "id" to transaction.id,
                    "date" to transaction.date,
                    "description" to transaction.description,
                    "category" to transaction.category,
                    "amount" to transaction.amount,
                    "isIncome" to transaction.isIncome
                )
            )
        }
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
                text = "Типизированная таблица (с дженериками)",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                text = "Поддерживает BigDecimal, LocalDate, Boolean и пользовательские форматтеры",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            ResizableTable(
                columns = columns,
                rows = rows,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
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

@Composable
private fun StringTableDemoContent() {
    val columns = remember {
        listOf(
            TableColumn<String>("id", "ID", 60.dp, 40.dp, 100.dp),
            TableColumn<String>("date", "Дата", 120.dp, 80.dp, 150.dp),
            TableColumn<String>("description", "Описание", 200.dp, 100.dp, 300.dp),
            TableColumn<String>("category", "Категория", 130.dp, 80.dp, 200.dp),
            TableColumn<String>("amount", "Сумма", 100.dp, 80.dp, 150.dp),
            TableColumn<String>("type", "Тип", 90.dp, 60.dp, 120.dp)
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
                text = "Строковая таблица (обычный подход)",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                text = "Все данные хранятся как строки",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            ResizableTable(
                columns = columns,
                rows = rows,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
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

private fun generateTypedFinancialData(): List<FinancialTransaction> {
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
        "Подарок на день рождения"
    )
    
    val random = Random()
    
    return (1..25).map { index ->
        val isIncome = random.nextFloat() < 0.3f // 30% chance for income
        val amount = if (isIncome) {
            BigDecimal((5000..50000).random())
        } else {
            BigDecimal((100..15000).random())
        }
        
        val date = LocalDate.now().minusDays(random.nextInt(365).toLong())
        
        FinancialTransaction(
            id = index.toLong(),
            date = date,
            description = descriptions.random(),
            category = categories.random(),
            amount = amount,
            isIncome = isIncome
        )
    }.sortedByDescending { it.date }
}

private fun generateSampleFinancialData(): List<TableRow<String>> {
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
    
    return (1..30).map { index ->
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