package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jk.transaction_database.transaction.entity.GoodsEntity
import com.jk.transaction_database.transaction.database.TransactionDatabase
import com.jk.transaction_database.transaction.list.GoodsSpecificationsListEntity
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

    @Update(entity = GoodsEntity::class)
    abstract suspend fun update(t: GoodsEntity)

    @Query("SELECT * FROM goods WHERE goods.id == :id")
    abstract fun getById(id: String): GoodsEntity

    @Transaction
    open suspend fun insertGoodsRelation(goodsList: List<GoodsxSpecificationsxMoneyRelation>) {
        for (goods in goodsList) {
            specificationDao.insert(goods.specifications)
            moneyDao.insert(goods.cost)
            insert(goods.goodsEntity)

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
//    @Transaction
//    open suspend fun insert(goodsRelation: GoodsRelation) {
//        for (i in goodsRelation.specificationList) {
//            val lang = i.measure.lang
//            val measure = i.measure.measureEntity
//            for (i in measure) {
//                measureDao.insert(i)
//                languageDao.insert(lang)
//                languageMeasureListDao.insert(i.id, lang.id)
//            }
//            specificationDao.insert(i.specificationEntity)
//            goodsSpecificationDao.insert(
//                GoodsSpecificationsListEntity(
//                    goodsId = goodsRelation.goodsEntity.id,
//                    specificationsId = i.specificationEntity.id
//                )
//            )
//        }
//        moneyDao.insert(goodsRelation.cost)
//        insert(goodsRelation.goodsEntity)
//    }

    @Delete(entity = GoodsEntity::class)
    abstract suspend fun delete(t: GoodsEntity)

    //
//    @Query(
//        value = "SELECT * FROM goods " +
//                " WHERE LOWER(goods.name) LIKE  '%'||:q||'%' " +
//                "ORDER BY CASE WHEN :isAsc ==1 THEN :sortBy END ASC, " +
//                "CASE WHEN :isAsc ==0 THEN :sortBy END DESC " +
//                "LIMIT :limit OFFSET :offset"
//    )
//    abstract suspend fun getAll(
//        q: String,
//        sortBy: String,
//        isAsc: Boolean,
//        limit: Int,
//        offset: Int
//    ): List<GoodsRelation>
//
    @Transaction
    @Query("SELECT * FROM goods WHERE goods.id in (:idList)")
    abstract suspend fun getByIdList(idList: List<String>): List<GoodsxSpecificationsxMoneyRelation>

    @Insert(entity = GoodsEntity::class)
    abstract suspend fun insert(t: GoodsEntity)

    @Insert(entity = GoodsEntity::class)
    abstract suspend fun insertList(t: List<GoodsEntity>)
}