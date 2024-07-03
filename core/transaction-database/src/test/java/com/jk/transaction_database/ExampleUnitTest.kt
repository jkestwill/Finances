package com.jk.transaction_database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jk.transaction_database.transaction.LanguageEntity
import com.jk.transaction_database.transaction.MeasureEntity
import com.jk.transaction_database.transaction.database.TransactionDatabase
import com.jk.transaction_database.transaction.database.transactionDatabase
import com.jk.transaction_database.transaction.datasource.GoodsLocalDataSource
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleUnitTest {

    private var db: TransactionDatabase? = null

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Before
    fun goodsDataSource() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, TransactionDatabase::class.java).build()
        if (db != null)
            checkNotNull(db)

    }

    @Test
    fun test() {
        val dao = GoodsLocalDataSource(
            db!!.getMeasureDao(),
            db!!.getLanguageDao(),
            db!!.getLanguageMeasureListDao()
        )
        println(db!!.getLanguageMeasureListDao().getMeasureRelation())
        assertEquals(dao.insert(testDataMeasure[0], testDataLang[0]), testDataLang[0])


    }


    companion object {
        val testDataMeasure = listOf(
            MeasureEntity("zxc", "qwe"),
            MeasureEntity("zxc", "qwe"),
        )

        val testDataLang = listOf(
            LanguageEntity("qq", "english", "eng", "huuui"),
            LanguageEntity("qq", "english", "eng", "huuui"),
        )


    }
}