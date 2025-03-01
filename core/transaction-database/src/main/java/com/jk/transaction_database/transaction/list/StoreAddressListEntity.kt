package com.jk.transaction_database.transaction.list

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.jk.transaction_database.transaction.entity.AddressEntity
import com.jk.transaction_database.transaction.entity.Store

@Entity(
    "store_address_list",
    primaryKeys = ["store_id", "address_id"],
    foreignKeys = [
        ForeignKey(
            entity = Store::class,
            parentColumns = ["id"],
            childColumns = ["store_id"]
        ),
        ForeignKey(
            entity = AddressEntity::class,
            parentColumns = ["id"],
            childColumns = ["address_id"]
        )
    ]
)
data class StoreAddressListEntity(
    @ColumnInfo("store_id")
    val storeId: String,
    @ColumnInfo("address_id")
    val addressId: String
)
