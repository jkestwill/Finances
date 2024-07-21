package com.jk.transaction

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import com.jk.common_ui.FinanceHelperTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TransactionScreen(categoryListId: List<String>?) {
    Scaffold(topBar = {
        Row {
            Text("Create Transaction", style = FinanceHelperTheme.typography.h1)
        }
    }){

    }
}

/**
 * Transaction section contains base info about transaction f.e name amount currency etc.
 * */
@Composable
fun TransactionInfoSection() {
    val transactionName = rememberSaveable(){
        mutableStateOf("")
    }

    val amount = rememberSaveable() {
        mutableStateOf("")
    }
    val currency = rememberSaveable(){
        mutableStateOf("")
    }

    Row {

    }
    

}