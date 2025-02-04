package com.jk.transaction_database

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.jk.transaction_database.transaction.dao.CurrencyDao
import com.jk.transaction_database.transaction.dao.GoodsDao
import com.jk.transaction_database.transaction.dao.MeasureDao
import com.jk.transaction_database.transaction.dao.MoneyDao
import com.jk.transaction_database.transaction.database.TransactionDatabaseProvider
import com.jk.transaction_database.transaction.database.transactionDatabaseProvider
import com.jk.transaction_database.transaction.entity.CurrencyEntity
import com.jk.transaction_database.transaction.entity.GoodsEntity
import com.jk.transaction_database.transaction.entity.MeasureEntity
import com.jk.transaction_database.transaction.entity.MoneyEntity
import com.jk.transaction_database.transaction.entity.SpecificationsEntity
import com.jk.transaction_database.transaction.relations.GoodsxSpecificationsxMoneyRelation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(value = AndroidJUnit4::class)
@SmallTest
class GoodsDaoUnitTest {
    private lateinit var goodsDao: GoodsDao
    private lateinit var currencyDao: CurrencyDao
    private lateinit var moneyDao: MoneyDao
    private lateinit var measureDao: MeasureDao
    private lateinit var database: TransactionDatabaseProvider

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
    }


    @Test
    fun updateGods() = runBlocking {
        val currency = CurrencyEntity("b", "BYN")
        val moneyId = MoneyEntity("aojsd", 23.1, currency.id)
        val goodsEntity = GoodsEntity("xc", "Flavor", 1, moneyId.id)
        val randomAmount = Random.nextInt()
        val newGoods = GoodsEntity("xc", "Flavor", randomAmount, moneyId.id)
        goodsDao.update(newGoods)
        val updatedGoods = goodsDao.getById("xc")
        assertTrue(updatedGoods != goodsEntity)
    }


    @Test
    fun insertAndGetEntities() = runBlocking {
        val currency = CurrencyEntity(Random.nextInt().toString(), "BYN")
        val moneyId = MoneyEntity(Random.nextInt().toString(), 23.1, currency.id)
        val goodsEntity = GoodsEntity(Random.nextInt().toString(), "Flavor", 1, moneyId.id)
        currencyDao.insert(currency)
        moneyDao.insert(moneyId)
        goodsDao.insert(goodsEntity)

        val goods = goodsDao.getByIdList(listOf(goodsEntity.id)).map { it.goodsEntity }
        println(goods)
        assertTrue(goods[0] == goodsEntity)
    }

    fun insertGoodxEtc() = runBlocking {
        val currency = CurrencyEntity(Random.nextInt().toString(), "BYN")
        val moneyId = MoneyEntity(Random.nextInt().toString(), 23.1, currency.id)
        val goodsEntity = GoodsEntity(Random.nextInt().toString(), "Flavor", 1, moneyId.id)
        val measure = MeasureEntity("zxc", "Kg")
        try {
            measureDao.insert(measure)
        } catch (e: Throwable) {
        }

        val goods =
            GoodsxSpecificationsxMoneyRelation(
                goodsEntity,
                specifications = randomSpecification(30, measure.id),
                cost = moneyId
            )

        goodsDao.insert()
    }

    private fun randomSpecification(n: Int, measureId: String) = List(n) {
        SpecificationsEntity(
            "$it  ${Random.nextInt()}",
            "Weight",
            Random.nextFloat(),
            measureId
        )
    }

    @After
    fun close() {
        database.db.close()
    }

}