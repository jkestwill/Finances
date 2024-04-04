package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

import com.jk.transaction_database.transaction.TransactionCategoryDatabaseEntity

@Dao
interface CategoryDao {
    @Delete(entity = TransactionCategoryDatabaseEntity::class)
    suspend fun delete(t: TransactionCategoryDatabaseEntity)

    @Insert(
        entity = TransactionCategoryDatabaseEntity::class,
        onConflict = OnConflictStrategy.ABORT
    )
    suspend fun insert(t: TransactionCategoryDatabaseEntity): Long

    @Insert(
        entity = TransactionCategoryDatabaseEntity::class,
        onConflict = OnConflictStrategy.ABORT
    )
    suspend fun insert(t: List<TransactionCategoryDatabaseEntity>)

    @Update(entity = TransactionCategoryDatabaseEntity::class)
    suspend fun update(t: TransactionCategoryDatabaseEntity)

    @Query(
        value = "SELECT * FROM category  WHERE LOWER(category.name) LIKE '%'||:q||'%'  ORDER BY " +
                " CASE WHEN :isAsc ==1 THEN :sortBy END ASC," +
                " CASE WHEN :isAsc ==0 THEN :sortBy END DESC " +
                "LIMIT :limit OFFSET :offset"
    )
    suspend fun getAll(
        q:String,
        sortBy: String,
        isAsc: Boolean,
        offset:Int,
        limit:Int
    ): List<TransactionCategoryDatabaseEntity>

    @Query(value = "SELECT * FROM category WHERE category.id LIKE :id")
    suspend fun getById(id: String): TransactionCategoryDatabaseEntity?
}