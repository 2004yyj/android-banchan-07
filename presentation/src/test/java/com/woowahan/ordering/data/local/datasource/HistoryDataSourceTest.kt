package com.woowahan.ordering.data.local.datasource

import androidx.paging.AsyncPagingDataDiffer
import androidx.recyclerview.widget.DiffUtil
import com.woowahan.ordering.data.datasource.HistoryDataSource
import com.woowahan.ordering.data.entity.HistoryEntity
import com.woowahan.ordering.domain.model.History
import com.woowahan.ordering.impl.dao.FakeHistoryDao
import com.woowahan.ordering.paging.PagingCallback
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HistoryDataSourceTest {
    private val deliveryTime1 = System.currentTimeMillis()
    private val deliveryTime2 = System.currentTimeMillis()

    private val testList = arrayListOf(
        HistoryEntity(
            detailHash = "HBDEF",
            title = "잡채",
            thumbnail = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
            price = 12900,
            discountPrice = 11610,
            latestViewedTime = deliveryTime1
        ),
        HistoryEntity(
            detailHash = "HF778",
            title = "소갈비찜",
            thumbnail = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
            price = 28900,
            discountPrice = 26010,
            latestViewedTime = deliveryTime2
        ),
    )

    private lateinit var fakeHistoryDao: FakeHistoryDao
    private lateinit var historyDataSource: HistoryDataSource

    @Before
    fun before() {
        fakeHistoryDao = FakeHistoryDao(testList)
        historyDataSource = HistoryDataSourceImpl(fakeHistoryDao)
    }

    @Test
    fun `이미 본 상품 아이템 가져오기`() = runTest {
        val expected = arrayListOf(
            History(
                detailHash = "HBDEF",
                title = "잡채",
                thumbnail = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
                price = 12900,
                discountedPrice = 11610,
                latestViewedTime = deliveryTime1
            ),
            History(
                detailHash = "HF778",
                title = "소갈비찜",
                thumbnail = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
                price = 28900,
                discountedPrice = 26010,
                latestViewedTime = deliveryTime2
            ),
        )
        var actual: List<History>? = null
        val testJob = launch(UnconfinedTestDispatcher()) {
            historyDataSource.getSimpleHistories(2).collect {
                actual = it
            }
        }
        assertEquals(expected, actual)
        testJob.cancel()
    }

    @Test
    fun `이미 본 상품 자세히보기 아이템 가져오기`() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val expected = arrayListOf(
            History(
                detailHash = "HBDEF",
                title = "잡채",
                thumbnail = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
                price = 12900,
                discountedPrice = 11610,
                latestViewedTime = deliveryTime1
            ),
            History(
                detailHash = "HF778",
                title = "소갈비찜",
                thumbnail = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
                price = 28900,
                discountedPrice = 26010,
                latestViewedTime = deliveryTime2
            ),
        )
        val differ = AsyncPagingDataDiffer(
            diffCallback = MyDiffCallback(),
            updateCallback = PagingCallback(),
            workerDispatcher = UnconfinedTestDispatcher()
        )
        val testJob = launch(UnconfinedTestDispatcher()) {
            historyDataSource.getAllHistories().collect {
                differ.submitData(it)
            }
        }
        val actual = differ.snapshot().items
        assertTrue(actual.containsAll(expected))
        testJob.cancel()
    }

    class MyDiffCallback : DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem.detailHash == newItem.detailHash
        }
        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem == newItem
        }
    }
}