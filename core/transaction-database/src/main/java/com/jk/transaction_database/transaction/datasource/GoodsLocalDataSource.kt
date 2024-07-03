package com.jk.transaction_database.transaction.datasource

import androidx.room.Transaction
import com.jk.transaction_database.transaction.dao.GoodsDao
import com.jk.transaction_database.transaction.dao.LanguageDao
import com.jk.transaction_database.transaction.dao.LanguageMeasureListDao
import com.jk.transaction_database.transaction.dao.MeasureDao
import com.jk.transaction_database.transaction.dao.MoneyDao
import com.jk.transaction_database.transaction.relations.GoodsRelation

class GoodsLocalDataSource(
    private val measureDao: MeasureDao,
    private val languageDao: LanguageDao,
    private val languageMeasureListDao: LanguageMeasureListDao,
    private val moneyDao: MoneyDao,
    private val goodsDao: GoodsDao
) {
    @Transaction
    suspend fun insert(goodsRelation: GoodsRelation) {
        for (i in goodsRelation.specificationList) {
            val lang = i.measure.lang
            val measure = i.measure.measureEntity
            measureDao.insert(measure)
            languageDao.insert(lang)
            languageMeasureListDao.insert(measure.id, lang.id)
        }

        moneyDao.insert(goodsRelation.cost.money)
        goodsDao.insert(goodsRelation.goodsEntity)
    }
}