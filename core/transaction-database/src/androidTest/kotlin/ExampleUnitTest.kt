package com.jk.transaction_database

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.jk.transaction_database.transaction.dao.GoodsDao
import com.jk.transaction_database.transaction.database.TransactionDatabaseProvider
import com.jk.transaction_database.transaction.database.transactionDatabaseProvider
import com.jk.transaction_database.transaction.entity.CurrencyEntity
import com.jk.transaction_database.transaction.entity.GoodsEntity
import com.jk.transaction_database.transaction.entity.MoneyEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(value =  AndroidJUnit4::class)
@SmallTest
class ExampleUnitTest {
    private lateinit var goodsDao: GoodsDao
    private lateinit var database: TransactionDatabaseProvider

    @Before
    fun init() {
        database =
            transactionDatabaseProvider(context = ApplicationProvider.getApplicationContext(), "transaction_db.db")
        goodsDao = database.getGoodsDao()
    }
//
//    fun `get all goodsxspecificationxMoneyRelation`() {
//        // todo test get
//    }
//
//    fun `add all goodsxspecificationxMoneyRelation`() {

  //  }
    @Test
    fun insertEntity() = runBlocking {
        val currency = CurrencyEntity("b", "BYN")
        val moneyId = MoneyEntity("aojsd", 23.1, currency.id)
        val goodsEntity = GoodsEntity("", "Flavor", 1, moneyId.id)
        goodsDao.insert(goodsEntity)
        val goods =  goodsDao.getByIdList(listOf(goodsEntity.id)).map { it.goodsEntity }
       assertTrue(goods[0] == goodsEntity)
    }

    @After
    fun close() {
       // database.db.close()
    }

}