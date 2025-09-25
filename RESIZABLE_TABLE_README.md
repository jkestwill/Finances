# Таблица с изменяемой шириной колонок (ResizableTable)

Компонент для создания таблиц с возможностью изменения ширины колонок в Jetpack Compose.

## Возможности

- ✅ Изменение ширины колонок перетаскиванием
- ✅ Настройка минимальной и максимальной ширины колонок
- ✅ Возможность запретить изменение размера для определенных колонок
- ✅ Прокрутка содержимого при большом количестве строк
- ✅ Настраиваемые цвета и стили
- ✅ Поддержка больших объемов данных через LazyColumn

## Быстрый старт

### Простое использование

```kotlin
@Composable
fun MyScreen() {
    SimpleResizableTable(
        headers = listOf("Имя", "Возраст", "Город"),
        rows = listOf(
            listOf("Иван", "25", "Москва"),
            listOf("Мария", "30", "СПб"),
            listOf("Алексей", "28", "Казань")
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    )
}
```

### Расширенное использование

```kotlin
@Composable
fun AdvancedTable() {
    val columns = listOf(
        TableColumn(
            key = "id",
            title = "ID",
            width = 60.dp,
            minWidth = 40.dp,
            maxWidth = 100.dp
        ),
        TableColumn(
            key = "name",
            title = "Название",
            width = 150.dp,
            minWidth = 100.dp,
            maxWidth = 250.dp
        ),
        TableColumn(
            key = "price",
            title = "Цена",
            width = 80.dp,
            minWidth = 60.dp,
            maxWidth = 120.dp,
            resizable = false // Не изменяется
        )
    )
    
    val rows = listOf(
        TableRow(
            id = "1",
            data = mapOf(
                "id" to "001",
                "name" to "Товар 1",
                "price" to "1000₽"
            )
        )
        // ... больше строк
    )
    
    ResizableTable(
        columns = columns,
        rows = rows,
        modifier = Modifier.fillMaxWidth(),
        headerBackgroundColor = Color(0xFFE3F2FD),
        borderColor = Color(0xFF90CAF9)
    )
}
```

## Параметры TableColumn

- `key`: String - уникальный ключ колонки
- `title`: String - заголовок колонки  
- `width`: Dp - начальная ширина (по умолчанию 100.dp)
- `minWidth`: Dp - минимальная ширина (по умолчанию 50.dp)
- `maxWidth`: Dp - максимальная ширина (по умолчанию 300.dp)
- `resizable`: Boolean - можно ли изменять размер (по умолчанию true)

## Параметры ResizableTable

- `columns`: List<TableColumn> - список колонок
- `rows`: List<TableRow> - список строк данных
- `modifier`: Modifier - модификатор для таблицы
- `headerBackgroundColor`: Color - цвет фона заголовков
- `rowBackgroundColor`: Color - цвет фона строк
- `borderColor`: Color - цвет границ
- `tableState`: TableState - состояние таблицы для управления ширинами

## Управление состоянием

```kotlin
@Composable
fun TableWithState() {
    val tableState = rememberTableState(columns)
    
    ResizableTable(
        columns = columns,
        rows = rows,
        tableState = tableState
    )
    
    // Можно получить текущую ширину колонки
    val currentWidth = tableState.getColumnWidth("column_key")
    
    // Или установить ширину программно
    tableState.setColumnWidth("column_key", 150.dp)
}
```

## Тестирование

Для тестирования компонента создана демонстрационная активность:

```kotlin
// ResizableTableDemoActivity.kt
// Добавьте в AndroidManifest.xml для тестирования
```

## Интеграция в проект

1. Компонент создан в модуле `common-utils-ui`
2. Файлы:
   - `ResizableTable.kt` - основной компонент
   - `ResizableTableExample.kt` - примеры использования
   - `ResizableTableDemoActivity.kt` - демо активность

3. Для использования импортируйте:
```kotlin
import com.jk.common_ui.composable.ResizableTable
import com.jk.common_ui.composable.SimpleResizableTable
import com.jk.common_ui.composable.TableColumn
import com.jk.common_ui.composable.TableRow
```

## Примечания

- Компонент использует существующий `DraggableWidthContent` для изменения размеров
- Поддерживается сохранение состояния при пересоздании компонента
- Оптимизирован для больших списков данных через LazyColumn
- Совместим с темой проекта FinanceHelper

## Как это работает

1. Пользователь может перетаскивать правую границу колонки для изменения ширины
2. Ширина ограничена минимальными и максимальными значениями
3. Состояние ширин сохраняется в `TableState`
4. LazyColumn обеспечивает производительность при большом количестве строк