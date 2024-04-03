package com.jk.financehelper.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.jk.financehelper.ui.theme.FinanceHelperTheme
import com.jk.financehelper.navigation.MainNavGraph
import com.jk.financehelper.navigation.MainNavigationRoutes
import com.jk.financehelper.ui.theme.FinanceHelperSize
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FinanceHelperTheme(textSize = FinanceHelperSize.LARGE) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = FinanceHelperTheme.colors.primaryBackground
                ) {
                    MainNavGraph()
                }
            }
        }
    }
}


