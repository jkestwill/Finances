package com.jk.financehelper.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jk.category.CategoryScreen
import com.jk.category.add_new_category.CategoryDialog
import com.jk.category.list.CategoryListScreen
import com.jk.category.select_category_dialog.SelectCategoryDialog
import com.jk.financehelper.R
import com.jk.financehelper.currency.ExchangeRate
import com.jk.goods.goods_list.GoodsListScreen
import com.jk.goods.select_goods.SelectGoodsDialog
import com.jk.transaction.TransactionScreen

private const val TAG = "NavHost"

@SuppressLint("RestrictedApi")
@Composable
fun MainNavGraph(
    navController: NavHostController = rememberNavController(),
) {

    NavHost(navController = navController, startDestination = Routes.GOODS) {

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
            }, navArgument("preselectedGoodsId") {
                nullable = true; type = NavType.StringType
            })
        ) {
            val onBack: () -> Unit = { navController.popBackStack() }
            val goodsIdList =
                it.savedStateHandle.getStateFlow<List<String>?>("preselectedGoodsId", null)
                    .collectAsState()
            val categoryIdList = it.savedStateHandle.getStateFlow<List<String>>(
                "categoryIdList",
                listOf()
            ).collectAsState()

            TransactionScreen(
                viewModel = hiltViewModel(),
                goodsIdList = goodsIdList.value,
                categoryListId = categoryIdList.value,
                onBackClick = if (navController.currentBackStack.collectAsState().value.size > 2) {
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
                },
                onGoodsAdd = { preselectedGoodsIdList ->
                    Log.e(TAG, "CreateTransaction onGoodsAdd ${preselectedGoodsIdList} ")
                    navController.navigate(
                        "${Routes.SELECT_GOODS}?preselectedGoodsId=${
                            preselectedGoodsIdList?.joinToString(
                                ","
                            )
                        }"
                    )
                    it.arguments?.remove("preselectedGoodsId")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = "goods_list") {
            GoodsListScreen(viewModel = hiltViewModel())
        }

        dialog("${Routes.SELECT_CATEGORY}?selectedCategoryId={selectedCategoryId}") {
            val selectedCategoryId = it.arguments?.getString("selectedCategoryId")?.split(",")

            Log.e("TAg", "MainNavGraph catId:${selectedCategoryId} ")
            SelectCategoryDialog(
                viewModel = hiltViewModel(),
                selectedCategoryIdList = selectedCategoryId,
                onSelectCategoryIds = { list ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "categoryIdList",
                        list
                    )
                    navController.popBackStack()


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

        dialog("${Routes.SELECT_GOODS}?preselectedGoodsId={preselectedGoodsId}") {
            val preselectedGoodsId = it.arguments?.getString("preselectedGoodsId")?.split(",")

            SelectGoodsDialog(
                viewModel = hiltViewModel(),
                preselectedIdList = preselectedGoodsId,
                onSelect = { idList ->
                    Log.e(TAG, "SELECT_GOODS:OnSelect ${idList} ")
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "preselectedGoodsId",
                        idList
                    )
                    navController.popBackStack()
                },
                onDismiss = {
                    navController.popBackStack()
                })


        }
    }
}