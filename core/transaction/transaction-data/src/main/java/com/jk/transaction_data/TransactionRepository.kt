package com.jk.transaction_data

import com.jk.transaction_database.transaction.dao.TransactionDao
import javax.inject.Inject

class TransactionRepository @Inject constructor (
    val transactionDao:TransactionDao
) {


}