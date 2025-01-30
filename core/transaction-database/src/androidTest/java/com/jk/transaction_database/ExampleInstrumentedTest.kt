package com.jk.transaction_database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jk.transaction_database.transaction.entity.MeasureEntity
import com.jk.transaction_database.transaction.database.TransactionDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GoodsTest {

    private var db: TransactionDatabase? = null

    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2)
    }

    @Before
    fun goodsDataSource() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, TransactionDatabase::class.java).build()
        if (db != null)
            checkNotNull(db)

    }

    @Test
    fun sql_transaction_test_with_error() = runBlocking {
        val scope = CoroutineScope(Job())
        val langDao = db!!.getLanguageDao()
        val dao = db!!.getGoodsDao()
        scope.launch {
            try {
//                for (i in goodsRelation.indices) {
//                    dao.insert(goodsRelation = goodsRelation[0])
//                }
                //  Log.e("zxc", "sql_transaction_test_with_error: ${db!!.getGoodsDao().getAll()}")
            } catch (e: Throwable) {
                // println(db!!.getGoodsDao().getAll())
            } finally {

                //   Log.e("zxc", "sql_transaction_test_with_error: ${db!!.getGoodsDao().getAll()}")

            }


            Assert.assertEquals(4, 2 + 2)
        }.join()
        // Log.e("zxc", "sql_transaction_test_with_error: ${db!!.getGoodsDao().getAll()}")
        //  println(db!!.getGoodsDao().getAll())

    }

    @Test
    fun test() {
        val scope = CoroutineScope(Job())
        val dao = db!!.getGoodsDao()

//        scope.launch {
//            try {
//                for (i in goodsRelation.indices) {
//                    dao.insert(goodsRelation = goodsRelation[0])
//                }
//            } catch (e: Throwable) {
//                println(db!!.getLanguageMeasureListDao().getMeasureRelation())
//            }
//        }

    }


    companion object {
        val testDataMeasure = listOf(
            MeasureEntity("zxc", "qq"),
            MeasureEntity("zxc", "qq2"),
        )

//        val testDataLang = listOf(
//            LanguageEntity("qq", "english", "eng", "huuui"),
//            LanguageEntity("qq2", "english", "eng", "huuui"),

//        val goodsRelation = listOf(
//            GoodsRelation(
//                goodsEntity = GoodsEntity("qq", "bread", 1,"qq"),
//                specificationList = listOf(
//                    SpecificationRelation(
//                        specificationEntity = SpecificationsEntity("ss", "weight", 10f, "mm"),
//                        measure = MeasureRelation(
//                            MeasureEntity("mm", "ll"),
//                            lang = LanguageEntity("ll", "english", "ENG", "kg")
//                        )
//                    )
//                ),
//                cost = MoneyRelation(
//                    money = MoneyEntity("mo", 2.0, "cc"),
//                    currency = TransactionCurrencyDatabaseEntity("cc", "BYN")
//                )
//            )
//        )

    }
}