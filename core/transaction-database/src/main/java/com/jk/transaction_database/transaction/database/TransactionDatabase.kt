package com.jk.transaction_database.transaction.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.RenameTable
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jk.transaction_database.transaction.AddressEntity
import com.jk.transaction_database.transaction.BankEntity
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
import com.jk.transaction_database.transaction.TransactionScheduleEntity
import com.jk.transaction_database.transaction.TransactionTypeDatabaseEntity
import com.jk.transaction_database.transaction.dao.AddressDao
import com.jk.transaction_database.transaction.dao.BankDao
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
import com.jk.transaction_database.transaction.typeconverter.LocalTimeTypeConverter
import java.util.concurrent.Executors


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
        TransactionScheduleEntity::class,
        LedgerEntity::class,
        LedgerTransactionList::class,
        ExchangeRateEntity::class,
        OperationScheduleList::class,
        SpecificationsEntity::class,
        MeasureEntity::class,
        LanguageEntity::class,
        LangMeasureListEntity::class,
        GoodsSpecificationsListEntity::class,
        BankEntity::class,
        AddressEntity::class
    ], version = 24, exportSchema = false
)
@TypeConverters(value = [LocalDateTimeTypeConverter::class, LocalDateTypeConverter::class, LocalTimeTypeConverter::class])
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
    abstract fun getBankDao():BankDao
    abstract fun getAddressDao():AddressDao
}

fun transactionDatabase(context: Context,dbAssetPath:String): TransactionDatabase {
    return Room.databaseBuilder(
        context = context,
        klass = TransactionDatabase::class.java,
        name = "transaction_db"
    ).fallbackToDestructiveMigration()
        .setQueryCallback({ sqlQuery, bindArgs ->
            Log.e("DATABASE_LOG", "${sqlQuery} ## Args:${bindArgs} ")
        }, executor = Executors.newSingleThreadExecutor())
        .createFromAsset(dbAssetPath)
        .build()
        .apply {
            query(query = "PRAGMA foreign_keys=ON", args = null)
        }
}