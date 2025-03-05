package com.jk.transaction_database

import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jk.transaction_database.transaction.dao.CategoryDao
import com.jk.transaction_database.transaction.dao.CurrencyDao
import com.jk.transaction_database.transaction.dao.GoodsDao
import com.jk.transaction_database.transaction.dao.MeasureDao
import com.jk.transaction_database.transaction.dao.MoneyAccountDao
import com.jk.transaction_database.transaction.dao.MoneyDao
import com.jk.transaction_database.transaction.dao.OperationCategoryDao
import com.jk.transaction_database.transaction.dao.OperationDao
import com.jk.transaction_database.transaction.dao.TransactionDao
import com.jk.transaction_database.transaction.dao.TypeDao
import com.jk.transaction_database.transaction.database.TransactionDatabaseProvider
import com.jk.transaction_database.transaction.database.transactionDatabaseProvider
import com.jk.transaction_database.transaction.entity.CategoryEntity
import com.jk.transaction_database.transaction.entity.CurrencyEntity
import com.jk.transaction_database.transaction.entity.MoneyAccountEntity
import com.jk.transaction_database.transaction.entity.MoneyEntity
import com.jk.transaction_database.transaction.entity.OperationEntity
import com.jk.transaction_database.transaction.entity.TransactionEntity
import com.jk.transaction_database.transaction.entity.TransactionTypeEntity
import com.jk.transaction_database.transaction.list.OperationCategoryList
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.random.Random
import kotlin.random.nextULong

@RunWith(AndroidJUnit4::class)
class TransactionPreviewTest {

    private lateinit var goodsDao: GoodsDao
    private lateinit var currencyDao: CurrencyDao
    private lateinit var moneyDao: MoneyDao
    private lateinit var measureDao: MeasureDao
    private lateinit var database: TransactionDatabaseProvider
    private lateinit var transactionTypeDao:TypeDao
    private lateinit var operationDao:OperationDao
    private lateinit var transactionDao:TransactionDao
    private lateinit var categoryDao:CategoryDao
    private lateinit var operationCategoryList: OperationCategoryDao
    private lateinit var moneyAccountDao: MoneyAccountDao

    private val TAG = "TransactionPreviewTest"

    @Before
    fun init() {
        database =
            transactionDatabaseProvider(
                context = ApplicationProvider.getApplicationContext(),
                "transaction_db.db"
            )
        goodsDao = database.getGoodsDao()
        currencyDao = database.getCurrencyDao()
        moneyDao = database.getMoneyDao()
        operationDao = database.getOperationDao()
        transactionDao = database.getTransactionDao()
        transactionTypeDao = database.getTypeDao()
        categoryDao = database.getCategoryDao()
        operationCategoryList = database.getOperationCategoryDao()
        moneyAccountDao = database.getMoneyAccountDao()
    }

    @Test
    fun insert_test() = runBlocking{
        database.db.clearAllTables()
        val currency = CurrencyEntity("b", "BYN")
        val money = MoneyEntity("aojsd", 23.1, currency.id, LocalDate.now())
        val transactionTypeEntity = TransactionTypeEntity("id","online")
        val moneyForAccount = MoneyEntity("zxc", 23.1, currency.id, LocalDate.now())
        val moneyAccount = MoneyAccountEntity("m","Card1",null,moneyId=moneyForAccount.id)
        val operation = OperationEntity("op","Пакупачка",money.id, isExpenses = true)
        val transaction = TransactionEntity("id", date = LocalDateTime.now(), operationId = operation.id, typeId = transactionTypeEntity.id, moneyAccountId = moneyAccount.id)
        val categoryList = randomCategoryList(20)
        categoryDao.insert(categoryList)

        transactionTypeDao.insert(transactionTypeEntity)
        currencyDao.insert(currency)
        moneyDao.insert(money)
        moneyDao.insert(moneyForAccount)
        moneyAccountDao.insert(moneyAccount)
        operationDao.insert(operation)
        transactionDao.insert(transaction)

        for (cat in categoryList) {
            operationCategoryList.insert(OperationCategoryList(operation.id,cat.id))
        }
        val previewList =  transactionDao.getTransactionPreview()

        Log.e(TAG, "insert_test:${previewList} ", )

        assertTrue(previewList.isNotEmpty())
    }

    private fun randomCategoryList(n:Int) =List<CategoryEntity>(n) {
        CategoryEntity(id="$it",name = "Chevapchis${it}", color = Random.nextInt())
    }
    @After
    fun close() {
        database.db.close()
    }
}