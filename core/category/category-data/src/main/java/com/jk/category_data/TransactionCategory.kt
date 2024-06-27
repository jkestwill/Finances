package com.jk.category_data

import javax.annotation.concurrent.Immutable

/**
 * Business logic layer transaction*/
data class TransactionCategory(
    val id:String,
    val name:String,
    val color:ULong,
    val isExpenses:Boolean
)


