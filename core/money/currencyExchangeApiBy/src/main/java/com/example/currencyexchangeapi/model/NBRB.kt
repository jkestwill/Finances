package com.example.currencyexchangeapi.model

import com.example.currencyexchangeapi.utils.serializer.LocalDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
// т.к этот сервис показывает только отношение BYN к иностранным валютам
@Serializable
data class NBRB(
    val currencyIn:String="BYN",
    @SerialName("Cur_ID")
    val id: Int,
    @SerialName("Date")
    @Serializable(with = LocalDateTimeSerializer::class)
    val date: LocalDateTime,
    @SerialName("Cur_Abbreviation")
    val currencyAbbreviation: String,
    @SerialName("Cur_Name")
    val currencyName:String,
    @SerialName("Cur_Scale")
    val scale: Int,
    @SerialName("Cur_OfficialRate")
    val rate: Double
)