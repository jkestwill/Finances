package com.jk.financehelper.main


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jk.category_common_ui.CategoryUI
import com.jk.common_ui.TransparentTextField
import com.jk.financehelper.BuildConfig
import com.jk.financehelper.ui.calendar.data.rememberYearState
import java.time.LocalDate
import java.time.Year

private const val TAG = "MainScreen"

@SuppressLint("RememberReturnType")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
        navController: NavController
) {
    BuildConfig.BUILD_TYPE
    Column {
        val date: State<LocalDate> = remember() {
            mutableStateOf(LocalDate.now())
        }
        val yearState = rememberYearState(
            startYear = Year.of(2024 - 10),
            endYear = Year.of(2030),
            firstVisibleYear = Year.of(2024)
        )
//        val data = viewModel.transactionsFlow.collectAsState(initial = listOf())
//        val err = viewModel.transactionErrors.collectAsState(initial = null)
//        val category: LazyPagingItems<CategoryUI> =
//            viewModel.categoryFlow.collectAsLazyPagingItems()
//        Bank(modifier = Modifier, amount = 200f, viewModel = viewModel)
//        YearCalendar(
//            modifier = Modifier.height(400.dp),
//            currentDate = date,
//            yearState = yearState,
//            onMonthClick = {
//                Log.e(TAG, "MainScreen: ${it}")
//                Log.e(TAG, "MainScreen: ${data}")
//                Log.e(TAG, "MainScreen: ${err.value}")
//                //  viewModel.getTransactionListByMonth(it)
//                // viewModel.getTransactionList()
//                //  viewModel.addCategory(TransactionCategory(id="1",name="Sport"))
//                //  viewModel.addTransaction(DataUtils.getBarChartData()[0])
//            }) {
//            VicoGraph(
//                transactionList = data.value,
//                chartDataType = ChartData.YearChartData(yearState.firstVisibleYear.value)
//            )
//        }
    }
}

//@Composable
//fun Bank(modifier: Modifier, amount: Float, viewModel: HomeViewModel) {
//    var editableAmount by remember() { mutableStateOf(amount.toString()) }
//    val isFieldEnabled by remember {
//        mutableStateOf(true)
//    }
//    val pattern = remember { Regex(pattern = "([0-9]{1,5})\\.([0-9]{1,2})") }
//    val expenses = viewModel.expensesFLow.collectAsState()
//
//    Row(modifier) {
//        Text(text = expenses.value.toString())
//        TransparentTextField(
//            modifier = Modifier, value = editableAmount, onValueChange = {
//                Log.e("qq", "Bank: ${pattern.matches(it)} ")
//                if (it.isNotEmpty() && pattern.matches(it)) editableAmount = it
//
//            }, isFieldEnabled
//        )
//    }
//}





