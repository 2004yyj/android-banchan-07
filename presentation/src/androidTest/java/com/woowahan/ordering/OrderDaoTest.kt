package com.woowahan.ordering

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.woowahan.ordering.data.entity.CartEntity
import com.woowahan.ordering.data.entity.OrderEntity
import com.woowahan.ordering.data.local.dao.CartDao
import com.woowahan.ordering.data.local.dao.OrderDao
import com.woowahan.ordering.data.local.db.AppDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class OrderDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var orderDao: OrderDao
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun createOrderDao() {
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        orderDao = db.orderDao()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun orderInsert() = runTest {
        val orderEntity = OrderEntity(0, System.currentTimeMillis(), false)
        try {
            orderDao.insertOrder(orderEntity)
        } catch (e: IOException) {
            assertThat(e.cause, equalTo(null))
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun isExistsNotDeliveredOrder_test() = runTest {
        try {
            orderDao.isExistNotDeliveredOrder()
        } catch (e: IOException) {
            assertThat(e.cause, equalTo(null))
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateOrder_test() = runTest {
        try {
            orderDao.updateOrder(1, true)
        } catch (e: IOException) {
            assertThat(e.cause, equalTo(null))
        }
    }

    @After
    fun closeDB() {
        db.close()
    }
}