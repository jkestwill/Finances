# ResizableTable - Таблица с изменяемыми колонками для Jetpack Compose

## Описание

ResizableTable - это кастомный компонент Jetpack Compose, который предоставляет функциональность таблицы с изменяемой шириной колонок, похожую на Excel. Компонент поддерживает типобезопасность через дженерики и пользовательские форматтеры для различных типов данных.

## Основные возможности

- ✅ **Изменяемая ширина колонок** - перетаскивание правого края заголовков колонок
- ✅ **Типобезопасность** - поддержка дженериков для различных типов данных
- ✅ **Пользовательские форматтеры** - настраиваемое отображение данных
- ✅ **Ограничения размеров** - минимальная и максимальная ширина колонок
- ✅ **Прокрутка содержимого** - поддержка большого количества данных
- ✅ **Чередующиеся цвета строк** - улучшенная читаемость
- ✅ **Темизация Material Design** - интеграция с системой цветов

## Быстрый старт

### Простая строковая таблица

```kotlin
val columns = listOf(
    TableColumn<String>("id", "ID", 60.dp),
    TableColumn<String>("name", "Название", 150.dp),
    TableColumn<String>("value", "Значение", 100.dp)
)

val rows = listOf(
    TableRow("1", mapOf("id" to "1", "name" to "Элемент 1", "value" to "100")),
    TableRow("2", mapOf("id" to "2", "name" to "Элемент 2", "value" to "200"))
)

ResizableTable(
    columns = columns,
    rows = rows,
    modifier = Modifier.fillMaxWidth(),
    onColumnWidthChanged = { columnKey, newWidth ->
        println("Колонка $columnKey изменена на $newWidth")
    }
)
```

### Типизированная таблица с пользовательскими форматтерами

```kotlin
val columns = listOf(
    TableColumn<Long>("id", "ID", 60.dp) { it.toString() },
    TableColumn<LocalDate>("date", "Дата", 120.dp) { 
        it?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) ?: "" 
    },
    TableColumn<BigDecimal>("amount", "Сумма", 120.dp) { 
        it?.let { DecimalFormat("#,##0.00").format(it) } ?: "0.00" 
    },
    TableColumn<Boolean>("active", "Активен", 80.dp) { 
        if (it == true) "Да" else "Нет" 
    }
)

data class Item(val id: Long, val date: LocalDate, val amount: BigDecimal, val active: Boolean)

val items = listOf(/* ваши данные */)
val rows = items.map { item ->
    TableRow(
        id = item.id.toString(),
        data = mapOf(
            "id" to item.id,
            "date" to item.date,
            "amount" to item.amount,
            "active" to item.active
        )
    )
}

ResizableTable(
    columns = columns,
    rows = rows,
    modifier = Modifier.fillMaxWidth()
)
```

## Использование вспомогательных функций

Библиотека предоставляет удобные функции для создания распространенных типов колонок:

```kotlin
import com.jk.financehelper.ui.table.*

val columns = listOf(
    longColumn("id", "ID", 60.dp),
    dateColumn("date", "Дата", 120.dp),
    currencyColumn("amount", "Сумма", 120.dp, "₽"),
    booleanColumn("active", "Активен", 80.dp, "Активен", "Неактивен"),
    percentageColumn("progress", "Прогресс", 100.dp)
)
```

## Доступные типы колонок

- `stringColumn()` - для строковых данных
- `intColumn()` / `longColumn()` - для целых чисел
- `currencyColumn()` - для денежных сумм с форматированием
- `dateColumn()` - для дат (LocalDate)
- `dateTimeColumn()` - для даты и времени (LocalDateTime)
- `booleanColumn()` - для boolean значений с настраиваемыми метками
- `enumColumn()` - для enum значений
- `percentageColumn()` - для процентных значений

## Параметры настройки

### TableColumn

- `key: String` - уникальный ключ колонки
- `title: String` - заголовок колонки
- `initialWidth: Dp` - начальная ширина (по умолчанию 120.dp)
- `minWidth: Dp` - минимальная ширина (по умолчанию 50.dp)
- `maxWidth: Dp` - максимальная ширина (по умолчанию 300.dp)
- `formatter: (T?) -> String` - функция форматирования данных

### ResizableTable

- `columns: List<TableColumn<T>>` - список колонок
- `rows: List<TableRow<T>>` - список строк данных
- `modifier: Modifier` - модификатор компонента
- `headerBackgroundColor: Color` - цвет фона заголовка
- `rowBackgroundColor: Color` - цвет фона строк
- `alternateRowBackgroundColor: Color` - цвет фона чередующихся строк
- `borderColor: Color` - цвет границ
- `onColumnWidthChanged: ((String, Dp) -> Unit)?` - callback изменения ширины колонки

## Примеры использования

Полные примеры доступны в `TableDemoScreen.kt`:

1. **Типизированная таблица** - демонстрирует работу с различными типами данных
2. **Строковая таблица** - классический подход с хранением всех данных как строк
3. **Финансовые транзакции** - пример использования для реальных данных

## Производительность

- Использует `LazyColumn` для эффективной прокрутки больших наборов данных
- Минимальные recomposition благодаря `remember` и локальному состоянию
- Оптимизированные drag gestures для плавного изменения размеров

## Ограничения

- Изменение размера доступно только для колонок (не для строк)
- Фиксированная высота строк (40dp для данных, 48dp для заголовка)
- Требует Android API 26+

## Интеграция

Добавьте файлы в ваш проект:
- `ResizableTable.kt` - основной компонент
- `TableExtensions.kt` - вспомогательные функции
- `TableDemoScreen.kt` - примеры использования