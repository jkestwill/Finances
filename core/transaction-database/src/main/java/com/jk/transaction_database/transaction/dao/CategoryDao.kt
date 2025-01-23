package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

import com.jk.transaction_database.transaction.CategoryEntity

@Dao
interface CategoryDao {
    @Delete(entity = CategoryEntity::class)
    suspend fun delete(t: CategoryEntity)

    @Query("DELETE FROM category " +
            "WHERE category.id IN (:idList)" +
            "")
    suspend fun delete(idList: List<String>)
    @Transaction
   suspend fun checkIfNoExistNInsert(t:CategoryEntity){
        if(getByName(t.name)==null){
            insert(t)
        }
    }

    @Insert(
        entity = CategoryEntity::class,
        onConflict = OnConflictStrategy.ABORT
    )
    suspend fun insert(t: CategoryEntity): Long

    @Insert(
        entity = CategoryEntity::class,
        onConflict = OnConflictStrategy.ABORT
    )
    suspend fun insert(t: List<CategoryEntity>)

    @Update(entity = CategoryEntity::class)
    suspend fun update(t: CategoryEntity)

    @Query(
        value = "SELECT * FROM category  WHERE LOWER(category.name) LIKE '%'||:q||'%'  ORDER BY " +
                " CASE WHEN :isAsc ==1 THEN :sortBy END ASC," +
                " CASE WHEN :isAsc ==0 THEN :sortBy END DESC " +
                "LIMIT :limit OFFSET :offset"
    )
    suspend fun getAll(
        q: String,
        sortBy: String,
        isAsc: Boolean,
        offset: Int,
        limit: Int
    ): List<CategoryEntity>

    @Query(value = "SELECT * FROM category WHERE category.id LIKE :id")
    suspend fun getById(id: String): CategoryEntity?

    @Query(value = "SELECT * FROM category WHERE category.id IN (:id)")
    suspend fun getByListId(id: List<String>): List<CategoryEntity>?

    @Query(value = "SELECT name FROM category WHERE name==:name")
    suspend fun getByName(name: String): String?
}