package com.jk.financehelper

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.jk.transaction_database.transaction.LedgerEntity
import com.jk.transaction_database.transaction.OperationEntity
import com.jk.transaction_database.transaction.TransactionCategoryDatabaseEntity
import com.jk.transaction_database.transaction.TransactionCurrencyDatabaseEntity
import com.jk.transaction_database.transaction.TransactionEntity
import com.jk.transaction_database.transaction.TransactionGoodsDatabaseEntity
import com.jk.transaction_database.transaction.TransactionMoneyDatabaseEntity
import com.jk.transaction_database.transaction.TransactionScheduleDatabaseEntity
import com.jk.transaction_database.transaction.TransactionTypeDatabaseEntity
import com.jk.transaction_database.transaction.dao.CategoryDao
import com.jk.transaction_database.transaction.dao.CurrencyDao
import com.jk.transaction_database.transaction.dao.GoodsDao
import com.jk.transaction_database.transaction.dao.LedgerDao
import com.jk.transaction_database.transaction.dao.LedgerTransactionListDao
import com.jk.transaction_database.transaction.dao.MoneyDao
import com.jk.transaction_database.transaction.dao.OperationCategoryDao
import com.jk.transaction_database.transaction.dao.OperationDao
import com.jk.transaction_database.transaction.dao.ScheduleDao
import com.jk.transaction_database.transaction.dao.TransactionDao
import com.jk.transaction_database.transaction.dao.TransactionGoodsListDao
import com.jk.transaction_database.transaction.dao.TypeDao
import com.jk.transaction_database.transaction.database.TransactionDatabase
import com.jk.transaction_database.transaction.list.LedgerTransactionList
import com.jk.transaction_database.transaction.list.OperationCategoryList
import com.jk.transaction_database.transaction.list.OperationGoodsListEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime


