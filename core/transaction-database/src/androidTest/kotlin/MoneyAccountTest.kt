package com.jk.transaction_database

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.jk.transaction_database.transaction.dao.CurrencyDao
import com.jk.transaction_database.transaction.dao.MoneyAccountDao
import com.jk.transaction_database.transaction.dao.MoneyDao
import com.jk.transaction_database.transaction.database.TransactionDatabaseProvider
import com.jk.transaction_database.transaction.database.transactionDatabaseProvider
import com.jk.transaction_database.transaction.entity.MoneyEntity
import com.jk.transaction_database.transaction.relations.MoneyAccountDTO
import com.jk.transaction_database.transaction.relations.MoneyDTO
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.util.UUID

@RunWith(AndroidJUnit4::class)
@SmallTest
class MoneyAccountTest {
    private lateinit var moneyDao: MoneyDao
    private lateinit var moneyAccountDao: MoneyAccountDao
    private lateinit var currencyDao: CurrencyDao
    private lateinit var database: TransactionDatabaseProvider

    @Before
    fun init() {
        database =
            transactionDatabaseProvider(
                context = ApplicationProvider.getApplicationContext(),
                "transaction_db.db"
            )
        moneyDao = database.getMoneyDao()
        moneyAccountDao = database.getMoneyAccountDao()
        currencyDao = database.getCurrencyDao()
    }

    @Test
    fun insert_Success() = runBlocking {
        moneyAccountDao.deleteAll()
        val currency = currencyDao.getAll()
        val randomId = UUID.randomUUID()
        val testData = MoneyAccountDTO(
            UUID.randomUUID().toString(),
            "picex",
            MoneyDTO(
                MoneyEntity(
                    id = randomId.toString(),
                    amount = 0.9,
                    currencyId = currency[0].id,
                    date = null
                ),
                currency[0]
            )
        )
        moneyAccountDao.insert(testData)
        val result = moneyAccountDao.getAllDTO()
        println("test data ${testData}")
        println(result)
        assertTrue(result.contains (testData))

    }

}