package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jk.transaction_database.transaction.list.OperationCategoryList

@Dao
interface OperationCategoryDao {

    @Delete(entity = OperationCategoryList::class)
    suspend fun delete(t: OperationCategoryList)

    @Insert(entity = OperationCategoryList::class)
    suspend fun insert(t: OperationCategoryList)

    @Insert(entity = OperationCategoryList::class)
    suspend fun insert(t: List<OperationCategoryList>)


    @Update(entity = OperationCategoryList::class)
    suspend fun update(t: OperationCategoryList)

    @Query(value = "SELECT * FROM category_list")
    suspend fun getAll(): List<OperationCategoryList>

}