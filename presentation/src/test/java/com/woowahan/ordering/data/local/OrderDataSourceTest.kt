package com.woowahan.ordering.data.local

import androidx.paging.AsyncPagingDataDiffer
import androidx.recyclerview.widget.DiffUtil
import com.woowahan.ordering.data.datasource.OrderDataSource
import com.woowahan.ordering.data.entity.CartEntity
import com.woowahan.ordering.data.entity.OrderEntity
import com.woowahan.ordering.data.entity.SimpleOrderEntity
import com.woowahan.ordering.data.local.datasource.OrderDataSourceImpl
import com.woowahan.ordering.data.mapper.toOrderList
import com.woowahan.ordering.domain.model.OrderedCartList
import com.woowahan.ordering.domain.model.SimpleOrder
import com.woowahan.ordering.impl.dao.FakeOrderDao
import com.woowahan.ordering.paging.PagingCallback
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class OrderDataSourceTest {
    private val deliveryTime1 = System.currentTimeMillis()
    private val deliveryTime2 = System.currentTimeMillis()

    private val testCartList = arrayListOf(
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

    private val testOrderList = arrayListOf(
        OrderEntity(
            id = 1,
            deliveryTime = deliveryTime1,
            isDelivered = false
        ),
        OrderEntity(
            id = 2,
            deliveryTime = deliveryTime2,
            isDelivered = false
        ),
    )

    private val testSimpleOrderList = arrayListOf(
        SimpleOrderEntity(
            title = "잡채",
            deliveryTime = deliveryTime1,
            thumbnail = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
            totalPrice = 11610,
            productCount = 1
        ),
        SimpleOrderEntity(
            title = "소갈비찜",
            deliveryTime = deliveryTime2,
            thumbnail = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
            totalPrice = 26010 + 11610,
            productCount = 2
        ),
    )

    private lateinit var fakeOrderDao: FakeOrderDao
    private lateinit var orderDataSource: OrderDataSource

    @Before
    fun before() {
        fakeOrderDao = FakeOrderDao(testCartList, testSimpleOrderList, testOrderList)
        orderDataSource = OrderDataSourceImpl(fakeOrderDao)
    }

    @Test
    fun `주문 상품 간략 리스트 가져오기`() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val expected = arrayListOf(
            SimpleOrder(
                title = "잡채",
                deliveryTime = deliveryTime1,
                thumbnail = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
                totalPrice = 11610,
                productCount = 1
            ),
            SimpleOrder(
                title = "소갈비찜",
                deliveryTime = deliveryTime2,
                thumbnail = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
                totalPrice = 26010 + 11610,
                productCount = 2
            ),
        )
        val differ = AsyncPagingDataDiffer(
            diffCallback = MyDiffCallback(),
            updateCallback = PagingCallback(),
            workerDispatcher = UnconfinedTestDispatcher()
        )
        val testJob = launch(UnconfinedTestDispatcher()) {
            orderDataSource.getSimpleOrder().collect {
                differ.submitData(it)
            }
        }
        val actual = differ.snapshot().items
        assertTrue(actual.containsAll(expected))
        testJob.cancel()
    }

    @Test
    fun `주문 상품 가져오기`() = runTest {
        Dispatchers.resetMain()
        val expected = arrayListOf(
            CartEntity(
                id = 2,
                title = "소갈비찜",
                thumbnail = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
                price = 26010,
                count = 1,
                detailHash = "HF778",
                isChecked = true,
                orderId = 1
            )
        ).toOrderList()
        val actual: OrderedCartList = orderDataSource.getOrderedCartByDeliveryTime(deliveryTime1)
        assertEquals(expected, actual)
    }

    @Test
    fun `배달되지 않은 주문 상품 존재 여부`() = runTest {
        val expected = false
        var actual: Boolean? = null
        val testJob = launch(UnconfinedTestDispatcher()) {
            orderDataSource.isExistNotDeliveredOrder().collect {
                actual = it
            }
        }
        assertTrue(actual == expected)
        testJob.cancel()
    }

    class MyDiffCallback : DiffUtil.ItemCallback<SimpleOrder>() {
        override fun areItemsTheSame(oldItem: SimpleOrder, newItem: SimpleOrder): Boolean {
            return oldItem.deliveryTime == newItem.deliveryTime
        }
        override fun areContentsTheSame(oldItem: SimpleOrder, newItem: SimpleOrder): Boolean {
            return oldItem == newItem
        }
    }
}