package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jk.transaction_database.transaction.GoodsMoneyListDao
import com.jk.transaction_database.transaction.entity.GoodsEntity
import com.jk.transaction_database.transaction.database.TransactionDatabase
import com.jk.transaction_database.transaction.list.GoodsMoneyListEntity
import com.jk.transaction_database.transaction.list.GoodsSpecificationsListEntity
import com.jk.transaction_database.transaction.preview.GoodsPreviewEntity
import com.jk.transaction_database.transaction.relations.GoodsxSpecificationsxMoneyRelation

@Dao
abstract class GoodsDao internal constructor(
    private val db: TransactionDatabase
) : SpecificationDao {
    private val measureDao: MeasureDao = db.getMeasureDao()
    private val languageDao: LanguageDao = db.getLanguageDao()
    private val languageMeasureListDao: LanguageMeasureListDao = db.getLanguageMeasureListDao()
    private val moneyDao: MoneyDao = db.getMoneyDao()
    private val specificationDao: SpecificationDao = db.getSpecificationDao()
    private val goodsSpecificationDao: GoodsSpecificationDao = db.getGoodsSpecificationDao()
    private val goodsMoneyListDao: GoodsMoneyListDao = db.getGoodsMoneyListDao()

    @Update(entity = GoodsEntity::class)
    abstract suspend fun update(t: GoodsEntity)

    @Query("SELECT * FROM goods WHERE goods.id == :id")
    abstract fun getById(id: String): GoodsEntity

    @Transaction
    open suspend fun insertGoodsRelation(goodsList: List<GoodsxSpecificationsxMoneyRelation>) {
        for (goods in goodsList) {
            specificationDao.insert(goods.specifications)
            insert(goods.goodsEntity)
            for (money in goods.cost) {
                moneyDao.insert(money)
                goodsMoneyListDao.insert(
                    GoodsMoneyListEntity(
                        goods.goodsEntity.id,
                        money.id,
                        date = money.date
                    )
                )
            }

            for (specs in goods.specifications) {
                goodsSpecificationDao.insert(
                    GoodsSpecificationsListEntity(
                        goodsId = goods.goodsEntity.id,
                        specificationsId = specs.id
                    )
                )
            }
        }
    }

    /**
     * Получает превью товаров с ценой на последнюю дату
     * */
    @Query(
        "SELECT goods.*, money.id as money_id, money.amount as money_amount,money.currency_id as money_currency_id, currency.name as currency_name, currency.id as currency_id FROM goods " +
                "INNER JOIN goods_money_list ON goods_money_list.goods_id == goods.id " +
                "INNER JOIN money ON goods_money_list.money_id == money.id " +
                "INNER JOIN currency ON money.currency_id == currency.id " +
                "WHERE LOWER(goods.name) LIKE  '%'||:q||'%' " +
                "AND goods_money_list.date ==  (SELECT MAX(goods_money_list.date) FROM goods_money_list LIMIT 1) " +
                "ORDER BY CASE WHEN :isAsc ==1 THEN :sortBy END ASC, " +
                "CASE WHEN :isAsc ==0 THEN :sortBy END DESC " +
                "LIMIT :limit OFFSET :offset  "
    )
    abstract suspend fun getGoodsPreviewList(
        q: String,
        sortBy: String,
        isAsc: Boolean,
        limit: Int,
        offset: Int
    ): List<GoodsPreviewEntity>


    @Delete(entity = GoodsEntity::class)
    abstract suspend fun delete(t: GoodsEntity)

    @Transaction
    @Query(
        value = "SELECT * FROM goods " +
                " WHERE LOWER(goods.name) LIKE  '%'||:q||'%' " +
                "ORDER BY CASE WHEN :isAsc ==1 THEN :sortBy END ASC, " +
                "CASE WHEN :isAsc ==0 THEN :sortBy END DESC " +
                "LIMIT :limit OFFSET :offset"
    )
    abstract suspend fun getAll(
        q: String,
        sortBy: String,
        isAsc: Boolean,
        limit: Int,
        offset: Int
    ): List<GoodsxSpecificationsxMoneyRelation>

    @Transaction
    @Query("SELECT * FROM goods WHERE goods.id in (:idList)")
    abstract suspend fun getByIdList(idList: List<String>): List<GoodsxSpecificationsxMoneyRelation>

    @Insert(entity = GoodsEntity::class)
    abstract suspend fun insert(t: GoodsEntity)

    @Insert(entity = GoodsEntity::class)
    abstract suspend fun insertList(t: List<GoodsEntity>)
}