package com.jk.financehelper.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.jk.common_ui.FinanceHelperSize
import com.jk.common_ui.FinanceHelperTheme
import com.jk.financehelper.FinanceHelperApp

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FinanceHelperTheme(textSize = FinanceHelperSize.LARGE) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = FinanceHelperTheme.colors.primaryBackground
                ) {
                    FinanceHelperApp()

                }
            }
        }
    }
}


