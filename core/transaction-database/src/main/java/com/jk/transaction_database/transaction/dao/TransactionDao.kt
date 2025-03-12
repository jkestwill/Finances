package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.room.Update
import com.jk.transaction_database.transaction.entity.OperationEntity
import com.jk.transaction_database.transaction.entity.TransactionEntity
import com.jk.transaction_database.transaction.database.TransactionDatabase
import com.jk.transaction_database.transaction.entity.GoodsEntity
import com.jk.transaction_database.transaction.list.OperationGoodsListEntity
import com.jk.transaction_database.transaction.preview.TransactionPreviewRelation
import com.jk.transaction_database.transaction.relations.TransactionxCategoriesxTypexGoods
import java.time.LocalDateTime

@Dao
abstract class TransactionDao internal constructor(
    private val db: TransactionDatabase,
) {
    private val operationDao: OperationDao = db.getOperationDao()
    private val typeDao: TypeDao = db.getTypeDao()
    private val goodsDao: GoodsDao = db.getGoodsDao()
    private val categoryDao: CategoryDao = db.getCategoryDao()
    private val moneyAccountDao: MoneyAccountDao = db.getMoneyAccountDao()
    private val goodsOperationDao: OperationGoodsListDao = db.getOperationGoodsListDao()

    @Transaction
    @Query(value = "SELECT * FROM `transaction`")
    abstract suspend fun getAll(): List<TransactionEntity>

    @Transaction
    @Query("SELECT * FROM full_transaction ")
    public abstract fun getRelation(): List<TransactionxCategoriesxTypexGoods>

    @Query("SELECT * FROM `transaction`")
    abstract suspend fun getTransactionPreview(): List<TransactionPreviewRelation>

    @Insert(entity = TransactionEntity::class, onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insert(transaction: TransactionEntity)

    @Transaction
    open suspend fun insert(transaction: TransactionxCategoriesxTypexGoods) {
        operationDao.insert(transaction.operation)
        categoryDao.insert(transaction.categoryList)
        for (goods in transaction.goodsList) {
            goodsDao.insert(GoodsEntity(goods.goodsId,goods.goodsName))
            goodsOperationDao.insert(
                OperationGoodsListEntity(
                    operationId = transaction.operation.operationEntity.id,
                    goodsId = goods.goodsId,
                    amount = goods.amount
                )
            )
        }
        typeDao.insertIfNotExist(transaction.type)
        moneyAccountDao.updateAmount(
            moneyAccountId = transaction.transactionEntity.moneyAccountId,
            amount = transaction.operation.money.amount
        )
        insert(transaction.transactionEntity)

    }

    @Update(entity = TransactionEntity::class, onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun update(transaction: TransactionEntity)

    @Delete(entity = OperationEntity::class)
    abstract suspend fun delete(operation: OperationEntity)


    @Transaction
    @Query(
        value = "SELECT " +
                " * FROM `category_list` " +
                "INNER JOIN category ON category_list.category_id == category.id " +
                "INNER JOIN `operation` ON category_list.operation_id==operation.id " +
                "INNER JOIN `transaction`ON operation.id == `transaction`.operation_id " +
                "INNER JOIN type ON type.id == `transaction`.type_id " +
                "INNER JOIN money ON operation.money_id == money.id " +
                "INNER JOIN currency ON money.currency_id == currency.id " +
                "WHERE category.id == :categoryId " +
                "AND operation.name LIKE '%'||:q||'%'" +
                "ORDER BY " +
                "CASE WHEN :isAsc==1 THEN :orderBy END ASC, " +
                "CASE WHEN :isAsc==0 THEN :orderBy END DESC " +
                "LIMIT :limit OFFSET :offset "

    )
    abstract suspend fun getTransactionPreviewListByCategoryId(
        categoryId: String,
        q: String,
        orderBy: String,
        isAsc: Boolean,
        offset: Int,
        limit: Int
    ): List<TransactionPreviewRelation>

    // get expenses with exchange rate
    @Query(
        value = "SELECT SUM(amount*ex.rate) FROM category_list " +
                "INNER JOIN category ON category_list.category_id == category.id " +
                "INNER JOIN `operation` ON category_list.operation_id==operation.id " +
                "INNER JOIN `transaction`ON operation.id == `transaction`.operation_id " +
                "INNER JOIN money ON operation.money_id == money.id " +
                "INNER JOIN currency ON money.currency_id == currency.id " +
                "LEFT JOIN exchange_rate ex ON ex.currency_from_id == currency.id " +
                "LEFT JOIN exchange_rate ex2 ON ex2.currency_to_id == :currencyTo " +
                "WHERE `transaction`.date >= :dateFrom AND `transaction`.date <= :dateTo " +
                " "
    )
    abstract fun getExpenses(
        dateFrom: LocalDateTime,
        dateTo: LocalDateTime,
        currencyTo: String
    ): Double
}