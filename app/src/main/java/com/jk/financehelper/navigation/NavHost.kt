package com.jk.financehelper.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.jk.financehelper.category.CategoryListScreen
import com.jk.financehelper.category.new_category.CategoryDialog
import com.jk.financehelper.category.new_category.CategoryScreen
import com.jk.financehelper.currency.ExchangeRate
import com.jk.financehelper.main.HomeScreen
import com.jk.financehelper.ui.theme.Celadon


@Composable
fun MainNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = Routes.CATEGORY_LIST) {
        composable(Routes.MAIN) {
            HomeScreen(viewModel = hiltViewModel(), navController = navController)
        }
        composable(Routes.CATEGORY_LIST) {
            CategoryListScreen(viewModel = hiltViewModel(), navController = navController)
        }
        composable(Routes.EXCHANGE_RATE){
            ExchangeRate(viewModel = hiltViewModel())
        }

        composable("${Routes.CATEGORY}?categoryId={categoryId}&color={colorInt}") {
            CategoryScreen(
                viewModel = hiltViewModel(),
                navController = navController,
                it.arguments?.getString("categoryId"),
                Celadon.toArgb().toLong()
            )
        }

        dialog(Routes.NEW_CATEGORY){
            CategoryDialog(viewModel = hiltViewModel(),navController=navController)
        }
    }
}