package com.jk.transaction_database

import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jk.transaction_database.transaction.dao.CurrencyDao
import com.jk.transaction_database.transaction.dao.GoodsDao
import com.jk.transaction_database.transaction.dao.GoodsSpecificationDao
import com.jk.transaction_database.transaction.dao.MeasureDao
import com.jk.transaction_database.transaction.dao.MoneyDao
import com.jk.transaction_database.transaction.dao.SpecificationDao
import com.jk.transaction_database.transaction.database.TransactionDatabaseProvider
import com.jk.transaction_database.transaction.database.transactionDatabaseProvider
import com.jk.transaction_database.transaction.entity.CurrencyEntity
import com.jk.transaction_database.transaction.entity.GoodsEntity
import com.jk.transaction_database.transaction.entity.MeasureEntity
import com.jk.transaction_database.transaction.entity.MoneyEntity
import com.jk.transaction_database.transaction.entity.SpecificationsEntity
import com.jk.transaction_database.transaction.list.GoodsSpecificationsListEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class SpecificationTest {
    private lateinit var database: TransactionDatabaseProvider
    private lateinit var specificationDao: SpecificationDao
    private lateinit var goodsDao: GoodsDao
    private lateinit var currencyDao: CurrencyDao
    private lateinit var moneyDao: MoneyDao
    private lateinit var measureDao: MeasureDao
    private lateinit var goodsSpecificationDao: GoodsSpecificationDao
    private val TAG = "SpecificationTest"

    @Before
    fun init() {
        database = transactionDatabaseProvider(ApplicationProvider.getApplicationContext(), "")
        specificationDao = database.getSpecificationDao()
        measureDao = database.getMeasureDao()
        goodsSpecificationDao = database.getGoodsSpecificationDao()
        goodsDao = database.getGoodsDao()
        currencyDao = database.getCurrencyDao()
        moneyDao = database.getMoneyDao()

    }

    @Test
    fun insert_test() = runBlocking {
        database.db.clearAllTables()
        Log.e("TAG", "insert_test: ${measureDao.getAll()}")
        val measure = MeasureEntity("c", "Kg")
        measureDao.insert(measure)
        val specs = randomSpecification(11000, measure.id)
        specificationDao.insert(specs)
        val dbList = specificationDao.getAll()
        Log.e(TAG, "all specs:${dbList.size} ${specs.size} ")
        assertTrue(specs == dbList)

    }

    @Test
     fun insert_spec_goods() = runBlocking {
        database.db.clearAllTables()
        val currency = CurrencyEntity(Random.nextInt().toString(), "BYN")
        val moneyId = MoneyEntity(Random.nextInt().toString(), 23.1, currency.id)
        val goodsEntity = GoodsEntity(Random.nextInt().toString(), "Flavor", 1, moneyId.id)
        val measure = MeasureEntity("c", "Kg")
        val specification = randomSpecification(30, measureId = measure.id)
        measureDao.insert(measure)
        specificationDao.insert(specification)
        currencyDao.insert(currency)
        moneyDao.insert(moneyId)
        goodsDao.insert(goodsEntity)
        for (i in specification) {
            goodsSpecificationDao.insert(
                GoodsSpecificationsListEntity(
                    goodsId = goodsEntity.id,
                    specificationsId = i.id
                )
            )
        }
        val specList = goodsSpecificationDao.getAll()
        assertTrue(specList.size == specification.size)
    }

    @After
    fun close() {

        database.db.close()
    }

    private fun randomSpecification(n: Int, measureId: String) = List(n) {
        SpecificationsEntity(
            "$it${Random.nextInt()}",
            "Weight",
            Random.nextFloat(),
            measureId
        )
    }
}