package com.jk.financehelper.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
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

    NavHost(navController = navController, startDestination = Routes.CATEGORY_LIST) {
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
            )
        }

        composable(Routes.GOODS) {
            // GoodsList()
        }

        composable("${Routes.CREATE_TRANSACTION}?categoryIdList={categoryIdList}"){
            TransactionScreen(null)
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