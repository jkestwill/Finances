package com.jk.transaction_database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jk.transaction_database.transaction.LanguageEntity
import com.jk.transaction_database.transaction.MeasureEntity
import com.jk.transaction_database.transaction.database.TransactionDatabase
import com.jk.transaction_database.transaction.datasource.GoodsLocalDataSource
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
    fun sql_transaction_test_with_error(){
        val langDao = db!!.getLanguageDao()
        val dao = GoodsLocalDataSource(
            db!!.getMeasureDao(),
            db!!.getLanguageDao(),
            db!!.getLanguageMeasureListDao()
        )
        try {
            for (i in testDataLang.indices) {
                dao.insert(testDataMeasure[i], testDataLang[i])
            }
        }catch (e:Throwable){
            println(langDao.getAll())
        }
        Assert.assertEquals(4, 2 + 2)

    }
    @Test
    fun test() {
        val dao = GoodsLocalDataSource(
            db!!.getMeasureDao(),
            db!!.getLanguageDao(),
            db!!.getLanguageMeasureListDao()
        )
        try {
            for (i in testDataLang.indices) {
                dao.insert(testDataMeasure[i], testDataLang[i])
            }
        }catch (e:Throwable){
            println(db!!.getLanguageMeasureListDao().getMeasureRelation())
        }

    }


    companion object {
        val testDataMeasure = listOf(
            MeasureEntity("zxc", "qq"),
            MeasureEntity("zxc", "qq2"),
        )

        val testDataLang = listOf(
            LanguageEntity("qq", "english", "eng", "huuui"),
            LanguageEntity("qq2", "english", "eng", "huuui"),
        )


    }
}