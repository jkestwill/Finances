package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jk.transaction_database.transaction.OperationDatabaseEntity
import com.jk.transaction_database.transaction.TransactionDatabaseEntity
import com.jk.transaction_database.transaction.preview.TransactionPreviewDatabase
import com.jk.transaction_database.transaction.relations.TransactionRelation
import java.time.LocalDateTime

@Dao
interface TransactionDao {

    @Transaction
    @Query(value = "SELECT * FROM `transaction`")
    suspend fun  getAll(): List<TransactionDatabaseEntity>

    @Transaction
    @Query(value = "SELECT * FROM `transaction`")
    suspend fun getRelation():List<TransactionRelation>

    @Insert(entity = TransactionDatabaseEntity::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(transaction: TransactionDatabaseEntity)

    @Update(entity = TransactionDatabaseEntity::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun update(transaction: TransactionDatabaseEntity)

    @Delete(entity = OperationDatabaseEntity::class)
    suspend fun delete(operation: OperationDatabaseEntity)

    @Transaction
    @Query(value="SELECT "+
            " * FROM `category_list` " +
            "INNER JOIN category ON category_list.category_id == category.id " +
            "INNER JOIN `operation` ON category_list.operation_id==operation.id " +
            "INNER JOIN `transaction`ON operation.id == `transaction`.operation_id " +
            "INNER JOIN type ON type.id == `transaction`.type_id " +
            "INNER JOIN money ON operation.money_id == money.id " +
            "INNER JOIN currency ON money.currency_id == currency.id " +
            "WHERE category.id == :categoryId " +
            "ORDER BY " +
            "CASE WHEN :isAsc==1 THEN :orderBy END ASC, " +
            "CASE WHEN :isAsc==0 THEN :orderBy END DESC")
    suspend fun getTransactionPreviewListByCategoryId(categoryId:String, orderBy:String, isAsc:Boolean):List<TransactionPreviewDatabase>
// get expenses with exchange rate given in params
    @Query(value="SELECT SUM(amount*ex.rate) FROM category_list " +
            "INNER JOIN category ON category_list.category_id == category.id " +
            "INNER JOIN `operation` ON category_list.operation_id==operation.id " +
            "INNER JOIN `transaction`ON operation.id == `transaction`.operation_id " +
            "INNER JOIN money ON operation.money_id == money.id " +
            "INNER JOIN currency ON money.currency_id == currency.id " +
            "LEFT JOIN exchange_rate ex ON ex.currency_from_id == currency.id " +
            "LEFT JOIN exchange_rate ex2 ON ex2.currency_to_id == :currencyTo " +
            "WHERE `transaction`.date >= :dateFrom AND `transaction`.date <= :dateTo " +
            "AND category.is_expenses==1 "

    )
     fun getExpenses(dateFrom:LocalDateTime,dateTo: LocalDateTime,currencyTo:String):Double
}