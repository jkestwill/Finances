package com.jk.financehelper.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.jk.category.CategoryListScreen
import com.jk.category.CategoryDialog
import com.jk.category.CategoryScreen
import com.jk.financehelper.R
import com.jk.financehelper.currency.ExchangeRate
import com.jk.financehelper.main.HomeScreen
import com.jk.financehelper.ui.theme.Celadon
import com.jk.goods.GoodsList


@SuppressLint("RestrictedApi")
@Composable
fun MainNavGraph(
    navController: NavHostController = rememberNavController(),
) {

    NavHost(navController = navController, startDestination = Routes.GOODS) {
        composable(Routes.MAIN) {
            HomeScreen(viewModel = hiltViewModel(), navController = navController)
        }
        composable(Routes.CATEGORY_LIST) {
            CategoryListScreen(
                viewModel = hiltViewModel(),
                onCategoryItemClick = { navController.navigate("${Routes.CATEGORY}?categoryId=${it.id}&colorInt=${it.color}") },
                onNewCategoryClick = { navController.navigate(Routes.NEW_CATEGORY) })
        }
        composable(Routes.EXCHANGE_RATE) {
            ExchangeRate(viewModel = hiltViewModel())
        }

        composable("${Routes.CATEGORY}?categoryId={categoryId}&color={colorInt}") {
            CategoryScreen(
                viewModel = hiltViewModel(),
                navController = navController,
                it.arguments?.getString("categoryId"),
                Celadon.toArgb().toULong()
            )
        }

        composable(Routes.GOODS) {
            // GoodsList()
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