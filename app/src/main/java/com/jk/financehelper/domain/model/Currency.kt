package com.jk.financehelper.domain.model

import javax.annotation.concurrent.Immutable

@Immutable
data class Currency(
    val id:String,
    val name:String
)
