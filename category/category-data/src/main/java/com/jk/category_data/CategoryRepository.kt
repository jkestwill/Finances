package com.jk.category_data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.jk.common_data.ApiRequest
import com.jk.transaction_database.transaction.dao.CategoryDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val categoryPagingSource: CategoryPagingSourceFactory
) {
    fun getList(q: String, sortBy: String, isAsc: Boolean): Flow<PagingData<TransactionCategory>> {
        return Pager(PagingConfig(20)) {
            categoryPagingSource.create(sortBy = sortBy, isAsc = isAsc, q = q)
        }.flow
    }


    fun removeByIdList(categoryIdList: List<String>): Flow<ApiRequest<Unit>> {
        val startFlow: Flow<ApiRequest<Unit>> = flowOf(ApiRequest.Loading<Unit>())
        val result: Flow<ApiRequest<Unit>> = flow<ApiRequest<Unit>> {
            try {
                emit(ApiRequest.Success(categoryDao.delete(categoryIdList)))
            } catch (e: Exception) {
                ApiRequest.Error<Unit>(data = null, error = e)
            }
        }
        return merge(startFlow, result)
    }

    fun add(category: TransactionCategory): Flow<ApiRequest<Long>> {
        val startFlow = flowOf(ApiRequest.Loading<Long>())
        val result: Flow<ApiRequest<Long>> = flow {
            emit(categoryDao.insert(category.toEntity()))
        }.map { result ->
            if (result > 0) {
                ApiRequest.Success(result)
            } else {
                ApiRequest.Error(result, error = Exception("Category not inserted"))
            }
        }

        return merge(startFlow, result)
    }

//    suspend fun update(category: TransactionCategory): Flow<Response<Unit>> = flowOf(
//        categoryLocalDataSource.update(category.toEntity())
//    )

    fun getById(categoryId: String): Flow<ApiRequest<TransactionCategory>> {
        val startEmitFlow = flowOf(ApiRequest.Loading<TransactionCategory>())

        val result = flow {
            emit(categoryDao.getById(categoryId))
        }.map {
            if (it != null) {
                ApiRequest.Success<TransactionCategory>(it.toCategory())
            } else {
                ApiRequest.Error<TransactionCategory>(
                    it,
                    NoSuchElementException("Category with id $categoryId doesn't exists")
                )
            }
        }.catch {
            ApiRequest.Error(data = null, error = it)
        }

        return merge(startEmitFlow, result)
    }
}

@AssistedFactory
interface CategoryPagingSourceFactory {
    fun create(
        @Assisted("q") q: String,
        @Assisted("sortBy") sortBy: String,
        @Assisted("isAsc") isAsc: Boolean
    ): CategoryPagingSource

}