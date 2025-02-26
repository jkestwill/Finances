package com.jk.goods

import com.jk.common_goods_data.Specification
import com.jk.transaction_database.transaction.entity.SpecificationsEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(uses = [MeasureMapper::class])
interface SpecificationsMapper {
    @Mapping(target = "measureId",source ="measure.id")
    fun toEntity(specification: Specification):SpecificationsEntity

    @Mapping(target = "measure.id", source = "measureId")
    @Mapping(target = "measure.name", expression = "java(\"\")")
    fun toSpecification(specificationsEntity: SpecificationsEntity):Specification
}