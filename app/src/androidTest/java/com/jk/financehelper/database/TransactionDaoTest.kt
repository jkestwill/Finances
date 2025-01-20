package com.jk.financehelper.database

//
//@RunWith(AndroidJUnit4::class)
//class TransactionDaoTest {
//
//    private lateinit var db: TransactionDatabase
//    private lateinit var transactionDao: TransactionDao
//    private var dbTest = DatabaseTest()
//    @Before
//    fun init(){
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        db = Room.inMemoryDatabaseBuilder(context, TransactionDatabase::class.java).build()
//        transactionDao = db.getTransactionDao()
//        dbTest.initDb()
//    }
//
//    @Test
//    fun readTransactionPreview():Unit= runBlocking{
//        dbTest.writeReadTransactionRelation()
//
//        println(transactionDao.getTransactionPreviewListByCategoryId("1", "","id", true,0,0))
//    }
//
//    @After
//    fun close(){
//        db.close()
//        dbTest.closeDb()
//    }
//
//    companion object{
////        fun testTransactionPreviewList():List<TransactionPreview>{
////            return listOf(
////                TransactionPreview()
////            )
////        }
//    }
//}