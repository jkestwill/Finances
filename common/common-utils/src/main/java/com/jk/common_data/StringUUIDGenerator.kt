package com.jk.common_data

import java.util.UUID

object StringUUIDGenerator {

    fun generate(): String {
        return UUID.randomUUID().toString()
    }
}