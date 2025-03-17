package com.example.currency_exchange

import com.example.currencyexchangeapi.ExchangeRateRequestParams
import com.jk.common_data.ApiRequest
import com.jk.exchange_rate_data.ApiRequestMergeStrategy
import com.jk.exchange_rate_data.CurrencyExchangeRepository
import com.jk.money_common_data.ExchangeRate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExchangeViewModelUnitTest {
    @Mock
    lateinit var currencyExchangeRepository: CurrencyExchangeRepository

    lateinit var viewModel: ExchangeRateViewModel

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        viewModel = ExchangeRateViewModel(currencyExchangeRepository)
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun exchange_correct() = runTest {
        val result = ApiRequest.Success<ExchangeRate>(
            ExchangeRate(
                "",
                "",
                1,
                rate = 2.3,
                date = LocalDateTime.now(),
                bank = null
            )
        )

        Mockito.`when`(
            currencyExchangeRepository.getExchangeRate(
                "NBRB",
                exchangeRateRequestParams = ExchangeRateRequestParams(
                    currencyIn = "",
                    currencyOut = "",
                    date = LocalDateTime.now()
                ),
                mergeStrategy = ApiRequestMergeStrategy()
            )
        ).thenReturn(flowOf(result))
        viewModel.getBynToCurrencyExchange("BYN", "USD", "NBRB")
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch(UnconfinedTestDispatcher()) {

            viewModel.exchangeRateState.collect {
                println(it)
            }
            //assertTrue(result.data == sas)
        }


    }
}