package com.jk.financehelper

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private val TAG = "ExampleInstrumentedTest"
    private val schemaVer = 3L
    private val name = "transaction_test"

    var job = SupervisorJob()
    var testScope = CoroutineScope(job + Dispatchers.IO)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.jk.financehelper", appContext.packageName)

    }


    @Test
    fun addTransactions() = runTest {

    }
@Test
    fun just() {
        val currentDate = LocalDate.now()


    }

    @Test
    fun getTransactions() = runTest {
//        val realm = getRealm()
//        val transactionLocalDataSource = TransactionLocalDataSource(realm)
//        val managedTransaction = transactionLocalDataSource.addTransaction(testTransactions[0])
//        assertTrue(managedTransaction.isManaged())
//
//        val result = transactionLocalDataSource.getTransactions().collect {
//            val sas=it.list.toList()
//            Log.e(TAG, "getTransactions: ${sas.size}", )
//            assertEquals(testTransactions,sas )
//        }
//
//        realm.close()

    }


}