package com.woowahan.ordering.data.local.datasource

import com.woowahan.ordering.data.datasource.CartDataSource
import com.woowahan.ordering.data.entity.CartEntity
import com.woowahan.ordering.data.mapper.toCartResult
import com.woowahan.ordering.data.mapper.toModel
import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.domain.model.CartResult
import com.woowahan.ordering.impl.dao.FakeCartDao
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CartDataSourceTest {
    private val testList = arrayListOf(
        CartEntity(
            id = 1,
            title = "잡채",
            thumbnail = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
            price = 11610,
            count = 2,
            detailHash = "HBDEF",
            isChecked = false,
            orderId = null
        ),
        CartEntity(
            id = 2,
            title = "소갈비찜",
            thumbnail = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
            price = 26010,
            count = 1,
            detailHash = "HF778",
            isChecked = true,
            orderId = 1
        ),
    )

    private lateinit var fakeCartDao: FakeCartDao
    private lateinit var cartDataSource: CartDataSource

    @Before
    fun before() {
        fakeCartDao = FakeCartDao(testList)
        cartDataSource = CartDataSourceImpl(fakeCartDao)
    }

    @Test
    fun `장바구니 아이템 가져오기`() = runTest {
        val expected = testList.map { it.toModel() }
        var actual: List<Cart>? = null
        val testJob = launch(UnconfinedTestDispatcher()) {
            cartDataSource.getCart().collect { actual = it }
        }
        assertEquals(expected, actual)
        testJob.cancel()
    }

    @Test
    fun `장바구니에 아이템이 존재하는가`() = runTest {
        val actual: Boolean = cartDataSource.isExistNotOrderedCart("HBDEF")
        assertEquals(true, actual)
    }

    @Test
    fun `장바구니 CartResult 가져오기`() = runTest {
        val expected = testList.toCartResult()
        var actual: CartResult? = null
        val testJob = launch(UnconfinedTestDispatcher()) {
            cartDataSource.getCartResult().collect { actual = it }
        }
        assertEquals(expected, actual)
        testJob.cancel()
    }
}