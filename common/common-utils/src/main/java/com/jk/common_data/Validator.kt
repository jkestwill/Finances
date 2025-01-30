package com.jk.common_data

interface Validator<T> {

    fun validate(target:T)
}