package com.jk.financehelper.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jk.category.list.CategoryListScreen
import com.jk.category.add_new_category.CategoryDialog
import com.jk.category.CategoryScreen
import com.jk.category.select_category_dialog.SelectCategoryDialog
import com.jk.common_data.toByteArray
import com.jk.common_data.ULong
import com.jk.common_ui.Celadon
import com.jk.financehelper.R
import com.jk.financehelper.currency.ExchangeRate
import com.jk.financehelper.main.HomeScreen
import com.jk.transaction.TransactionScreen


@SuppressLint("RestrictedApi")
@Composable
fun MainNavGraph(
    navController: NavHostController = rememberNavController(),
) {

    NavHost(navController = navController, startDestination = Routes.CREATE_TRANSACTION) {
        composable(Routes.MAIN) {
            HomeScreen(viewModel = hiltViewModel(), navController = navController)
        }
        composable(Routes.CATEGORY_LIST) {
            CategoryListScreen(
                viewModel = hiltViewModel(),
                onCategoryItemClick = { navController.navigate("${Routes.CATEGORY}?categoryId=${it.id}") },
                onNewCategoryClick = { navController.navigate(Routes.NEW_CATEGORY) })
        }
        composable(Routes.EXCHANGE_RATE) {
            ExchangeRate(viewModel = hiltViewModel())
        }

        composable(
            "${Routes.CATEGORY}?categoryId={categoryId}&color={color}"
        ) {
            CategoryScreen(
                viewModel = hiltViewModel(),
                navController = navController,
                categoryId = it.arguments?.getString("categoryId"),
                onCreateTransactionClick = {
                    navController.navigate(Routes.CREATE_TRANSACTION)
                }
            )
        }

        composable(Routes.GOODS) {
            // GoodsList()
        }

        composable(
            Routes.CREATE_TRANSACTION,
            arguments = listOf(navArgument("categoryIdList") {
                nullable = true; type = NavType.StringType
            })
        ) {
            val onBack: () -> Unit = { navController.popBackStack() }

            val categoryIdList by it.savedStateHandle.getStateFlow<List<String>>(
                "categoryIdList",
                listOf()
            ).collectAsState()
            Log.e("TAG", "MainNavGraph jhjh:${categoryIdList} ")
            TransactionScreen(
                viewModel = hiltViewModel(),
                categoryListId = categoryIdList,
                onBackClick = if (navController.currentBackStack.collectAsState().value.isNotEmpty()) {
                    onBack
                } else null,
                onAddCategory = { idList ->
                    navController.navigate(
                        "${Routes.SELECT_CATEGORY}?selectedCategoryId=${
                            idList?.joinToString(
                                ","
                            )
                        }"
                    )
                }
            )
        }

        dialog("${Routes.SELECT_CATEGORY}?selectedCategoryId={selectedCategoryId}") {
            val selectedCategoryId = it.arguments?.getString("selectedCategoryId")?.split(",")
            Log.e("TAg", "MainNavGraph catId:${selectedCategoryId} ")
            SelectCategoryDialog(
                viewModel = hiltViewModel(),
                selectedCategoryIdList = selectedCategoryId,
                onSelectCategoryIds = { list ->
                    val route = navController.previousBackStackEntry?.destination?.route
                    Log.e("TAG", "MainNavGraph qq:$list")
                    if (route != null) {
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            "categoryIdList",
                            list
                        )
                        navController.popBackStack()

                    }

                }, onDismiss = {
                    navController.popBackStack()
                })
        }

        dialog(Routes.NEW_CATEGORY) {
            CategoryDialog(viewModel = hiltViewModel(),
                onDismiss = { navController.popBackStack() },
                categoryLabel = stringResource(
                    id = R.string.add_new_category
                ),
                categoryNamePlaceholder = stringResource(
                    id = R.string.name
                ),
                expensesLabel = stringResource(
                    id = R.string.expenses
                ),
                buttonCancelLabel = stringResource(
                    id = R.string.cancel
                ),
                buttonCreateLabel = stringResource(
                    id = R.string.create
                ),
                onNewCategoryCreated = {
                    navController.popBackStack(
                        route = Routes.CATEGORY_LIST,
                        inclusive = false,
                        saveState = false
                    )
                    navController.navigate(
                        Routes.CATEGORY_LIST,
                        navOptions = NavOptions.Builder().setLaunchSingleTop(true)
                            .setPopUpTo(route = Routes.CATEGORY_LIST, true).build()
                    )
                })
        }
    }
}