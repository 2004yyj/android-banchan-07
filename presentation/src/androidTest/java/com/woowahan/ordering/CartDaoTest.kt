package com.woowahan.ordering

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.woowahan.ordering.data.entity.CartEntity
import com.woowahan.ordering.data.local.dao.CartDao
import com.woowahan.ordering.data.local.db.AppDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CartDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var cartDao: CartDao
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun createCartDao() {
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        cartDao = db.cartDao()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun selectCartAddedList_insertedItemOrderNull_insert() = runBlockingTest {
        val cartEntity = CartEntity(
            0, "양윤재", "ㅁㅇㄴㄹㅁ", 10000, 1, "1242314", false, null
        )

        try {
            cartDao.insertCart(cartEntity)
        } catch (e: IOException) {
            assertThat(e.cause, equalTo(null))
        }
    }

    @After
    fun closeDB() {
        db.close()
    }
}