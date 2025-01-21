package com.jk.transaction_database.transaction.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
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
import com.jk.transaction_database.transaction.GoodsEntity
import com.jk.transaction_database.transaction.MoneyEntity
import com.jk.transaction_database.transaction.TransactionScheduleEntity
import com.jk.transaction_database.transaction.TransactionTypeEntity
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
        GoodsEntity::class,
        MoneyEntity::class,
        TransactionTypeEntity::class,
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
    ], version = 26, exportSchema = true, autoMigrations = []
)
@TypeConverters(value = [LocalDateTimeTypeConverter::class, LocalDateTypeConverter::class, LocalTimeTypeConverter::class])
internal abstract class TransactionDatabase : RoomDatabase() {

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


fun transactionDatabaseProvider(context: Context, dbAssetPath:String): TransactionDatabaseProvider {
    val db = Room.databaseBuilder(
        context = context,
        klass = TransactionDatabase::class.java,
        name = "transaction_db"
    )
        .setQueryCallback({ sqlQuery, bindArgs ->
            Log.e("DATABASE_LOG", "${sqlQuery} ## Args: ${bindArgs} ")
        }, executor = Executors.newSingleThreadExecutor())
        .createFromAsset(dbAssetPath)
        .build()
        .apply {
            query(query = "SELECT * FROM currency", args = null)
            query(query = "PRAGMA foreign_keys=ON", args = null)
        }

    return TransactionDatabaseProvider(db)
}
// костыль чтобы не делать руму как апи
class TransactionDatabaseProvider internal constructor (internal val db:TransactionDatabase){
    fun getCategoryDao(): CategoryDao {
        return db.getCategoryDao()
    }
    fun getTransactionDao(): TransactionDao{
       return db.getTransactionDao()
    }

    fun getGoodsDao(): GoodsDao{
       return db.getGoodsDao()
    }
    fun getMeasureDao(): MeasureDao{
        return db.getMeasureDao()
    }
    fun getLanguageDao(): LanguageDao{
        return db.getLanguageDao()
    }

    fun getLanguageMeasureListDao(): LanguageMeasureListDao{
         return db.getLanguageMeasureListDao()
    }

    fun getMoneyDao(): MoneyDao{
        return db.getMoneyDao()
    }
    fun getOperationDao(): OperationDao{
        return db.getOperationDao()
    }

     fun getScheduleDao(): ScheduleDao{
         return db.getScheduleDao()
     }

    fun getTypeDao(): TypeDao{
        return db.getTypeDao()
    }

    fun getCurrencyDao(): CurrencyDao{
        return db.getCurrencyDao()
    }

     fun getOperationCategoryDao(): OperationCategoryDao{
         return db.getOperationCategoryDao()
     }

     fun getTransactionGoodsListDao(): TransactionGoodsListDao{
         return db.getTransactionGoodsListDao()
     }
     fun getSpecificationDao(): SpecificationDao{
         return db.getGoodsDao()
     }
     fun getLedgerDao(): LedgerDao{
         return db.getLedgerDao()
     }

     fun getLedgerTransactionListDao(): LedgerTransactionListDao{
         return db.getLedgerTransactionListDao()
     }

     fun getExchangeRateDao(): ExchangeRateDao{
         return db.getExchangeRateDao()
     }

     fun getGoodsSpecificationDao(): GoodsSpecificationDao{
         return db.getGoodsSpecificationDao()
     }
    fun getBankDao():BankDao{
        return db.getBankDao()
    }

    fun getAddressDao():AddressDao{
        return db.getAddressDao()
    }

}



