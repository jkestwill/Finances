package com.jk.financehelper.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jk.financehelper.DatabaseTest
import com.jk.transaction_database.transaction.dao.TransactionDao
import com.jk.transaction_database.transaction.database.TransactionDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TransactionDaoTest {

    private lateinit var db: TransactionDatabase
    private lateinit var transactionDao: TransactionDao
    private var dbTest = DatabaseTest()
    @Before
    fun init(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, TransactionDatabase::class.java).build()
        transactionDao = db.getTransactionDao()
        dbTest.initDb()
    }

    @Test
    fun readTransactionPreview():Unit= runBlocking{
        dbTest.writeReadTransactionRelation()

        println(transactionDao.getTransactionPreviewListByCategoryId("1", "","id", true,0,0))
    }

    @After
    fun close(){
        db.close()
        dbTest.closeDb()
    }

    companion object{
//        fun testTransactionPreviewList():List<TransactionPreview>{
//            return listOf(
//                TransactionPreview()
//            )
//        }
    }
}