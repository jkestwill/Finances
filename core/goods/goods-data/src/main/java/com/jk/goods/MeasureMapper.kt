package com.jk.goods

import com.jk.common_goods_data.Measure
import com.jk.transaction_database.transaction.entity.MeasureEntity
import org.mapstruct.Mapper

@Mapper()
interface MeasureMapper {

    fun toEntity(measure:Measure):MeasureEntity

    fun toMeasure(measureEntity:MeasureEntity):Measure
}