package com.jk.financehelper.di

import com.jk.financehelper.datasource.TransactionPagingLocalSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory


@AssistedFactory
interface TransactionPreviewPagingSourceFactory {
    fun create(
        @Assisted("sortBy") sortBy: String,
        @Assisted("isAsc") isAsc: Boolean,
        @Assisted("categoryId") categoryId:String
    ):TransactionPagingLocalSource

}

