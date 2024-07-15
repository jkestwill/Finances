package com.jk.financehelper.navigation

import android.annotation.SuppressLint
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
import com.jk.category.CategoryScreen
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
                Celadon.toArgb().toULong()
            )
        }

        composable(Routes.GOODS){
            GoodsList(goodsViewModel = hiltViewModel())
        }

        dialog(Routes.NEW_CATEGORY){
            CategoryDialog(viewModel = hiltViewModel(),navController=navController)
        }
    }
}