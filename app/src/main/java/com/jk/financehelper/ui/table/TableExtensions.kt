package com.jk.financehelper.ui.table

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.math.BigDecimal
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Вспомогательные функции для создания типизированных таблиц
 */

/**
 * Создает колонку для строковых данных
 */
fun stringColumn(
    key: String,
    title: String,
    initialWidth: Dp = 120.dp,
    minWidth: Dp = 50.dp,
    maxWidth: Dp = 300.dp
): TableColumn<String> = TableColumn(
    key = key,
    title = title,
    initialWidth = initialWidth,
    minWidth = minWidth,
    maxWidth = maxWidth,
    formatter = { it ?: "" }
)

/**
 * Создает колонку для числовых данных (Int)
 */
fun intColumn(
    key: String,
    title: String,
    initialWidth: Dp = 100.dp,
    minWidth: Dp = 50.dp,
    maxWidth: Dp = 200.dp
): TableColumn<Int> = TableColumn(
    key = key,
    title = title,
    initialWidth = initialWidth,
    minWidth = minWidth,
    maxWidth = maxWidth,
    formatter = { it?.toString() ?: "0" }
)

/**
 * Создает колонку для числовых данных (Long)
 */
fun longColumn(
    key: String,
    title: String,
    initialWidth: Dp = 100.dp,
    minWidth: Dp = 50.dp,
    maxWidth: Dp = 200.dp
): TableColumn<Long> = TableColumn(
    key = key,
    title = title,
    initialWidth = initialWidth,
    minWidth = minWidth,
    maxWidth = maxWidth,
    formatter = { it?.toString() ?: "0" }
)

/**
 * Создает колонку для денежных сумм (BigDecimal)
 */
fun currencyColumn(
    key: String,
    title: String,
    initialWidth: Dp = 120.dp,
    minWidth: Dp = 80.dp,
    maxWidth: Dp = 200.dp,
    currencySymbol: String = "₽",
    decimalFormat: DecimalFormat = DecimalFormat("#,##0.00")
): TableColumn<BigDecimal> = TableColumn(
    key = key,
    title = title,
    initialWidth = initialWidth,
    minWidth = minWidth,
    maxWidth = maxWidth,
    formatter = { amount ->
        amount?.let { "${decimalFormat.format(it)} $currencySymbol" } ?: "0.00 $currencySymbol"
    }
)

/**
 * Создает колонку для дат (LocalDate)
 */
fun dateColumn(
    key: String,
    title: String,
    initialWidth: Dp = 120.dp,
    minWidth: Dp = 80.dp,
    maxWidth: Dp = 150.dp,
    dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
): TableColumn<LocalDate> = TableColumn(
    key = key,
    title = title,
    initialWidth = initialWidth,
    minWidth = minWidth,
    maxWidth = maxWidth,
    formatter = { date -> date?.format(dateFormat) ?: "" }
)

/**
 * Создает колонку для даты и времени (LocalDateTime)
 */
fun dateTimeColumn(
    key: String,
    title: String,
    initialWidth: Dp = 150.dp,
    minWidth: Dp = 120.dp,
    maxWidth: Dp = 200.dp,
    dateTimeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
): TableColumn<LocalDateTime> = TableColumn(
    key = key,
    title = title,
    initialWidth = initialWidth,
    minWidth = minWidth,
    maxWidth = maxWidth,
    formatter = { dateTime -> dateTime?.format(dateTimeFormat) ?: "" }
)

/**
 * Создает колонку для boolean значений
 */
fun booleanColumn(
    key: String,
    title: String,
    initialWidth: Dp = 80.dp,
    minWidth: Dp = 60.dp,
    maxWidth: Dp = 120.dp,
    trueLabel: String = "Да",
    falseLabel: String = "Нет"
): TableColumn<Boolean> = TableColumn(
    key = key,
    title = title,
    initialWidth = initialWidth,
    minWidth = minWidth,
    maxWidth = maxWidth,
    formatter = { if (it == true) trueLabel else falseLabel }
)

/**
 * Создает колонку для enum значений
 */
inline fun <reified T : Enum<T>> enumColumn(
    key: String,
    title: String,
    initialWidth: Dp = 120.dp,
    minWidth: Dp = 80.dp,
    maxWidth: Dp = 200.dp,
    noinline labelProvider: (T) -> String = { it.name }
): TableColumn<T> = TableColumn(
    key = key,
    title = title,
    initialWidth = initialWidth,
    minWidth = minWidth,
    maxWidth = maxWidth,
    formatter = { it?.let(labelProvider) ?: "" }
)

/**
 * Создает колонку для процентных значений
 */
fun percentageColumn(
    key: String,
    title: String,
    initialWidth: Dp = 100.dp,
    minWidth: Dp = 70.dp,
    maxWidth: Dp = 150.dp,
    decimalFormat: DecimalFormat = DecimalFormat("#0.00")
): TableColumn<Double> = TableColumn(
    key = key,
    title = title,
    initialWidth = initialWidth,
    minWidth = minWidth,
    maxWidth = maxWidth,
    formatter = { percentage ->
        percentage?.let { "${decimalFormat.format(it * 100)}%" } ?: "0.00%"
    }
)

/**
 * Вспомогательная функция для создания строки таблицы из произвольного объекта
 */
inline fun <reified T> createTableRow(
    id: String,
    item: T,
    vararg pairs: Pair<String, Any?>
): TableRow<Any?> = TableRow(
    id = id,
    data = mapOf(*pairs)
)

/**
 * Extension функция для безопасного создания строки таблицы
 */
fun <T> T.toTableRow(
    id: String,
    dataMapper: (T) -> Map<String, Any?>
): TableRow<Any?> = TableRow(
    id = id,
    data = dataMapper(this)
)

/**
 * Создает строку таблицы из FinancialTransaction для демонстрации
 */
fun FinancialTransaction.toTableRow(): TableRow<Any?> = TableRow(
    id = this.id.toString(),
    data = mapOf(
        "id" to this.id,
        "date" to this.date,
        "description" to this.description,
        "category" to this.category,
        "amount" to this.amount,
        "isIncome" to this.isIncome
    )
)