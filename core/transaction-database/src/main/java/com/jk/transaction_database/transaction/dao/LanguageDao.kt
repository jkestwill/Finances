package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jk.transaction_database.transaction.LanguageEntity

@Dao
interface LanguageDao {
    @Insert(entity = LanguageEntity::class)
    fun insert(languageEntity: LanguageEntity)


    @Query("SELECT * FROM language WHERE language.id==:id")
    fun getById(id: String):LanguageEntity
}