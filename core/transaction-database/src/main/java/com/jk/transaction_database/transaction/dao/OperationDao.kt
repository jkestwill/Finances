package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jk.transaction_database.transaction.entity.OperationEntity
import com.jk.transaction_database.transaction.database.TransactionDatabase
import com.jk.transaction_database.transaction.relations.OperationAndGoodsListRelation
import com.jk.transaction_database.transaction.relations.OperationRelation


@Dao
 abstract class OperationDao internal constructor(
     db: TransactionDatabase
) {

    private val goodsDao: GoodsDao = db.getGoodsDao()
    private val moneyDao: MoneyDao = db.getMoneyDao()
    private val scheduleDao: ScheduleDao = db.getScheduleDao()
    private val categoryDao: CategoryDao = db.getCategoryDao()

    @Insert(entity = OperationEntity::class)
    abstract suspend fun insert(t: OperationEntity)

    @Transaction
    open suspend fun insert(operationRelation: OperationRelation){
        moneyDao.insert(operationRelation.money)
        insert(operationRelation.operationGoodsEntity)

    }
    @Update(entity = OperationEntity::class)
    abstract suspend fun update(t: OperationEntity)

    @Delete(entity = OperationEntity::class)
    abstract suspend fun delete(t: OperationEntity)

    @Query("SELECT * FROM operation")
    abstract suspend fun getAll(): List<OperationEntity>



    @Transaction
    open suspend fun insertGoodsCrossRef(operationRelation:OperationAndGoodsListRelation) {
        goodsDao.insertList(operationRelation.goodsList)
        insert(operationRelation.operation)
    }

}