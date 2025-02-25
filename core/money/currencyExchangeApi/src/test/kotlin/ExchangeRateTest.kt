import com.example.currencyexchangeapi.ExchangeRateRequestParams
import com.example.currencyexchangeapi.ExchangeRateServices
import com.example.currencyexchangeapi.services.by.ExchangeServiceFactoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.time.LocalDateTime


class ExchangeRateTest {

    private val factory = ExchangeServiceFactoryImpl()
    private val nbrbApi = ExchangeRateServices.NBRB()

    @Test
    fun get_ExchangeRateTest(): Unit = runBlocking {
        val api = factory.create(nbrbApi)
        val result = api.exchange(
            ExchangeRateRequestParams(
                currencyIn = "BYN",
                currencyOut = "USD",
                date = LocalDateTime.now()
            )
        )
        println(result)
    }

}