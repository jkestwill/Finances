package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jk.transaction_database.transaction.OperationEntity
import com.jk.transaction_database.transaction.database.TransactionDatabase
import com.jk.transaction_database.transaction.relations.GoodsRelation
import com.jk.transaction_database.transaction.relations.OperationRelation

@Dao
abstract class OperationDao(

    private val db: TransactionDatabase
) {
    private val goodsDao: GoodsDao = db.getGoodsDao()
    private val moneyDao: MoneyDao = db.getMoneyDao()
    private val scheduleDao: ScheduleDao = db.getScheduleDao()
    private val categoryDao: CategoryDao = db.getCategoryDao()

    @Insert(entity = OperationEntity::class)
    abstract suspend fun insert(t: OperationEntity)

    @Update(entity = OperationEntity::class)
    abstract suspend fun update(t: OperationEntity)

    @Delete(entity = OperationEntity::class)
    abstract suspend fun delete(t: OperationEntity)

    @Query("SELECT * FROM operation")
    abstract suspend fun getAll(): List<OperationEntity>

    @Transaction
    @Query("SELECT * FROM operation")
    abstract fun getRelation(): List<OperationRelation>

    @Transaction
    open suspend fun insert(operationRelation: OperationRelation) {
        moneyDao.insert(operationRelation.cost.money)
        scheduleDao.insert(operationRelation.schedule)
        moneyDao.insert(operationRelation.cost)
        for (category in operationRelation.categoryList) {
            categoryDao.checkIfNoExistNInsert(category)
        }
        for (goods in operationRelation.goodsList) {
            goodsDao.insert(goods)
        }
        insert(operationRelation.operation)
    }
}