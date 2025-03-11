package com.jk.financehelper

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jk.common_ui.FinanceHelperTheme
import com.jk.financehelper.navigation.MainNavGraph
import com.jk.financehelper.navigation.Routes


@Composable
fun FinanceHelperApp() {

    val navController = rememberNavController()


    val destinationList = remember {
        mutableStateListOf(
            NavBarDestination(
                Icons.Default.AccountBox,
                Routes.CREATE_TRANSACTION,
                displayedDestinationName ="Create Transaction"
            ),
            NavBarDestination(
                Icons.Default.AccountBox,
                Routes.GOODS_LIST,
                displayedDestinationName = "Goods list"
            ),
            NavBarDestination(
                Icons.Default.AccountBox,
                Routes.CATEGORY,
                displayedDestinationName = "Category"
            ),
        )
    }

    val bottomBarVisibility = remember(navController.currentBackStackEntryAsState().value) {
        derivedStateOf {
            destinationList.fastAny { it.destination == navController.currentDestination?.route }
        }
    }
    Box {
        MainNavGraph(navController)
        if (bottomBarVisibility.value)
            NavBar(
                destinationList = destinationList,
                onDestinationClick = {
                    navController.navigate(it.destination)
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(40.dp)
                    .offset(y=-50.dp)
                    .padding(start = 20.dp, end = 20.dp)
                    .background(FinanceHelperTheme.colors.defaultButtonColor,FinanceHelperTheme.shape.shapeRoundMedium)


            )
    }
}

@Composable
fun NavBar(
    destinationList: List<NavBarDestination>,
    onDestinationClick: (NavBarDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        repeat(destinationList.size) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .clickable { onDestinationClick(destinationList[it]) }) {
                Icon(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    imageVector = destinationList[it].icon,
                    contentDescription = destinationList[it].destination
                )
                if (destinationList[it].displayedDestinationName != null)
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = destinationList[it].displayedDestinationName!!
                    )

            }
        }
    }
}

data class NavBarDestination(
    val icon: ImageVector,
    val destination: String,
    val displayedDestinationName: String?,
)