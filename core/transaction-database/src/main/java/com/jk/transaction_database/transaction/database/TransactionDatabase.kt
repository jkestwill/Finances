package com.jk.transaction_database.transaction.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jk.transaction_database.transaction.ExchangeRateEntity
import com.jk.transaction_database.transaction.LanguageEntity
import com.jk.transaction_database.transaction.LedgerEntity
import com.jk.transaction_database.transaction.MeasureEntity
import com.jk.transaction_database.transaction.OperationEntity
import com.jk.transaction_database.transaction.SpecificationsEntity
import com.jk.transaction_database.transaction.TransactionCategoryDatabaseEntity
import com.jk.transaction_database.transaction.TransactionCurrencyDatabaseEntity
import com.jk.transaction_database.transaction.TransactionEntity
import com.jk.transaction_database.transaction.TransactionGoodsDatabaseEntity
import com.jk.transaction_database.transaction.TransactionMoneyDatabaseEntity
import com.jk.transaction_database.transaction.TransactionScheduleDatabaseEntity
import com.jk.transaction_database.transaction.TransactionTypeDatabaseEntity
import com.jk.transaction_database.transaction.dao.CategoryDao
import com.jk.transaction_database.transaction.dao.CurrencyDao
import com.jk.transaction_database.transaction.dao.ExchangeRateDao
import com.jk.transaction_database.transaction.dao.GoodsDao
import com.jk.transaction_database.transaction.dao.GoodsSpecificationDao
import com.jk.transaction_database.transaction.dao.LanguageDao
import com.jk.transaction_database.transaction.dao.LanguageMeasureListDao
import com.jk.transaction_database.transaction.dao.LedgerDao
import com.jk.transaction_database.transaction.dao.LedgerTransactionListDao
import com.jk.transaction_database.transaction.dao.MeasureDao
import com.jk.transaction_database.transaction.dao.MoneyDao
import com.jk.transaction_database.transaction.dao.OperationCategoryDao
import com.jk.transaction_database.transaction.dao.OperationDao
import com.jk.transaction_database.transaction.dao.ScheduleDao
import com.jk.transaction_database.transaction.dao.SpecificationDao
import com.jk.transaction_database.transaction.dao.TransactionDao
import com.jk.transaction_database.transaction.dao.TransactionGoodsListDao
import com.jk.transaction_database.transaction.dao.TypeDao
import com.jk.transaction_database.transaction.list.GoodsSpecificationsListEntity
import com.jk.transaction_database.transaction.list.LangMeasureListEntity
import com.jk.transaction_database.transaction.list.LedgerTransactionList
import com.jk.transaction_database.transaction.list.OperationCategoryList
import com.jk.transaction_database.transaction.list.OperationGoodsListEntity
import com.jk.transaction_database.transaction.list.OperationScheduleList
import com.jk.transaction_database.transaction.typeconverter.LocalDateTimeTypeConverter
import com.jk.transaction_database.transaction.typeconverter.LocalDateTypeConverter


@Database(
    entities = [
        OperationEntity::class,
        TransactionCategoryDatabaseEntity::class,
        TransactionCurrencyDatabaseEntity::class,
        TransactionGoodsDatabaseEntity::class,
        TransactionMoneyDatabaseEntity::class,
        TransactionTypeDatabaseEntity::class,
        TransactionEntity::class,
        OperationGoodsListEntity::class,
        OperationCategoryList::class,
        TransactionScheduleDatabaseEntity::class,
        LedgerEntity::class,
        LedgerTransactionList::class,
        ExchangeRateEntity::class,
        OperationScheduleList::class,
        SpecificationsEntity::class,
        MeasureEntity::class,
        LanguageEntity::class,
        LangMeasureListEntity::class,
        GoodsSpecificationsListEntity::class
    ], version = 21, exportSchema = false
)
@TypeConverters(value = [LocalDateTimeTypeConverter::class, LocalDateTypeConverter::class])
abstract class TransactionDatabase : RoomDatabase() {

    abstract fun getTransactionDao(): TransactionDao

    abstract fun getCategoryDao(): CategoryDao

    abstract fun getGoodsDao(): GoodsDao
    abstract fun getMeasureDao(): MeasureDao
    abstract fun getLanguageDao(): LanguageDao
    abstract fun getLanguageMeasureListDao(): LanguageMeasureListDao

    abstract fun getMoneyDao(): MoneyDao

    abstract fun getOperationDao(): OperationDao

    abstract fun getScheduleDao(): ScheduleDao

    abstract fun getTypeDao(): TypeDao

    abstract fun getCurrencyDao(): CurrencyDao

    abstract fun getOperationCategoryDao(): OperationCategoryDao

    abstract fun getTransactionGoodsListDao(): TransactionGoodsListDao
    abstract fun getSpecificationDao(): SpecificationDao
    abstract fun getLedgerDao(): LedgerDao

    abstract fun getLedgerTransactionListDao(): LedgerTransactionListDao

    abstract fun getExchangeRateDao(): ExchangeRateDao

    abstract fun getGoodsSpecificationDao(): GoodsSpecificationDao
}

fun transactionDatabase(context: Context): TransactionDatabase {
    return Room.databaseBuilder(
        context = context,
        klass = TransactionDatabase::class.java,
        name = "transaction_db"
    ).fallbackToDestructiveMigration()
        .build()
        .apply {
            query(query = "PRAGMA foreign_keys=ON", args = null)
        }
}