package com.jk.transaction_database.transaction.dao

import android.location.Address
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jk.transaction_database.transaction.AddressEntity

@Dao
interface AddressDao{

    @Insert(AddressEntity::class)
    suspend fun insert(addressEntity: AddressEntity)


    @Query("SELECT * FROM address WHERE id LIKE :id")
    suspend fun getById(id:String):Address
}