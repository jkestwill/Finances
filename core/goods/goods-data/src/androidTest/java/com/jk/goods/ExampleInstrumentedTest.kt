package com.jk.goods

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jk.common_goods_data.Goods
import com.jk.common_goods_data.MoneyAndDate
import com.jk.money_common_data.Currency
import com.jk.money_common_data.Money
import com.jk.transaction_database.transaction.dao.CurrencyDao
import com.jk.transaction_database.transaction.dao.GoodsDao
import com.jk.transaction_database.transaction.dao.MeasureDao
import com.jk.transaction_database.transaction.dao.MoneyDao
import com.jk.transaction_database.transaction.dao.SpecificationDao
import com.jk.transaction_database.transaction.database.TransactionDatabaseProvider
import com.jk.transaction_database.transaction.database.transactionDatabaseProvider
import com.jk.transaction_database.transaction.entity.CurrencyEntity
import com.jk.transaction_database.transaction.entity.MeasureEntity
import com.jk.transaction_database.transaction.entity.MoneyEntity
import com.jk.transaction_database.transaction.entity.SpecificationsEntity
import junit.framework.AssertionFailedError
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {


    lateinit var db: TransactionDatabaseProvider

    lateinit var goodsDao: GoodsDao

    lateinit var specificationDao: SpecificationDao

    lateinit var measureDao: MeasureDao

    lateinit var specificationMapper: SpecificationsMapper

    lateinit var moneyDao: MoneyDao

    lateinit var currencyDao: CurrencyDao

    lateinit var goodsMapper: GoodsMapper

    lateinit var goodsRepository: GoodsRepository

    @Before
    fun init() {
        db = transactionDatabaseProvider(
            context = ApplicationProvider.getApplicationContext(),
            "transaction_db.db"
        )
        moneyDao = db.getMoneyDao()
        currencyDao = db.getCurrencyDao()
        goodsDao = db.getGoodsDao()
        goodsMapper = GoodsMapperImpl()
        specificationMapper = SpecificationsMapperImpl()
        specificationDao = db.getSpecificationDao()
        measureDao = db.getMeasureDao()
        // todo как сука инжектнуть
        //goodsRepository=GoodsRepository(goodsDao,)
    }

    @Test
    fun GoodsRepository_getList_Success() = runBlocking {
        db.clear()
        // todo проверить на соответствие записанных данных
        val measure = MeasureEntity(id = "mes", "kg")
        val specifiacationsList = List(30) {
            SpecificationsEntity("$it", "text$it", measureId = measure.id, amount = 12f)
        }
        val currency = CurrencyEntity("cur", name = "USD")
        val moneyEntity = MoneyEntity(id = "mon", 12.0, currency.id,date= LocalDate.now())
        val money = MoneyAndDate(id = "m", moneyEntity.amount, date = LocalDate.now(), currency =  Currency(currency.id, currency.name))
        currencyDao.insert(currency)
        // moneyDao.insert(moneyEntity)
        measureDao.insert(measure)

        // specificationDao.insert(specifiacationsList)

        val goodsList = List(30) {
            Goods(
                "${it + 30}",
                name = "goods${it}",
                amount = 10,
                specifications = specifiacationsList.map { specificationMapper.toSpecification(it) },
                cost = listOf(money)
            )
        }

        goodsDao.insertGoodsRelation(goodsList.map {
            goodsMapper.toGoodsxSpecificationsxMoneyRelation(
                it
            )
        })
        val result = goodsDao.getByIdList(goodsList.map { it.id })
            .map { it.copy(specifications = it.specifications.sortedBy { s -> s.id }) }
            .sortedBy { it.goodsEntity.id }
        val sortedGoodsList = goodsList.map { goodsMapper.toGoodsxSpecificationsxMoneyRelation(it) }
            .map { it.copy(specifications = it.specifications.sortedBy { s -> s.id }) }
            .sortedBy { it.goodsEntity.id }

        assertTrue(sortedGoodsList == result)

    }

    @After
    fun after() {
        db.clear()
    }
}
