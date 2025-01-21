package com.jk.transaction_database.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "bank",
    foreignKeys = [
        ForeignKey(entity = AddressEntity::class,
            parentColumns = ["id"],
            childColumns = ["addressId"],
            onDelete = ForeignKey.CASCADE)
    ]
)
data class BankEntity(
    @PrimaryKey
    val id:String,
    @ColumnInfo(name="name")
    val name:String,
    @ColumnInfo(name="abbreviation")
    val abbreviation:String?,
    @ColumnInfo(name="url")
    val url: String,
    @ColumnInfo(name="address_id")
    val addressId:String?,
    @ColumnInfo(name="image_url")
    val imageUrl:String?
)

