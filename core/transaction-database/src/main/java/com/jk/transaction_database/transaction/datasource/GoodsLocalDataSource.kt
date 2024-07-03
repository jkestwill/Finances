package com.jk.transaction_database.transaction.datasource

import androidx.room.Transaction
import com.jk.transaction_database.transaction.LanguageEntity
import com.jk.transaction_database.transaction.MeasureEntity
import com.jk.transaction_database.transaction.dao.LanguageDao
import com.jk.transaction_database.transaction.dao.LanguageMeasureListDao
import com.jk.transaction_database.transaction.dao.MeasureDao

class GoodsLocalDataSource(
    private val measureDao: MeasureDao,
    private val languageDao: LanguageDao,
    private val languageMeasureListDao: LanguageMeasureListDao
) {
    @Transaction
    fun insert(measureEntity: MeasureEntity, languageEntity: LanguageEntity): String {


        measureDao.insert(measureEntity)
        languageDao.insert(languageEntity)
        languageMeasureListDao.insert(measureEntity.id, languageEntity.id)
        return measureEntity.langId
    }
}