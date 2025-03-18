package com.jk.category_data

import android.database.sqlite.SQLiteReadOnlyDatabaseException
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jk.category_common_data.Category
import com.jk.common_data.ApiRequest
import com.jk.common_data.FinanceHelperException
import com.jk.common_data.SearchParams
import com.jk.transaction_database.transaction.dao.CategoryDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val categoryPagingSource: CategoryPagingSourceFactory,
    private val categoryMapper: CategoryMapper
) {
    fun getList(
        searchParams: SearchParams
    ): Flow<PagingData<Category>> {
        return Pager(PagingConfig(20)) {
            categoryPagingSource.create(
                sortBy = searchParams.sortBy,
                isAsc = searchParams.isAsc,
                q = searchParams.q
            )
        }.flow
    }

    fun removeByIdList(categoryIdList: List<String>): Flow<ApiRequest<Unit>> {
        val startFlow: Flow<ApiRequest<Unit>> = flowOf(ApiRequest.Loading())
        val result: Flow<ApiRequest<Unit>> = flow {
            try {
                emit(ApiRequest.Success(categoryDao.delete(categoryIdList)))
            } catch (e: SQLiteReadOnlyDatabaseException) {
                emit(ApiRequest.Error<Unit>(data = null, error = FinanceHelperException.SystemException()))
            }
        }
        return merge(startFlow, result)
    }

    fun add(category:Category): Flow<ApiRequest<Long>> {
        val startFlow = flowOf(ApiRequest.Loading<Long>())
        val result: Flow<ApiRequest<Long>> = flow<Long> {
            emit(categoryDao.insert(categoryMapper.toEntity(category)))
        }.map { result ->
            if (result > 0) {
                ApiRequest.Success(result)
            } else {
                ApiRequest.Error(result, error = Exception("Category not inserted"))
            }
        }

        return merge(startFlow, result)
    }

//    suspend fun update(category: Category): Flow<ApiRequest<Unit>> = flowOf(
//        categoryDao.update(categoryMapper.toEntity(category))
//    )

    fun getById(categoryId: String): Flow<ApiRequest<Category>> {
        val startEmitFlow = flowOf(ApiRequest.Loading<Category>())

        val result = flow {
            emit(categoryDao.getById(categoryId))
        }.map {
            if (it != null) {
                ApiRequest.Success(categoryMapper.toCategory(it))
            } else {
                ApiRequest.Error<Category>(
                    it,
                    NoSuchElementException("Category with id $categoryId doesn't exists")
                )
            }
        }.catch {
            ApiRequest.Error(data = null, error = it)
        }

        return merge(startEmitFlow, result)
    }

    fun getByCategoryListId(categoryId:List<String>): Flow<ApiRequest<List<Category>>> {
        val startEmitFlow = flowOf(ApiRequest.Loading<List<Category>>())

        val result = flow {
            emit(categoryDao.getByListId(categoryId))
        }.map {
            val category = it?.map { c->categoryMapper.toCategory(c)}
            if (category != null) {
                ApiRequest.Success(category )
            } else {
                ApiRequest.Error<List<Category>>(
                    null,
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