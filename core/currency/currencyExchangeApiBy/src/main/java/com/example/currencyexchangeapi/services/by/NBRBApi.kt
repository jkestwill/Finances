package com.example.currencyexchangeapi.services.by

import com.example.currencyexchangeapi.Api
import com.example.currencyexchangeapi.model.NBRB
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

public interface NBRBApi : Api {
    @GET("rates/{currency}")
    suspend fun exchange(
        @Path(value = "currency") currencyName: String,
        @Query("parammode") paramMode: Int
    ): Result<NBRB>

    companion object {
        const val PARAM_MODE_1 = 1
        const val PARAM_MODE_2 = 2
    }
}