@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var transactionDao: TransactionDao
    private lateinit var operationDao: OperationDao
    private lateinit var categoryDao: CategoryDao
    private lateinit var goodsDao: GoodsDao
    private lateinit var moneyDao: MoneyDao
    private lateinit var scheduleDao: ScheduleDao
    private lateinit var typeDao: TypeDao
    private lateinit var currencyDao: CurrencyDao
    private lateinit var operationCategoryDao: OperationCategoryDao
    private lateinit var transactionGoodsListDao: TransactionGoodsListDao
    private lateinit var ledgerDao: LedgerDao
    private lateinit var ledgerTransactionListDao: LedgerTransactionListDao

    private lateinit var db: TransactionDatabase

    @Before
    fun initDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, TransactionDatabase::class.java).build()
        transactionDao = db.getTransactionDao()
        operationDao = db.getOperationDao()
        categoryDao = db.getCategoryDao()
        moneyDao = db.getMoneyDao()
        goodsDao = db.getGoodsDao()
        scheduleDao = db.getScheduleDao()
        typeDao = db.getTypeDao()
        currencyDao = db.getCurrencyDao()
        operationCategoryDao = db.getOperationCategoryDao()
        transactionGoodsListDao = db.getTransactionGoodsListDao()
        ledgerDao = db.getLedgerDao()
        ledgerTransactionListDao = db.getLedgerTransactionListDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeOperationTest(): Unit = runBlocking(Dispatchers.IO) {
        for (i in testOperationList()) {
            operationDao.insert(i)
        }

        for (i in testOperationList())
            assertThat(operationDao.getAll()).contains(i)
    }

    @Test
    fun writeOperationRelationTest(): Unit = runBlocking(Dispatchers.IO) {
        for (i in testOperationList()) {
            operationDao.insert(i)
        }
        writeReadScheduleTest()
        writeReadCurrencyTest()
        writeReadMoneyTest()
        writeReadTypTest()
        writeReadCategoryTest()
        writeReadOperationCategoryTest()

        for (i in testOperationList())
            println(operationDao.getRelation())
        assertThat(true).isTrue()
    }

    @Test
    fun writeReadScheduleTest(): Unit = runBlocking() {
        writeReadTest(dbData = { scheduleDao.getAll() }, checkDataList = testScheduleList()) {
            scheduleDao.insert(listOf(it))
        }
    }

    @Test
    fun writeReadGoodsTest(): Unit = runBlocking {
//        writeReadTest(dbData = { goodsDao.getAll() }, checkDataList = testGoodsList()) {
//            goodsDao.insert(it)
//        }
    }

    @Test
    fun writeReadTransactionGoodsList() = runBlocking {
        writeReadTest(
            dbData = { transactionGoodsListDao.getAll() },
            checkDataList = testTransactionGoodsList()
        ) {
            transactionGoodsListDao.insert(it)
        }
    }

    @Test
    fun writeReadMoneyTest(): Unit = runBlocking {
        writeReadTest(dbData = { moneyDao.getAll() }, checkDataList = testCostList()) {
            moneyDao.insert(it)
        }
    }

    @Test
    fun writeReadTypTest(): Unit = runBlocking {
        writeReadTest(dbData = { typeDao.getAll() }, checkDataList = testTypeData()) {
            typeDao.insert(it)
        }
    }

    @Test
    fun writeReadCategoryTest(): Unit = runBlocking {
        writeReadTest(dbData = {
            categoryDao.getAll(
                sortBy = "id",
                isAsc = true,
                offset = 0,
                q = "",
                limit = 20
            )
        }, checkDataList = testCategoryList()) {
            categoryDao.insert(it)
        }
    }

    @Test
    fun writeReadCurrencyTest(): Unit = runBlocking {
        writeReadTest(dbData = { currencyDao.getAll() }, checkDataList = testCurrencyList()) {
            currencyDao.insert(it)
        }
    }

    @Test
    fun writeReadOperationCategoryTest(): Unit = runBlocking {
        writeReadTest(
            dbData = { operationCategoryDao.getAll() },
            checkDataList = testOperationCategoryList()
        ) {
            operationCategoryDao.insert(it)
        }
    }

    @Test
    fun writeReadTransactionGoodsListTest(): Unit = runBlocking {
        writeReadTest(
            dbData = { transactionGoodsListDao.getAll() },
            checkDataList = testTransactionGoodsList()
        ) {
            transactionGoodsListDao.insert(it)
        }
    }

    @Test
    fun writeReadTransactionsListTest(): Unit = runBlocking {
        writeReadTest(
            dbData = { transactionDao.getAll() },
            checkDataList = testTransactionList()
        ) {
            transactionDao.insert(it)
        }
    }

    @Test
    fun writeReadTransactionRelation(): Unit = runBlocking {
        for (i in testTransactionList()) {
            transactionDao.insert(i)
        }
        writeOperationRelationTest()
        writeReadGoodsTest()
        writeReadTransactionGoodsList()
        println(transactionDao.getRelation())
    }


    fun writeReadLedger(): Unit = runBlocking {
        writeReadTest({ ledgerDao.getAll() }, testLedgerData()) {
            ledgerDao.insert(it)
        }
    }

    fun writeReadLedgerTransactionList(): Unit = runBlocking {
        writeReadTest({ ledgerTransactionListDao.getAll() }, testLedgerTransactionListData()) {
            ledgerTransactionListDao.insert(it)
        }
    }

    @Test
    fun writeReadLedgerRelation() = runBlocking {
        for (i in testLedgerData()) {
            ledgerDao.insert(i)
        }
        writeReadTransactionRelation()
        writeReadLedgerTransactionList()

        println(ledgerDao.getRelation())
    }

    // тест записи и чтения тестовых данных
    private suspend fun <T> writeReadTest(
        dbData: suspend () -> List<T>,
        checkDataList: List<T>,
        insert: suspend (T) -> Unit
    ) {

        for (i in checkDataList) {
            insert(i)
        }

        for (i in checkDataList) {
            assertThat(dbData()).contains(i)
        }
    }


    companion object {

        fun testTransactionList(): List<TransactionEntity> {
            return listOf(
                TransactionEntity(
                    id = "t1",
                    date = LocalDateTime.of(2011, 5, 9, 17, 4),
                    operationId = "1",
                    typeId = "11"
                ),
                TransactionEntity(
                    id = "t2",
                    date = LocalDateTime.of(2021, 7, 9, 9, 4),
                    operationId = "2",
                    typeId = "11"
                )
            )
        }


        fun testOperationCategoryList(): List<OperationCategoryList> {
            return listOf(
                OperationCategoryList(
                    operationId = "1", categoryId = "k2"
                ),
                OperationCategoryList(
                    operationId = "2", categoryId = "k2"
                ),
                OperationCategoryList(
                    operationId = "1", categoryId = "k4"
                ),
                OperationCategoryList(
                    operationId = "3", categoryId = "k4"
                ),
                OperationCategoryList(
                    operationId = "2", categoryId = "k4"
                ),
                OperationCategoryList(
                    operationId = "2", categoryId = "k1"
                )
            )
        }

        fun testOperationList(): List<OperationEntity> {
            return listOf(
                OperationEntity(
                    id = "1", name = "Evroopt", "c1"
                ),
                OperationEntity(
                    id = "2", name = "Dionis",  "c2"
                ),
                OperationEntity(
                    id = "3", name = "A1", "c3"
                )
            )
        }

        fun testCategoryList(): List<TransactionCategoryDatabaseEntity> {
            return listOf(
                TransactionCategoryDatabaseEntity(
                    id = "k1", name = "Sport", color = 0xFFFF8484.toString(), isExpenses = true
                ),
                TransactionCategoryDatabaseEntity(
                    id = "k2", name = "Home", color = 0xFF2374AB.toString(), isExpenses = true
                ),
                TransactionCategoryDatabaseEntity(
                    id = "k3", name = "Health", color = 0xFFFFEC51.toString(), isExpenses = true
                ),
                TransactionCategoryDatabaseEntity(
                    id = "k4", name = "Computer", color = 0xFF9E2B25.toString(), isExpenses = true
                )
            )
        }

        fun testScheduleList(): List<TransactionScheduleDatabaseEntity> {
            return listOf(
                TransactionScheduleDatabaseEntity(
                    id = "zxc1", startDate = LocalDateTime.of(2011, 5, 9, 17, 4), 3, 111111
                ),
                TransactionScheduleDatabaseEntity(
                    id = "zxc2", startDate = LocalDateTime.of(2015, 5, 9, 17, 4), 0, 0
                ),
                TransactionScheduleDatabaseEntity(
                    id = "zxc3", startDate = LocalDateTime.of(1990, 1, 2, 13, 26), 1, 1870901
                ),
                TransactionScheduleDatabaseEntity(
                    id = "zxc4", startDate = LocalDateTime.of(2040, 4, 1, 23, 59), 2, 201981737
                ),
            )
        }

        fun testTransactionGoodsList(): List<OperationGoodsListEntity> {
            return listOf(
                OperationGoodsListEntity(
                    operationId = "t1", goodsId = "111"
                ),
                OperationGoodsListEntity(
                    operationId = "t1", goodsId = "122"
                ),
                OperationGoodsListEntity(
                    operationId = "t1", goodsId = "133"
                ),
                OperationGoodsListEntity(
                    operationId = "t1", goodsId = "144"
                ),
            )
        }

        fun testCurrencyList(): List<TransactionCurrencyDatabaseEntity> {
            return listOf(
                TransactionCurrencyDatabaseEntity(
                    id = "qwe1", name = "BYN"
                ),
                TransactionCurrencyDatabaseEntity(
                    id = "qwe2", name = "USD"
                ),
                TransactionCurrencyDatabaseEntity(
                    id = "qwe3", name = "UAH"
                ),
                TransactionCurrencyDatabaseEntity(
                    id = "qwe4", name = "RUB"
                )
            )
        }

        fun testGoodsList(): List<TransactionGoodsDatabaseEntity> {
            return listOf(
                TransactionGoodsDatabaseEntity(id = "111", name = "Oil", "c1"),
                TransactionGoodsDatabaseEntity(id = "122", name = "Bread", "c2"),
                TransactionGoodsDatabaseEntity(id = "133", name = "Ham", "c2"),
                TransactionGoodsDatabaseEntity(id = "144", name = "Coffee", "c2"),
                TransactionGoodsDatabaseEntity(id = "155", name = "Tea", "c3"),
                TransactionGoodsDatabaseEntity(id = "166", name = "Sugar", "c1"),
                TransactionGoodsDatabaseEntity(id = "177", name = "RTX 3060 TI", "c4"),
                TransactionGoodsDatabaseEntity(id = "188", name = "LG Ultragear 27", "c5"),
            )
        }

        fun testCostList(): List<TransactionMoneyDatabaseEntity> {
            return listOf(
                TransactionMoneyDatabaseEntity(
                    id = "c1", amount = 20.0, currencyId = "qwe1"
                ),
                TransactionMoneyDatabaseEntity(
                    id = "c2", amount = 20.0, currencyId = "qwe2"
                ),
                TransactionMoneyDatabaseEntity(
                    id = "c3", amount = 50.0, currencyId = "qwe3"
                ),
                TransactionMoneyDatabaseEntity(
                    id = "c4", amount = 1030.56, currencyId = "qwe2"
                ),
                TransactionMoneyDatabaseEntity(
                    id = "c5", amount = 100.0, currencyId = "qwe2"
                ),
            )
        }

        fun testTypeData(): List<TransactionTypeDatabaseEntity> {
            return listOf(
                TransactionTypeDatabaseEntity(id = "11", "online"),
                TransactionTypeDatabaseEntity(id = "12", "offline"),
            )
        }

        fun testLedgerData(): List<LedgerEntity> {
            return listOf(
                LedgerEntity(
                    id = "l1", name = "Funerals", LocalDate.of(2023, 9, 12)
                ),
                LedgerEntity(
                    id = "l2", name = "Worker's salary", LocalDate.of(2024, 10, 12)
                )
            )
        }

        fun testLedgerTransactionListData(): List<LedgerTransactionList> {
            return listOf<LedgerTransactionList>(
                LedgerTransactionList(
                    transactionId = "t1",
                    ledgerId = "l1"
                ),
                LedgerTransactionList(
                    transactionId = "t2",
                    ledgerId = "l1"
                ),

                )
        }

    }
}