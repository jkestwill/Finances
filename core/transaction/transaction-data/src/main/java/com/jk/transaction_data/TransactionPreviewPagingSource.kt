package com.jk.transaction_data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jk.transaction_database.transaction.dao.TransactionDao
import dagger.Component.Factory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject


class TransactionPreviewPagingSource @AssistedInject constructor(
    private val transactionDao: TransactionDao,
    @Assisted("q") private val q:String,
    @Assisted("sortBy") private var sortBy: String,
    @Assisted("isAsc") private var isAsc: Boolean
):PagingSource<Int,TransactionPreview>() {
    override fun getRefreshKey(state: PagingState<Int, TransactionPreview>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransactionPreview> {
        TODO("Not yet implemented")
    }


}


